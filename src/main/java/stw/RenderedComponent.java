package stw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RenderedComponent {
    private final String tag;
    private final Map<String, String> attributes;
    private final List<RenderedComponent> children;
    private final String textContent;

    public RenderedComponent(String tag, Map<String, String> attributes, List<RenderedComponent> children, String textContent) {
        this.tag = Objects.requireNonNull(tag, "tag");
        this.attributes = Collections.unmodifiableMap(new LinkedHashMap<>(Objects.requireNonNullElse(attributes, Map.of())));
        this.children = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNullElse(children, List.of())));
        this.textContent = textContent == null ? "" : textContent;
    }

    public String getTag() {
        return tag;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public List<RenderedComponent> getChildren() {
        return children;
    }

    public String getTextContent() {
        return textContent;
    }

    public String toHtml() {
        String attrs = attributes.entrySet().stream()
            .map(entry -> entry.getKey() + "=\"" + escape(entry.getValue()) + "\"")
            .collect(Collectors.joining(" "));
        StringBuilder builder = new StringBuilder();
        builder.append('<').append(tag);
        if (!attrs.isEmpty()) {
            builder.append(' ').append(attrs);
        }
        builder.append('>');
        if (!textContent.isEmpty()) {
            builder.append(escape(textContent));
        }
        for (RenderedComponent child : children) {
            builder.append(child.toHtml());
        }
        builder.append("</").append(tag).append('>');
        return builder.toString();
    }

    private static String escape(String value) {
        return value
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }
}
