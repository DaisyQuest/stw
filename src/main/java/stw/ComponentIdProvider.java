package stw;

import java.awt.Component;
import java.util.Objects;

public final class ComponentIdProvider {
    public String componentId(Component component) {
        Objects.requireNonNull(component, "component");
        String name = component.getName();
        if (name != null && !name.isBlank()) {
            return name.trim();
        }
        return "component-" + System.identityHashCode(component);
    }
}
