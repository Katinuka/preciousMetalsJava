package com.katinuka.preciousMetals.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
        String url = "http://localhost:5000/api/metals/prices";

        // Make a GET request to the Flask API
        try {
            ResponseEntity<Map<String, Double>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            return response.getBody();
        } catch (RestClientException ex) {
            return Collections.emptyMap();
        }
    }
}
