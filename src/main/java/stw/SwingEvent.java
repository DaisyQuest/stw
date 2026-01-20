package stw;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class SwingEvent {
    private final String componentId;
    private final EventType type;
    private final long sequence;
    private final Map<String, String> payload;

    public SwingEvent(String componentId, EventType type, long sequence, Map<String, String> payload) {
        this.componentId = Objects.requireNonNull(componentId, "componentId");
        this.type = Objects.requireNonNull(type, "type");
        this.sequence = sequence;
        this.payload = Collections.unmodifiableMap(new HashMap<>(Objects.requireNonNullElse(payload, Map.of())));
    }

    public String getComponentId() {
        return componentId;
    }

    public EventType getType() {
        return type;
    }

    public long getSequence() {
        return sequence;
    }

    public Map<String, String> getPayload() {
        return payload;
    }
}
