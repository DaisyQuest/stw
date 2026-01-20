package stw;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class BrowserEvent {
    private final String componentId;
    private final String eventType;
    private final Map<String, String> payload;

    public BrowserEvent(String componentId, String eventType, Map<String, String> payload) {
        this.componentId = normalizeRequired(componentId, "componentId");
        this.eventType = normalizeRequired(eventType, "eventType");
        this.payload = payload == null
            ? null
            : Collections.unmodifiableMap(new HashMap<>(payload));
    }

    public String getComponentId() {
        return componentId;
    }

    public String getEventType() {
        return eventType;
    }

    public Map<String, String> getPayload() {
        return payload;
    }

    private static String normalizeRequired(String value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " is required");
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(field + " is required");
        }
        return trimmed;
    }
}
