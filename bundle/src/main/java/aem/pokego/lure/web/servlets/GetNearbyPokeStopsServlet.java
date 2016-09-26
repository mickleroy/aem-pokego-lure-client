
package aem.pokego.lure.web.servlets;

import aem.pokego.lure.models.PokeStop;
import aem.pokego.lure.services.PokeGoApiService;
import aem.pokego.lure.services.impl.PokeGoApiServiceImpl;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component(enabled = true, immediate = true)
@SlingServlet(
        description = "Retrieves all the nearby PokeStops",
        label = "PokeGoLure - Get Nearby PokeStops Servlet",
        metatype = false,
        generateComponent = false,
        methods = {"GET"},
        paths = {"/bin/pokego/nearbypokestops"}
)
public class GetNearbyPokeStopsServlet extends SlingSafeMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(GetNearbyPokeStopsServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        final PokeGoApiService pokeGoApiService = PokeGoApiServiceImpl.getInstance();

        Double latitude = Double.parseDouble(request.getParameter("latitude"));
        Double longitude = Double.parseDouble(request.getParameter("longitude"));

        response.setContentType("application/json");
        JSONObject jsonResp = new JSONObject();

        try {
            List<PokeStop> pokeStops = pokeGoApiService.getNearbyPokestops(latitude, longitude, 0.0);
            JSONArray jsonArray = new JSONArray();
            for(PokeStop stop : pokeStops) {
                jsonArray.put(stop.toJSON());
            }
            jsonResp.put("nearbyStops", jsonArray);
        } catch (JSONException ex) {
            log.error("Could not get nearby PokeStops", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(jsonResp.toString());
    }
}
