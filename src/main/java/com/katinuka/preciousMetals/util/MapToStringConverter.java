package com.katinuka.preciousMetals.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katinuka.preciousMetals.model.Currency;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.LinkedHashMap;
import java.util.Map;

@Converter
public class MapToStringConverter implements AttributeConverter<Map<Currency, Double>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<Currency, Double> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting map to string", e);
        }
    }

    @Override
    public Map<Currency, Double> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<LinkedHashMap<Currency, Double>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting string to map", e);
        }
    }
}
