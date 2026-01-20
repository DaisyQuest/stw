package stw;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SequenceTracker {
    private final Map<String, Long> sequences = new ConcurrentHashMap<>();

    public long nextSequence(String componentId) {
        if (componentId == null || componentId.isBlank()) {
            throw new IllegalArgumentException("componentId is required");
        }
        return sequences.merge(componentId, 1L, Long::sum);
    }

    long getCurrentSequence(String componentId) {
        if (componentId == null || componentId.isBlank()) {
            throw new IllegalArgumentException("componentId is required");
        }
        return sequences.getOrDefault(componentId, 0L);
    }
}
