package com.katinuka.preciousMetals.controller;

import com.katinuka.preciousMetals.service.MetalPriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/metals")
public class MetalPriceController {

    private final MetalPriceService metalPriceService;

    public MetalPriceController(MetalPriceService metalPriceService) {
        this.metalPriceService = metalPriceService;
    }

    @GetMapping
    public Map<String, Double> getMetalPrices() {
        return metalPriceService.getMetalPrices();
    }
}

