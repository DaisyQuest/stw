package stw;

import java.awt.Component;
import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;

public final class SwingWebServletConfig {
    private final Supplier<Component> rootComponentSupplier;
    private final Duration sessionTimeout;
    private final boolean diffRenderingEnabled;

    private SwingWebServletConfig(Builder builder) {
        this.rootComponentSupplier = Objects.requireNonNull(builder.rootComponentSupplier, "rootComponentSupplier");
        this.sessionTimeout = Objects.requireNonNull(builder.sessionTimeout, "sessionTimeout");
        this.diffRenderingEnabled = builder.diffRenderingEnabled;
    }

    public Supplier<Component> getRootComponentSupplier() {
        return rootComponentSupplier;
    }

    public Duration getSessionTimeout() {
        return sessionTimeout;
    }

    public boolean isDiffRenderingEnabled() {
        return diffRenderingEnabled;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Supplier<Component> rootComponentSupplier;
        private Duration sessionTimeout = Duration.ofMinutes(20);
        private boolean diffRenderingEnabled = true;

        public Builder rootComponentSupplier(Supplier<Component> rootComponentSupplier) {
            this.rootComponentSupplier = rootComponentSupplier;
            return this;
        }

        public Builder sessionTimeout(Duration sessionTimeout) {
            if (sessionTimeout == null || sessionTimeout.isNegative() || sessionTimeout.isZero()) {
                throw new IllegalArgumentException("sessionTimeout must be positive");
            }
            this.sessionTimeout = sessionTimeout;
            return this;
        }

        public Builder diffRenderingEnabled(boolean diffRenderingEnabled) {
            this.diffRenderingEnabled = diffRenderingEnabled;
            return this;
        }

        public SwingWebServletConfig build() {
            return new SwingWebServletConfig(this);
        }
    }
}
