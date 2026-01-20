package stw;

public final class DemoSwingWebServlet extends SwingWebServlet {
    public DemoSwingWebServlet() {
        super(
            SwingWebServletConfig.builder()
                .rootComponentSupplier(DemoSwingApp::createRootPanel)
                .build(),
            new BasicSwingRenderer(new ComponentIdProvider())
        );
    }
}
