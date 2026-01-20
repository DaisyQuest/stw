package stw;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class EventBridge {
    private final SequenceTracker sequenceTracker;

    public EventBridge(SequenceTracker sequenceTracker) {
        this.sequenceTracker = Objects.requireNonNull(sequenceTracker, "sequenceTracker");
    }

    public SwingEvent normalize(BrowserEvent event) {
        Objects.requireNonNull(event, "event");
        EventType type = parseType(event.getEventType());
        Map<String, String> payload = normalizePayload(event.getPayload());
        long sequence = sequenceTracker.nextSequence(event.getComponentId());
        return new SwingEvent(event.getComponentId(), type, sequence, payload);
    }

    private EventType parseType(String eventType) {
        String normalized = eventType.trim().toUpperCase(Locale.ROOT);
        try {
            return EventType.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unsupported event type: " + eventType, ex);
        }
    }

    private Map<String, String> normalizePayload(Map<String, String> payload) {
        Map<String, String> normalized = new HashMap<>();
        if (payload == null) {
            return normalized;
        }
        for (Map.Entry<String, String> entry : payload.entrySet()) {
            String key = entry.getKey() == null ? "" : entry.getKey().trim();
            if (key.isEmpty()) {
                continue;
            }
            String value = entry.getValue() == null ? "" : entry.getValue().trim();
            normalized.put(key, value);
        }
        return normalized;
    }
}
