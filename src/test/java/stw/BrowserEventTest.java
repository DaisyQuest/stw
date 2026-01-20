package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class BrowserEventTest {
    @Test
    void trimsAndStoresValues() {
        BrowserEvent event = new BrowserEvent("  comp ", " action ", Map.of(" key ", " value "));

        assertEquals("comp", event.getComponentId());
        assertEquals("action", event.getEventType());
        assertEquals(" value ", event.getPayload().get(" key "));
    }

    @Test
    void rejectsMissingComponentId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> new BrowserEvent("  ", "action", Map.of()));

        assertEquals("componentId is required", ex.getMessage());
    }

    @Test
    void rejectsMissingEventType() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> new BrowserEvent("comp", " ", Map.of()));

        assertEquals("eventType is required", ex.getMessage());
    }

    @Test
    void rejectsNullComponentId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> new BrowserEvent(null, "action", Map.of()));

        assertEquals("componentId is required", ex.getMessage());
    }

    @Test
    void rejectsNullEventType() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> new BrowserEvent("comp", null, Map.of()));

        assertEquals("eventType is required", ex.getMessage());
    }

    @Test
    void handlesNullPayload() {
        BrowserEvent event = new BrowserEvent("comp", "action", null);

        assertNull(event.getPayload());
    }
}
