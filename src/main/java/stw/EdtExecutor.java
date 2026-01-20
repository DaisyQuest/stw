package stw;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import javax.swing.SwingUtilities;

public final class EdtExecutor {
    private EdtExecutor() {
    }

    public static <T> T runOnEdtAndWait(Callable<T> action) {
        if (action == null) {
            throw new IllegalArgumentException("action is required");
        }
        if (SwingUtilities.isEventDispatchThread()) {
            return call(action);
        }
        Result<T> result = new Result<>();
        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    result.value = action.call();
                } catch (Exception ex) {
                    result.failure = ex;
                }
            });
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new EdtExecutionException("Interrupted waiting for EDT", ex);
        } catch (InvocationTargetException ex) {
            throw new EdtExecutionException("Failed executing on EDT", ex.getCause());
        }
        if (result.failure != null) {
            throw new EdtExecutionException("Failed executing on EDT", result.failure);
        }
        return result.value;
    }

    private static <T> T call(Callable<T> action) {
        try {
            return action.call();
        } catch (Exception ex) {
            throw new EdtExecutionException("Failed executing on EDT", ex);
        }
    }

    private static final class Result<T> {
        private T value;
        private Exception failure;
    }
}
