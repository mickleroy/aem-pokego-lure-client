package aem.pokego.lure.services;

import aem.pokego.lure.exceptions.PokeStopManageException;
import aem.pokego.lure.models.PokeStop;
import java.util.List;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * This service is responsible for performing operations on the managed 
 * PokeStops within the JCR.
 */
public interface PokeStopService {
    
    /**
     * Retrieves the entire list of managed PokeStops from the JCR.
     * @param resolver
     * @return
     * @throws PokeStopManageException 
     */
    List<PokeStop> findAll(ResourceResolver resolver) throws PokeStopManageException;
    
    /**
     * Adds a PokeStop node within the JCR under the page content.
     * 
     * @param resolver
     * @param pokestop
     * @throws PokeStopManageException 
     */
    void addPokeStop(ResourceResolver resolver, PokeStop pokestop) throws PokeStopManageException;
    
    /**
     * Removes a PokeStop from the list of managed PokeStops.
     * 
     * @param resolver
     * @param id
     * @throws PokeStopManageException 
     */
    void removePokeStop(ResourceResolver resolver, String id) throws PokeStopManageException;
}
