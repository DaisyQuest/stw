package stw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import javax.swing.JLabel;
import org.junit.jupiter.api.Test;

class SwingWebServletTest {
    @Test
    void rendersHtmlResponse() throws Exception {
        SwingWebServletConfig config = SwingWebServletConfig.builder()
            .rootComponentSupplier(() -> new JLabel("Root"))
            .build();
        SwingWebServlet servlet = new SwingWebServlet(config, new BasicSwingRenderer(new ComponentIdProvider()));
        StubHttpServletResponse response = new StubHttpServletResponse();

        servlet.doGet(null, response);

        assertEquals("text/html;charset=UTF-8", response.getContentType());
        String body = response.getBody();
        assertTrue(body.startsWith("<!DOCTYPE html>"));
        assertTrue(body.contains("Root"));
    }

    private static final class StubHttpServletResponse implements HttpServletResponse {
        private final StringWriter body = new StringWriter();
        private final PrintWriter writer = new PrintWriter(body);
        private String contentType;
        private Locale locale = Locale.getDefault();

        String getBody() {
            writer.flush();
            return body.toString();
        }

        @Override
        public void addCookie(Cookie cookie) {
        }

        @Override
        public boolean containsHeader(String name) {
            return false;
        }

        @Override
        public String encodeURL(String url) {
            return url;
        }

        @Override
        public String encodeRedirectURL(String url) {
            return url;
        }


        @Override
        public void sendError(int sc, String msg) {
        }

        @Override
        public void sendError(int sc) {
        }

        @Override
        public void sendRedirect(String location) {
        }

        @Override
        public void setDateHeader(String name, long date) {
        }

        @Override
        public void addDateHeader(String name, long date) {
        }

        @Override
        public void setHeader(String name, String value) {
        }

        @Override
        public void addHeader(String name, String value) {
        }

        @Override
        public void setIntHeader(String name, int value) {
        }

        @Override
        public void addIntHeader(String name, int value) {
        }

        @Override
        public void setStatus(int sc) {
        }


        @Override
        public int getStatus() {
            return 200;
        }

        @Override
        public String getHeader(String name) {
            return null;
        }

        @Override
        public Collection<String> getHeaders(String name) {
            return Collections.emptyList();
        }

        @Override
        public Collection<String> getHeaderNames() {
            return Collections.emptyList();
        }

        @Override
        public String getCharacterEncoding() {
            return "UTF-8";
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            throw new UnsupportedOperationException("getOutputStream not supported in tests");
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

        @Override
        public void setCharacterEncoding(String charset) {
        }

        @Override
        public void setContentLength(int len) {
        }

        @Override
        public void setContentLengthLong(long len) {
        }

        @Override
        public void setContentType(String type) {
            this.contentType = type;
        }

        @Override
        public void setBufferSize(int size) {
        }

        @Override
        public int getBufferSize() {
            return 0;
        }

        @Override
        public void flushBuffer() throws IOException {
        }

        @Override
        public void resetBuffer() {
        }

        @Override
        public boolean isCommitted() {
            return false;
        }

        @Override
        public void reset() {
        }

        @Override
        public void setLocale(Locale loc) {
            this.locale = loc;
        }

        @Override
        public Locale getLocale() {
            return locale;
        }
    }
}
