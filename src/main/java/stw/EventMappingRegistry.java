package stw;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class EventMappingRegistry {
    private final EnumMap<EventType, List<String>> mappings;

    public EventMappingRegistry(Map<EventType, List<String>> mappings) {
        if (mappings == null) {
            throw new IllegalArgumentException("mappings is required");
        }
        EnumMap<EventType, List<String>> copy = new EnumMap<>(EventType.class);
        for (EventType type : EventType.values()) {
            List<String> events = mappings.get(type);
            if (events == null || events.isEmpty()) {
                throw new IllegalArgumentException("Missing browser mapping for " + type);
            }
            copy.put(type, List.copyOf(events));
        }
        this.mappings = copy;
    }

    public static EventMappingRegistry defaultRegistry() {
        return new EventMappingRegistry(Map.of(
            EventType.ACTION, List.of("click", "keydown.enter"),
            EventType.MOUSE, List.of("click", "dblclick", "mousedown", "mouseup", "mousemove", "drag", "wheel"),
            EventType.KEY, List.of("keydown", "keyup", "keypress"),
            EventType.FOCUS, List.of("focus", "blur"),
            EventType.DOCUMENT, List.of("input", "change"),
            EventType.CHANGE, List.of("input", "change"),
            EventType.ITEM, List.of("change"),
            EventType.COMPONENT, List.of("resize", "visibilitychange"),
            EventType.HIERARCHY, List.of("domnodeinserted", "domnoderemoved")
        ));
    }

    public List<String> browserEventsFor(EventType type) {
        if (type == null) {
            throw new IllegalArgumentException("type is required");
        }
        return mappings.get(type);
    }
}
