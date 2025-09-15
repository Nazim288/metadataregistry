package com.gpbapp.metadataregistry.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false) // false, чтобы явно указывать @Convert
public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        try {
            return attribute != null ? objectMapper.writeValueAsString(attribute) : null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка сериализации JsonNode в строку", e);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? objectMapper.readTree(dbData) : null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка десериализации строки в JsonNode", e);
        }
    }
}
