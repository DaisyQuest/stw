package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class RenderedComponentTest {
    @Test
    void buildsHtmlWithEscaping() {
        RenderedComponent child = new RenderedComponent("span", Map.of(), List.of(), "child");
        RenderedComponent component = new RenderedComponent(
            "div",
            Map.of("data", "<value>"),
            List.of(child),
            "Hello & \"World\""
        );

        assertEquals(
            "<div data=\"&lt;value&gt;\">Hello &amp; &quot;World&quot;<span>child</span></div>",
            component.toHtml()
        );
    }

    @Test
    void defaultsNullCollectionsAndText() {
        RenderedComponent component = new RenderedComponent("div", null, null, null);

        assertEquals("<div></div>", component.toHtml());
        assertEquals("", component.getTextContent());
        assertEquals(0, component.getAttributes().size());
        assertEquals(0, component.getChildren().size());
    }
}
