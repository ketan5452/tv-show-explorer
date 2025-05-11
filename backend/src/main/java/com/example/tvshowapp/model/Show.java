package com.example.tvshowapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Ketan Deshmukh
 * on 10/05/25
 */
@AllArgsConstructor
@Data
public class Show {

    private String title;

    private String summary;

    private String network;

    private String schedule;

    private String status;
}
