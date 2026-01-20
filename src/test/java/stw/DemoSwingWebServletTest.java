package stw;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DemoSwingWebServletTest {
    @Test
    void rendersDemoPage() {
        DemoSwingWebServlet servlet = new DemoSwingWebServlet();

        String html = servlet.renderPage();

        assertTrue(html.contains("demo-panel"));
        assertTrue(html.contains("demo-button"));
        assertTrue(html.contains("demo-input"));
        assertTrue(html.contains("demo-label"));
    }
}
