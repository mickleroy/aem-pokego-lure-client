package aem.pokego.lure.servlets;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.servlet.ServletException;
import java.io.IOException;

@SlingServlet(
        label       = "PokeGoLures Authentication Servlet",
        description = "Handles authentication with the Pokemon Go API",
        paths       = { "/pokego/auth" },
        methods     = { "POST" },
        metatype    = true
)
public class AuthServlet extends SlingAllMethodsServlet {

    private Logger log = LoggerFactory.getLogger(AuthServlet.class);

    @Override
    protected void doPost(@Nonnull SlingHttpServletRequest  request,
                          @Nonnull SlingHttpServletResponse response) throws ServletException, IOException {

        // TODO - implement auth handling
        log.info("PokeGoLures - Authorisation: service not implemented.");
    }
}
