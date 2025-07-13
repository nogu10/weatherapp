package com.example.weatherapp.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PrefectureConverter implements AttributeConverter<Prefecture, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Prefecture attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public Prefecture convertToEntityAttribute(Integer dbData) {
        return dbData != null ? Prefecture.fromCode(dbData) : null;
    }
}
