package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class EventMappingRegistryTest {
    @Test
    void exposesDefaultMappings() {
        EventMappingRegistry registry = EventMappingRegistry.defaultRegistry();

        assertTrue(registry.browserEventsFor(EventType.ACTION).contains("click"));
        assertTrue(registry.browserEventsFor(EventType.KEY).contains("keydown"));
        assertTrue(registry.browserEventsFor(EventType.DOCUMENT).contains("input"));
    }

    @Test
    void rejectsMissingMappings() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> new EventMappingRegistry(Map.of(EventType.ACTION, List.of("click"))));

        assertEquals("Missing browser mapping for MOUSE", ex.getMessage());
    }

    @Test
    void rejectsEmptyEventList() {
        Map<EventType, List<String>> mappings = Map.of(
            EventType.ACTION, List.of(),
            EventType.MOUSE, List.of("click"),
            EventType.KEY, List.of("keydown"),
            EventType.FOCUS, List.of("focus"),
            EventType.DOCUMENT, List.of("input"),
            EventType.CHANGE, List.of("change"),
            EventType.ITEM, List.of("change"),
            EventType.COMPONENT, List.of("resize"),
            EventType.HIERARCHY, List.of("domnodeinserted")
        );

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> new EventMappingRegistry(mappings));

        assertEquals("Missing browser mapping for ACTION", ex.getMessage());
    }

    @Test
    void returnsUnmodifiableMappings() {
        EventMappingRegistry registry = EventMappingRegistry.defaultRegistry();

        List<String> events = registry.browserEventsFor(EventType.ACTION);

        assertThrows(UnsupportedOperationException.class, () -> events.add("submit"));
    }

    @Test
    void rejectsNullRegistry() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> new EventMappingRegistry(null));

        assertEquals("mappings is required", ex.getMessage());
    }

    @Test
    void rejectsNullEventTypeLookup() {
        EventMappingRegistry registry = EventMappingRegistry.defaultRegistry();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> registry.browserEventsFor(null));

        assertEquals("type is required", ex.getMessage());
    }
}
