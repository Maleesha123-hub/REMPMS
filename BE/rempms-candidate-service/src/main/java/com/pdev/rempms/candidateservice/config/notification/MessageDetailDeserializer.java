package com.pdev.rempms.candidateservice.config.notification;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.rempms.candidateservice.config.notification.dto.EmailDetailDTO;
import com.pdev.rempms.candidateservice.config.notification.dto.SMSDetailDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageDetailDeserializer extends JsonDeserializer<Object> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken jsonToken = p.getCurrentToken();
        String json;
        if (jsonToken == JsonToken.START_ARRAY ) {
            json = p.readValueAsTree().toString();
        }else if(jsonToken == JsonToken.START_OBJECT){
            json = p.getValueAsString();
        }else {
            json = p.getText();
        }
        JsonNode rootNode = objectMapper.readTree(json);
        if (rootNode.isArray()) {
            List<Object> result = new ArrayList<>();
            for (JsonNode node : rootNode) {
                String massageType = node.get("notificationType").asText();
                if ("SMS".equalsIgnoreCase(massageType)) {
                    result.add(objectMapper.treeToValue(node, SMSDetailDTO.class));
                } else if ("EMAIL".equalsIgnoreCase(massageType)) {
                    result.add(objectMapper.treeToValue(node, EmailDetailDTO.class));
                } else {
                    throw new IllegalArgumentException("Unsupported massageType: " + massageType);
                }
            }
            return result;
        } else {
            throw new IllegalArgumentException("Expected JSON array but got: " + rootNode);
        }
    }
}
