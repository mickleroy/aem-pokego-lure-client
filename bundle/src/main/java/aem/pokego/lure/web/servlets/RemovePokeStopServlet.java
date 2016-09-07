
package aem.pokego.lure.web.servlets;

import aem.pokego.lure.exceptions.PokeStopManageException;
import aem.pokego.lure.services.PokeStopService;
import java.io.IOException;
import javax.annotation.Nonnull;
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

import static aem.pokego.lure.util.Constants.CONTENT_TYPE_APPLICATION_JSON;

@Component(immediate = true)
@SlingServlet(
        description         = "Removes a pokestop to manage",
        label               = "PokeGoLure - Remove PokeStop Servlet",
        paths               = { "/bin/pokego/pokestop/remove" },
        methods             = { "POST" },
        generateComponent   = false
)
public class RemovePokeStopServlet extends SlingAllMethodsServlet {

    @Reference
    private PokeStopService pokeStopService;
    
    private static final Logger log = LoggerFactory.getLogger(AddPokeStopServlet.class);
    
    @Override
    protected void doPost(@Nonnull SlingHttpServletRequest  request,
                          @Nonnull SlingHttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");

        response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
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