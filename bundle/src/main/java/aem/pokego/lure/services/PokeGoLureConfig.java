
package aem.pokego.lure.services;


public interface PokeGoLureConfig {
        
    /**
     * Returns the Google Maps API Key configured.
     * @return 
     */
    String getMapsApiKey();
    
    /**
     * Returns the subservice name to be used for the system user "pokestop-service"
     * @return 
     */
    String getSubserviceName();

}
