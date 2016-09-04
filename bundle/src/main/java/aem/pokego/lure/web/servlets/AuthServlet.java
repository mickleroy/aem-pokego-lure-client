package aem.pokego.lure.web.servlets;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.auth.CredentialProvider;
import com.pokegoapi.auth.GoogleAutoCredentialProvider;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;
import okhttp3.OkHttpClient;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.servlet.ServletException;
import java.io.IOException;

import static aem.pokego.lure.util.Constants.CONTENT_TYPE_APPLICATION_JSON;

@SlingServlet(
        label       = "PokeGoLures Authentication Servlet",
        description = "Handles authentication with the Pokemon Go API",
        paths       = { "/bin/pokego/auth" },
        methods     = { "POST" }
)
public class AuthServlet extends SlingAllMethodsServlet {

    private Logger log = LoggerFactory.getLogger(AuthServlet.class);

    @Override
    protected void doPost(@Nonnull SlingHttpServletRequest  request,
                          @Nonnull SlingHttpServletResponse response) throws ServletException, IOException {

        response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        JSONObject json = new JSONObject();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {

            OkHttpClient http = new OkHttpClient();
            CredentialProvider credentialProvider = new GoogleAutoCredentialProvider(http, username, password);
            PokemonGo go = new PokemonGo(credentialProvider, http);

        } catch (LoginFailedException|RemoteServerException e) {
            e.printStackTrace();
        }

        response.getWriter().write(json.toString());
    }
}
