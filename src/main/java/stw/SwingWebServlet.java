package stw;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Component;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class SwingWebServlet extends HttpServlet {
    private final SwingWebServletConfig config;
    private final SwingRenderer renderer;

    public SwingWebServlet(SwingWebServletConfig config, SwingRenderer renderer) {
        this.config = Objects.requireNonNull(config, "config");
        this.renderer = Objects.requireNonNull(renderer, "renderer");
    }

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter writer = resp.getWriter()) {
            writer.print(renderPage());
        }
    }

    String renderPage() {
        Component root = config.getRootComponentSupplier().get();
        RenderedComponent rendered = renderer.render(root);
        return "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"></head><body>" +
            rendered.toHtml() +
            "</body></html>";
    }
}
