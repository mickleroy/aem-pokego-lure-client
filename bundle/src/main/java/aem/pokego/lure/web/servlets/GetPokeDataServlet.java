
package aem.pokego.lure.web.servlets;

import aem.pokego.lure.services.PokeGoApiService;
import aem.pokego.lure.services.impl.PokeGoApiServiceImpl;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(enabled = true, immediate = true)
@SlingServlet(
        description = "Retrieves PokeData to manage",
        label = "PokeGoLure - Get User Data Servlet",
        metatype = false,
        generateComponent = false,
        methods = {"GET"},
        paths = {"/bin/pokego/pokedata"}
)
public class GetPokeDataServlet extends SlingSafeMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(AddPokeStopServlet.class);
    
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) 
                                                throws ServletException, IOException {
        final PokeGoApiService pokeGoApiService = PokeGoApiServiceImpl.getInstance();

        response.setContentType("application/json");
        JSONObject jsonResp = new JSONObject();
        
        try {
            jsonResp.put("username", pokeGoApiService.getTrainerName());
            jsonResp.put("luresLeft", pokeGoApiService.luresInInventory());
        } catch (JSONException ex) {
            log.error("Could not get PokeData", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(jsonResp.toString());
    }
}
