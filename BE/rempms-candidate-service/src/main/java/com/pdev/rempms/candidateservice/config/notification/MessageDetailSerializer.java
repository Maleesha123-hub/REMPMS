package com.pdev.rempms.candidateservice.config.notification;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class MessageDetailSerializer extends JsonSerializer<Object> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (SerializationContext.isCustomSerializationEnabled() && value instanceof List) {
            List<?> list = (List<?>) value;
            gen.writeString(objectMapper.writeValueAsString(list));
        } else {
            gen.writeObject(value);
        }
    }
}