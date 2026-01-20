package stw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class EventJsonCodec {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String encode(BrowserEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("event is required");
        }
        Map<String, Object> payload = new HashMap<>();
        payload.put("componentId", event.getComponentId());
        payload.put("eventType", event.getEventType());
        payload.put("payload", event.getPayload());
        try {
            return MAPPER.writeValueAsString(payload);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to encode event", ex);
        }
    }

    public BrowserEvent decode(String json) {
        if (json == null || json.isBlank()) {
            throw new IllegalArgumentException("json is required");
        }
        JsonNode node;
        try {
            node = MAPPER.readTree(json);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Invalid JSON payload", ex);
        }
        String componentId = textValue(node.get("componentId"), "componentId");
        String eventType = textValue(node.get("eventType"), "eventType");
        Map<String, String> payload = parsePayload(node.get("payload"));
        return new BrowserEvent(componentId, eventType, payload);
    }

    private String textValue(JsonNode node, String field) {
        if (node == null || node.isNull()) {
            throw new IllegalArgumentException(field + " is required");
        }
        if (!node.isTextual()) {
            throw new IllegalArgumentException(field + " must be a string");
        }
        String value = node.asText();
        if (value.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
        return value;
    }

    private Map<String, String> parsePayload(JsonNode payloadNode) {
        if (payloadNode == null || payloadNode.isNull()) {
            return null;
        }
        if (!payloadNode.isObject()) {
            throw new IllegalArgumentException("payload must be an object");
        }
        Map<String, String> payload = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = payloadNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            JsonNode valueNode = entry.getValue();
            if (valueNode == null || valueNode.isNull()) {
                payload.put(entry.getKey(), null);
            } else if (valueNode.isTextual()) {
                payload.put(entry.getKey(), valueNode.asText());
            } else {
                throw new IllegalArgumentException("payload values must be strings");
            }
        }
        return payload;
    }
}
