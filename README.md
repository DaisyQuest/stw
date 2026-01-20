# stw

Swing-to-web servlet adapter demo project.

## Demo

The demo servlet renders a simple Swing panel (label, text input, button) via the servlet adapter.

### Run in a Servlet Container

1. Build the project:
   ```bash
   gradle build
   ```
2. Deploy `DemoSwingWebServlet` as a servlet in your container (Jetty/Tomcat).
3. Load the servlet endpoint to view the rendered HTML output.

### Demo Components

The demo uses `DemoSwingApp.createRootPanel()` to build the Swing component tree that the renderer converts to HTML.
