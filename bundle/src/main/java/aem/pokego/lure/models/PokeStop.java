package aem.pokego.lure.models;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.pokegoapi.api.map.fort.Pokestop;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class PokeStop {
    
    @Inject
    private String id;
    @Inject
    private Double latitude;
    @Inject
    private Double longitude;
    @Inject
    private String address;

    public PokeStop() {}
    
    /**
     * Static factory used to build a PokeStop instance from a request.
     * @param request
     * @return 
     */
    public static PokeStop fromHttpRequest(HttpServletRequest request) {
        PokeStop stop = new PokeStop();
        stop.setId(request.getParameter("id"));
        stop.setLatitude(Double.parseDouble(request.getParameter("latitude")));
        stop.setLongitude(Double.parseDouble(request.getParameter("longitude")));
        stop.setAddress(request.getParameter("address"));
        return stop;
    }

    /**
     * Static factory used to build a PokeStop instance from a PokeGOApi Object.
     * @param pokestop
     * @return
     */
    public static PokeStop fromPokeGoApi(Pokestop pokestop){
        PokeStop stop = new PokeStop();
        stop.setId(pokestop.getId());
        stop.setLatitude(pokestop.getLatitude());
        stop.setLongitude(pokestop.getLongitude());
        return stop;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Instance method to convert the properties of a PokeStop to a Map.
     * @return 
     */
    public Map<String, Object> toProps() {
        Map<String, Object> props = new HashMap<>();
        props.put("id", this.id);
        props.put("latitude", this.latitude);
        props.put("longitude", this.longitude);
        props.put("address", this.address);
        return props;
    }
    
    /**
     * Instance method to convert the properties of a PokeStop to a JSON object.
     * @return 
     */
    public JSONObject toJSON() {
        return new JSONObject(this.toProps());
    }
}
