package examples;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

/**
 * Hit this with: curl -v http://localhost:8090/terminate
 */
class JettyTestServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(80);
        server.setConnectors(new Connector[] { connector });

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");
        contextHandler.addServlet(TerminateConnectionServlet.class, "/terminate");
        server.setHandler(contextHandler);

        server.start();
    }

    public static class TerminateConnectionServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setStatus(SC_OK);
            response.setHeader("foo", "bar");

            PrintWriter writer = response.getWriter();
            writer.println("response body line 1");
            response.flushBuffer(); //make sure Jetty isn't buffering the response

            System.out.println("setting response.sendError(-1);");
            response.sendError(-1);
            System.out.println("Response error sent");
        }
    }
}