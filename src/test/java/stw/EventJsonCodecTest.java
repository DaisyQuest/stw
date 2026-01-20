package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class EventJsonCodecTest {
    private final EventJsonCodec codec = new EventJsonCodec();

    @Test
    void encodesAndDecodesEvent() {
        Map<String, String> payload = new HashMap<>();
        payload.put("value", "42");
        payload.put("note", null);
        BrowserEvent event = new BrowserEvent("comp", "action", payload);

        String json = codec.encode(event);
        BrowserEvent decoded = codec.decode(json);

        assertEquals("comp", decoded.getComponentId());
        assertEquals("action", decoded.getEventType());
        assertEquals("42", decoded.getPayload().get("value"));
        assertNull(decoded.getPayload().get("note"));
    }

    @Test
    void handlesNullPayload() {
        BrowserEvent event = new BrowserEvent("comp", "action", null);

        String json = codec.encode(event);
        BrowserEvent decoded = codec.decode(json);

        assertNull(decoded.getPayload());
    }

    @Test
    void rejectsBlankJson() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode(" "));

        assertEquals("json is required", ex.getMessage());
    }

    @Test
    void rejectsNullJson() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode(null));

        assertEquals("json is required", ex.getMessage());
    }

    @Test
    void rejectsInvalidJson() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode("{"));

        assertEquals("Invalid JSON payload", ex.getMessage());
    }

    @Test
    void rejectsMissingComponentId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode("{\"eventType\":\"action\"}"));

        assertEquals("componentId is required", ex.getMessage());
    }

    @Test
    void rejectsBlankComponentId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode("{\"componentId\":\" \",\"eventType\":\"action\"}"));

        assertEquals("componentId is required", ex.getMessage());
    }

    @Test
    void rejectsNonStringComponentId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode("{\"componentId\":1,\"eventType\":\"action\"}"));

        assertEquals("componentId must be a string", ex.getMessage());
    }

    @Test
    void rejectsPayloadWithNonStringValues() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode("{\"componentId\":\"comp\",\"eventType\":\"action\",\"payload\":{\"value\":1}}"));

        assertEquals("payload values must be strings", ex.getMessage());
    }

    @Test
    void rejectsPayloadWithNonObject() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode("{\"componentId\":\"comp\",\"eventType\":\"action\",\"payload\":\"nope\"}"));

        assertEquals("payload must be an object", ex.getMessage());
    }

    @Test
    void handlesEmptyPayloadObject() {
        BrowserEvent decoded = codec.decode("{\"componentId\":\"comp\",\"eventType\":\"action\",\"payload\":{}}");

        assertEquals(0, decoded.getPayload().size());
    }

    @Test
    void handlesMissingPayload() {
        BrowserEvent decoded = codec.decode("{\"componentId\":\"comp\",\"eventType\":\"action\"}");

        assertNull(decoded.getPayload());
    }

    @Test
    void handlesNullPayloadField() {
        BrowserEvent decoded = codec.decode("{\"componentId\":\"comp\",\"eventType\":\"action\",\"payload\":null}");

        assertNull(decoded.getPayload());
    }

    @Test
    void rejectsMissingEventType() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode("{\"componentId\":\"comp\"}"));

        assertEquals("eventType is required", ex.getMessage());
    }

    @Test
    void rejectsNonStringEventType() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode("{\"componentId\":\"comp\",\"eventType\":2}"));

        assertEquals("eventType must be a string", ex.getMessage());
    }

    @Test
    void rejectsNullEventType() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode("{\"componentId\":\"comp\",\"eventType\":null}"));

        assertEquals("eventType is required", ex.getMessage());
    }

    @Test
    void rejectsBlankEventType() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode("{\"componentId\":\"comp\",\"eventType\":\" \"}"));

        assertEquals("eventType is required", ex.getMessage());
    }

    @Test
    void rejectsNullComponentId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.decode("{\"componentId\":null,\"eventType\":\"action\"}"));

        assertEquals("componentId is required", ex.getMessage());
    }

    @Test
    void rejectsNullEncode() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> codec.encode(null));

        assertEquals("event is required", ex.getMessage());
    }
}
