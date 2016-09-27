package aem.pokego.lure.web.servlets;

import aem.pokego.lure.services.PokeGoApiService;
import aem.pokego.lure.services.impl.PokeGoApiServiceImpl;
import com.pokegoapi.api.PokemonGo;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static aem.pokego.lure.util.Constants.CONTENT_TYPE_APPLICATION_JSON;

@Component(immediate = true)
@SlingServlet(
        label               = "PokeGoLures Authentication Servlet",
        description         = "Handles authentication with the Pokemon Go API",
        paths               = { "/bin/pokego/auth" },
        methods             = { "POST" },
        generateComponent   = false
)
public class AuthServlet extends SlingAllMethodsServlet {

    private Logger log = LoggerFactory.getLogger(AuthServlet.class);

    @Override
    protected void doPost(@Nonnull SlingHttpServletRequest  request,
                          @Nonnull SlingHttpServletResponse response) throws ServletException, IOException {

        response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        JSONObject json = new JSONObject();

        String token    = request.getParameter("token");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        PokeGoApiService api = PokeGoApiServiceImpl.getInstance();

        if (token != null) {
            if (api.login(token)) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            if (api.login(username, password)) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        response.getWriter().write(json.toString());
    }
}
