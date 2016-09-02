
package aem.pokego.lure.web.servlets;

import aem.pokego.lure.exceptions.PokeStopManageException;
import aem.pokego.lure.models.PokeStop;
import aem.pokego.lure.services.PokeStopService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(enabled = true, immediate = true)
@SlingServlet(
        description = "Retrieves all the PokeStops to manage", 
        label = "PokeGoLure - Get PokeStops Servlet",
        metatype = false,
        generateComponent = false,
        methods = {"GET"},
        paths = {"/bin/pokego/pokestops"}
)
public class GetPokeStopsServlet extends SlingSafeMethodsServlet {
    
    @Reference
    private PokeStopService pokeStopService;
    
    private static final Logger log = LoggerFactory.getLogger(AddPokeStopServlet.class);
    
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) 
                                                throws ServletException, IOException {
        response.setContentType("application/json");
        JSONObject jsonResp = new JSONObject();
        
        try {            
            List<PokeStop> pokeStops = pokeStopService.findAll(request.getResourceResolver());
            JSONArray jsonArray = new JSONArray();
            for(PokeStop stop : pokeStops) {
                jsonArray.put(stop.toJSON());
            }
            jsonResp.put("stops", jsonArray);
        } catch (PokeStopManageException | JSONException ex) {
            log.error("Could not find PokeStops", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(jsonResp.toString());
    }
}
