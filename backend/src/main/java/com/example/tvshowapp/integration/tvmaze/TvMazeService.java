package com.example.tvshowapp.integration.tvmaze;

import com.example.tvshowapp.model.Show;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Ketan Deshmukh
 * on 10/05/25
 */
@Service
public class TvMazeService {

    private final RestTemplate restTemplate;
    private final List<String> replacements = Arrays.asList("<p>", "</p>", "<i>", "</i>", "<b>", "</b>");

    public TvMazeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Show fetchShowDetails(String title) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://api.tvmaze.com/singlesearch/shows")
                .queryParam("q", title)
                .build()
                .encode()
                .toUri();

        Show result = fetchWithRetries(uri);
        if (result != null)
            return result;

        // Fallback
        URI fallbackUri = UriComponentsBuilder
                .fromHttpUrl("https://api.tvmaze.com/search/shows")
                .queryParam("q", title)
                .build()
                .encode()
                .toUri();

        return fetchFromSearchEndpoint(fallbackUri);
    }

    private Show fetchWithRetries(URI uri) {
        int maxRetries = 5;
        int delay = 1000;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
                if (response != null && !response.isEmpty() && response.containsKey("name")) {
                    return mapToShow(response);
                }
                break;
            } catch (HttpClientErrorException.TooManyRequests e) {
                System.err.println("429 Too Many Requests. Retrying in " + delay + "ms");
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ignored) {}
                delay *= 2;
            } catch (Exception e) {
                break;
            }
        }
        return null;
    }

    private Show fetchFromSearchEndpoint(URI fallbackUri) {
        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    fallbackUri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {});
            if (response.hasBody() && !response.getBody().isEmpty()) {
                Map<String, Object> showMap = (Map<String, Object>) response.getBody().get(0).get("show");
                if (showMap != null && !showMap.isEmpty() && showMap.containsKey("name")) {
                    return mapToShow(showMap);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in fallback fetch: " + e.getMessage());
        }
        return null;
    }

    private Show mapToShow(Map<String, Object> showMap) {
        String name = (String) showMap.get("name");
        String summary = showMap.containsKey("summary") ? (String) showMap.get("summary") : "N/A";
        Map<String, Object> networkData = (Map<String, Object>) showMap.get("network");
        String network = getNetwork(networkData);
        Map<String, Object> scheduleData = (Map<String, Object>) showMap.get("schedule");
        String schedule = getSchedule(scheduleData);
        String status = (String) showMap.get("status");
        summary = summary != null ? cleanUpSummary(summary) : "N/A";
        return new Show(name, summary, network, schedule, status);
    }

    private String getNetwork(Map<String, Object> networkData) {
        return networkData != null && networkData.containsKey("name")
                ? (String) networkData.get("name")
                : "N/A";
    }

    private String getSchedule(Map<String, Object> scheduleData) {
        return scheduleData != null && scheduleData.containsKey("days") && scheduleData.containsKey("time")
                ? String.join(", ", (List<String>) scheduleData.get("days")) + " at " + scheduleData.get("time")
                : "N/A";
    }

    private String cleanUpSummary(String summary) {
        return replacements.stream()
                .reduce(summary, (s, r) -> s.replaceAll(r, ""));
    }
}
