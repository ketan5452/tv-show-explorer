package com.example.tvshowapp.service;

import com.example.tvshowapp.integration.tvmaze.TvMazeService;
import com.example.tvshowapp.model.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ketan Deshmukh
 * on 10/05/25
 */
@Service
public class ShowService {

    @Value("classpath:tvtitles.txt")
    private Resource tvTitlesFile;

    private final TvMazeService tvMazeService;
    private final Map<String, Show> shows = new ConcurrentHashMap<>();

    @Autowired
    public ShowService(TvMazeService tvMazeService) {
        this.tvMazeService = tvMazeService;
    }

    @Async
    public void initializeShowsAsync() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(tvTitlesFile.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String cleanedTitle = line
                        .trim()
                        .replaceAll("\\(.*?\\)", "")
                        .replaceAll("[^\\p{L}\\p{N}':&!?/\\-\\s]", "")
                        .replaceAll("-", " ")
                        .replaceAll("\\s{2,}", " ") // Remove extra spaces
                        .trim();
                if (!cleanedTitle.isEmpty()) {
                    Show details = tvMazeService.fetchShowDetails(cleanedTitle);
                    if (details != null) {
                        shows.put(cleanedTitle.toLowerCase(), details);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading tvtitles.txt: " + e.getMessage());
        }
    }

    public List<Show> getAllShows() {
        return new ArrayList<>(shows.values());
    }

    public Show getShowDetails(String title) {
        title = title.toLowerCase();
        if (shows.containsKey(title))
            return shows.get(title);
        else {
            Show details = tvMazeService.fetchShowDetails(title);
            if (details != null) {
                shows.put(title, details);
            }
            return details;
        }
    }
}
