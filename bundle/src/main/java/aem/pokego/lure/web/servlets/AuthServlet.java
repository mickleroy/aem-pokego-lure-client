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

    private static final String POST_PARAM_TOKEN    = "token";
    private static final String POST_PARAM_USERNAME = "username";
    private static final String POST_PARAM_PASSWORD = "password";
    private static final String GET_PARAM_LOGOUT    = "logout";

    @Override
    protected void doPost(@Nonnull SlingHttpServletRequest  request,
                          @Nonnull SlingHttpServletResponse response) throws ServletException, IOException {

        response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        JSONObject json = new JSONObject();

        String token    = request.getParameter(POST_PARAM_TOKEN);
        String username = request.getParameter(POST_PARAM_USERNAME);
        String password = request.getParameter(POST_PARAM_PASSWORD);

        PokeGoApiService api = PokeGoApiServiceImpl.getInstance();

        int status = HttpServletResponse.SC_UNAUTHORIZED;

        /* Attempt login with token first, if available. Fallback to PTC username and password. */
        if ((token != null && api.login(token)) || api.login(username, password)) {
            status = HttpServletResponse.SC_ACCEPTED;
        }

        response.setStatus(status);
        response.getWriter().write(json.toString());
    }

    @Override
    protected void doGet(@Nonnull SlingHttpServletRequest  request,
                         @Nonnull SlingHttpServletResponse response) throws ServletException, IOException {

        if (request.getRequestParameterMap().containsKey(GET_PARAM_LOGOUT)) {
            PokeGoApiServiceImpl.getInstance().logout();
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
