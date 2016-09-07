package aem.pokego.lure.services.impl;

import aem.pokego.lure.models.PokeStop;
import aem.pokego.lure.services.PokeGoLureConfig;
import aem.pokego.lure.services.PokeStopService;
import org.apache.felix.scr.annotations.*;
import aem.pokego.lure.services.LureService;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * This service is used to attach lures to Pokestops specified in the JCR
 */
@Component
@Service(value = Runnable.class)
@Property(name = "scheduler.expression", value = "0 0/5 * 1/1 * ?")
public class LureServiceImpl implements Runnable, LureService {

    private static final Logger log = LoggerFactory.getLogger(LureServiceImpl.class);

    @Reference
    private PokeGoLureConfig pokeGoLureConfig;

    @Reference
    private PokeStopService pokeStopService;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public void run() {
        addLures();
    }


    private void addLures(){
        ResourceResolver resourceResolver = getResourceResolver();
        if(resourceResolver != null) {
            try {

                for(PokeStop pokeStop : pokeStopService.findAll(resourceResolver)){
                    // !pokestop.activeLure()
                    //      //pokestop.activateLure()
                }

            } catch(Exception e){
                log.error("unable to add lures", e);
            }
        }

        if(resourceResolver != null && resourceResolver.isLive()){
            resourceResolver.close();
        }
    }

    private ResourceResolver getResourceResolver(){
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, pokeGoLureConfig.getSubserviceName());
        ResourceResolver resolver = null;
//        try {
//            resolver = resolverFactory.getServiceResourceResolver(param);
//        } catch (LoginException e) {
//            log.error("Unable to get CRX User LoginException", e);
//        } catch (Exception e){
//            log.error("Unable to get CRX User Exception", e);
//        }
        return resolver;
    }

}
