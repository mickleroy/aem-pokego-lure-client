
package aem.pokego.lure.web.functions;

import aem.pokego.lure.services.PokeGoApiService;
import aem.pokego.lure.services.PokeGoLureConfig;
import aem.pokego.lure.services.impl.PokeGoApiServiceImpl;
import com.pokegoapi.auth.GoogleCredentialProvider;
import com.pokegoapi.auth.GoogleUserCredentialProvider;
import org.apache.sling.api.scripting.SlingScriptHelper;

public final class HelperFunction {
    
    private HelperFunction() {}
    
    /**
     * Returns the Google Maps API key configured through the PokeGoLureConfig.
     * @param sling
     * @return
     */
    public static String getMapsApiKey(SlingScriptHelper sling) {
        PokeGoLureConfig configService = sling.getService(PokeGoLureConfig.class);
        if(configService != null) {
            return configService.getMapsApiKey();
        }
        return null;
    }

    /**
     * Checks whether a user is logged into the Pokemon API.
     * @param sling
     * @return
     */
    public static Boolean isLoggedIn(SlingScriptHelper sling) {
        PokeGoApiService service = PokeGoApiServiceImpl.getInstance();
        return service.getApi() != null;
    }

    public static String getGoogleLoginUrl() {
        return GoogleUserCredentialProvider.LOGIN_URL;
    }

}
