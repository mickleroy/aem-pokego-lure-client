package aem.pokego.lure.services.impl;

import aem.pokego.lure.exceptions.PokeStopManageException;
import aem.pokego.lure.models.PokeStop;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import aem.pokego.lure.services.PokeStopService;
import aem.pokego.lure.util.Constants;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service(PokeStopService.class)
@Component(
        enabled = true, 
        immediate = true,
        metatype = true,
        label = "Pokemon Go Lure - PokeStop Manage Service"
)
public class PokeStopServiceImpl implements PokeStopService {
    
    private static final Logger log = LoggerFactory.getLogger(PokeStopServiceImpl.class);
    
    @Override
    public List<PokeStop> findAll(ResourceResolver resolver) throws PokeStopManageException {
        Resource resource = resolver.getResource(Constants.POKESTOP_PATH);
        
        if(resource != null) {
            List<PokeStop> pokeStops = new ArrayList<>();
            for(Resource child : resource.getChildren()) {
                pokeStops.add(child.adaptTo(PokeStop.class));
            }
            return pokeStops;
        } else {
            throw new PokeStopManageException("Could not find resource at path " + Constants.POKESTOP_PATH);
        }
    }
    
    @Override
    public void addPokeStop(ResourceResolver resolver, PokeStop pokestop) throws PokeStopManageException {
        Resource resource = resolver.getResource(Constants.POKESTOP_PATH);
        
        if(resource != null) {
            try {
                resolver.create(resource, pokestop.getId(), pokestop.toProps());
                resolver.commit();
            } catch (PersistenceException | UnsupportedOperationException ex) {
                throw new PokeStopManageException("Could not persist node to JCR", ex);
            } finally {
                resolver.close();
            }
        } else {
            throw new PokeStopManageException("Could not find resource at path " + Constants.POKESTOP_PATH);
        }
    }

    @Override
    public void removePokeStop(ResourceResolver resolver, String id) throws PokeStopManageException {
        Resource resource = resolver.getResource(Constants.POKESTOP_PATH);
        
        if(resource != null) {
            try {
                for(Resource child : resource.getChildren()) {
                    PokeStop pokeStop = child.adaptTo(PokeStop.class);
                    if(pokeStop != null && pokeStop.getId().equals(id)) {
                        resolver.delete(child);
                        resolver.commit();
                    }
                }
            } catch (PersistenceException | UnsupportedOperationException ex) {
                throw new PokeStopManageException("Could not remove node from JCR", ex);
            } finally {
                resolver.close();
            }
        } else {
            throw new PokeStopManageException("Could not find resource at path " + Constants.POKESTOP_PATH);
        }
    }
}
