
package aem.pokego.lure.services.impl;

import aem.pokego.lure.services.PokeGoLureConfig;
import java.util.Map;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.PropertiesUtil;

/**
 * This service is used to hold configuration necessary for the Pokemon Go Lure 
 * service.
 * 
 * For more information on obtaining a Google Maps API key, visit:
 * https://developers.google.com/maps/documentation/javascript
 */
@Service(PokeGoLureConfig.class)
@Component(
        enabled = true, 
        immediate = true,
        metatype = true,
        label = "Pokemon Go Lure - Configuration"
)
public class PokeGoLureConfigImpl implements PokeGoLureConfig {
    
    @Property(label="Google Maps API key", description="A key for the Javascript Google Maps API")
    private static final String PROP_MAPS_KEY = "pokegolure.maps.key";
    
    private String googleMapsApiKey;

    @Property(label="Subservice Name", description="The system user name for the lure service (default \"pokestop\")")
    private static final String SUBSERVICE_NAME = "pokegolure.subservice.name";

    private String subserviceName;
    
    @Activate
    protected void activate(final Map<String, String> props) throws Exception {
        this.googleMapsApiKey = PropertiesUtil.toString(props.get(PROP_MAPS_KEY), "");
        this.subserviceName = PropertiesUtil.toString(props.get(SUBSERVICE_NAME), "pokestop");

    }

    @Override
    public String getMapsApiKey() {
        return this.googleMapsApiKey;
    }

    @Override
    public String getSubserviceName() {
        return this.subserviceName;
    }
}
