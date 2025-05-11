package com.example.tvshowapp.controller;

import com.example.tvshowapp.model.Show;
import com.example.tvshowapp.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author Ketan Deshmukh
 * on 10/05/25
 */
@RestController
@RequestMapping("/api/shows")
public class ShowController implements ApplicationListener<ContextRefreshedEvent> {

    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public ResponseEntity<List<Show>> getAllShows() {
        return new ResponseEntity<>(showService.getAllShows(), HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity<Show> getShowDetails(@PathVariable String title) {
        Show show = showService.getShowDetails(title);
        if (show != null) {
            return new ResponseEntity<>(show, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        showService.initializeShowsAsync();
    }
}
