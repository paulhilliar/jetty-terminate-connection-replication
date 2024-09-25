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
        connector.setPort(8090);
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
            writer.println("Response 1 at %s \n".formatted(Instant.now()));
            response.flushBuffer();

            response.sendError(-1);

            //I would expect response.sendError(-1); to have terminated the request and this second line not to appear
            //See: https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/http/httpservletresponse#sendError-int-
            // "After using this method, the response should be considered to be committed and should not be written to."

            writer.println("Response 2 at %s \n".formatted(Instant.now()));
            response.flushBuffer();
        }
    }
}