package aem.pokego.lure.models;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.pokegoapi.api.map.fort.FortDetails;
import com.pokegoapi.api.map.fort.Pokestop;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;
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
    @Inject
    private String imageUrl;
    @Inject
    private String description;

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
        stop.setDescription(request.getParameter("description"));
        return stop;
    }

    /**
     * Static factory used to build a PokeStop instance from a PokeGOApi Object.
     * @param pokestop
     * @return
     */
    public static PokeStop fromPokeGoApi(Pokestop pokestop) {
        PokeStop stop = new PokeStop();
        try {
            FortDetails fortDetails = pokestop.getDetails();
            stop.setImageUrl(fortDetails.getImageUrl().get(0));
            stop.setDescription(fortDetails.getDescription());

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        props.put("imageUrl", this.imageUrl);
        props.put("description", this.description);
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
