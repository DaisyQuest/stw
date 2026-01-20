package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class EventBridgeTest {
    @Test
    void normalizesEventAndPayload() {
        EventBridge bridge = new EventBridge(new SequenceTracker());
        Map<String, String> payload = new java.util.HashMap<>();
        payload.put(" value ", " 123 ");
        payload.put("", "ignored");
        payload.put("nullValue", null);
        payload.put(null, "ignored");
        BrowserEvent event = new BrowserEvent("comp", "action", payload);

        SwingEvent normalized = bridge.normalize(event);

        assertEquals(EventType.ACTION, normalized.getType());
        assertEquals(1L, normalized.getSequence());
        assertEquals("123", normalized.getPayload().get("value"));
        assertEquals("", normalized.getPayload().get("nullValue"));
    }

    @Test
    void rejectsUnsupportedEventType() {
        EventBridge bridge = new EventBridge(new SequenceTracker());
        BrowserEvent event = new BrowserEvent("comp", "unknown", Map.of());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> bridge.normalize(event));

        assertEquals("Unsupported event type: unknown", ex.getMessage());
    }

    @Test
    void handlesNullPayload() {
        EventBridge bridge = new EventBridge(new SequenceTracker());
        BrowserEvent event = new BrowserEvent("comp", "key", null);

        SwingEvent normalized = bridge.normalize(event);

        assertEquals(EventType.KEY, normalized.getType());
        assertEquals(1L, normalized.getSequence());
        assertEquals(0, normalized.getPayload().size());
    }

    @Test
    void handlesEmptyPayloadMap() {
        EventBridge bridge = new EventBridge(new SequenceTracker());
        BrowserEvent event = new BrowserEvent("comp", "mouse", Map.of());

        SwingEvent normalized = bridge.normalize(event);

        assertEquals(EventType.MOUSE, normalized.getType());
        assertEquals(1L, normalized.getSequence());
        assertEquals(0, normalized.getPayload().size());
    }

    @Test
    void rejectsNullEvent() {
        EventBridge bridge = new EventBridge(new SequenceTracker());

        NullPointerException ex = assertThrows(NullPointerException.class,
            () -> bridge.normalize(null));

        assertEquals("event", ex.getMessage());
    }
}
