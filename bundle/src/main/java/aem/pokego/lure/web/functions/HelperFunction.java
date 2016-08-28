
package aem.pokego.lure.web.functions;

import aem.pokego.lure.services.PokeGoLureConfig;
import org.apache.sling.api.scripting.SlingScriptHelper;

public final class HelperFunction {
    
    private HelperFunction() {}
    
    public static String getMapsApiKey(SlingScriptHelper sling) {
        PokeGoLureConfig configService = sling.getService(PokeGoLureConfig.class);
        if(configService != null) {
            return configService.getMapsApiKey();
        }
        return null;
    }
}
