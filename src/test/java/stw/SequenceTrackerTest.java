package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SequenceTrackerTest {
    @Test
    void incrementsSequencesPerComponent() {
        SequenceTracker tracker = new SequenceTracker();

        assertEquals(1L, tracker.nextSequence("comp"));
        assertEquals(2L, tracker.nextSequence("comp"));
        assertEquals(1L, tracker.nextSequence("other"));
        assertEquals(2L, tracker.getCurrentSequence("comp"));
    }

    @Test
    void rejectsBlankComponentId() {
        SequenceTracker tracker = new SequenceTracker();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> tracker.nextSequence(" "));

        assertEquals("componentId is required", ex.getMessage());
    }

    @Test
    void rejectsNullComponentId() {
        SequenceTracker tracker = new SequenceTracker();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> tracker.nextSequence(null));

        assertEquals("componentId is required", ex.getMessage());
    }

    @Test
    void rejectsBlankComponentIdForCurrentSequence() {
        SequenceTracker tracker = new SequenceTracker();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> tracker.getCurrentSequence(" "));

        assertEquals("componentId is required", ex.getMessage());
    }

    @Test
    void rejectsNullComponentIdForCurrentSequence() {
        SequenceTracker tracker = new SequenceTracker();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> tracker.getCurrentSequence(null));

        assertEquals("componentId is required", ex.getMessage());
    }
}
