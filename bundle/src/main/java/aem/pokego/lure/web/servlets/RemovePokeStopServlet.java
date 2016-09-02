
package aem.pokego.lure.web.servlets;

import aem.pokego.lure.exceptions.PokeStopManageException;
import aem.pokego.lure.services.PokeStopService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(enabled = true, immediate = true)
@SlingServlet(
        description = "Removes a pokestop to manage", 
        label = "PokeGoLure - Remove PokeStop Servlet",
        metatype = false,
        generateComponent = false,
        methods = {"POST"},
        paths = {"/bin/pokego/remove-pokestop"}
)
public class RemovePokeStopServlet extends SlingAllMethodsServlet {

    @Reference
    private PokeStopService pokeStopService;
    
    private static final Logger log = LoggerFactory.getLogger(AddPokeStopServlet.class);
    
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) 
                                                throws ServletException, IOException {
        String id = request.getParameter("id");
        response.setContentType("application/json");
        JSONObject jsonResp = new JSONObject();
        
        try {            
            pokeStopService.removePokeStop(request.getResourceResolver(), id);
        } catch (PokeStopManageException ex) {
            log.error("Could not store PokeStop", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(jsonResp.toString());
    }
}