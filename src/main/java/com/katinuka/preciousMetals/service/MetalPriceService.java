package com.katinuka.preciousMetals.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

@Service
public class MetalPriceService {

    private final RestTemplate restTemplate;

    public MetalPriceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to fetch metal prices from the Python API
    public Map<String, Double> getMetalPrices() {
        String url = "http://localhost:5000/api/metals/prices";  // API endpoint

        // Make a GET request to the Flask API
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return response.getBody();
        } catch (RestClientException ex) {
            return Collections.emptyMap();
        }
    }
}
