package aem.pokego.lure.services.impl;

import POGOProtos.Inventory.Item.ItemIdOuterClass;
import aem.pokego.lure.models.PokeStop;
import aem.pokego.lure.services.PokeGoApiService;
import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.inventory.Item;
import com.pokegoapi.api.map.fort.Pokestop;
import com.pokegoapi.auth.CredentialProvider;
import com.pokegoapi.auth.PtcCredentialProvider;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokeGoApiServiceImpl implements PokeGoApiService{

    private static final Logger log = LoggerFactory.getLogger(PokeGoApiServiceImpl.class);
    private static PokeGoApiService instance;

    private PokemonGo api;


    public static PokeGoApiService getInstance() {
        if(instance == null){
            synchronized (PokeGoApiServiceImpl.class) {
                if(instance == null) {
                    instance = new PokeGoApiServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean login(String username, String password) {
        OkHttpClient http = new OkHttpClient();
        try {
            CredentialProvider credentialProvider = new PtcCredentialProvider(http, username, password);
            api = new PokemonGo(credentialProvider, http);
        } catch (LoginFailedException | RemoteServerException e) {
            return false;
        }
        return true;
    }

    @Override
    public PokemonGo getApi() {
        return api;
    }

    @Override
    public Pokestop getPokeStop(PokeStop pokeStop){
        PokemonGo api = getApi();
        if(api != null) {
            api.setLocation(pokeStop.getLatitude(), pokeStop.getLongitude(), 0);
            try {
                for (Pokestop stop : api.getMap().getMapObjects().getPokestops()) {
                    if (stop.getId().equals(pokeStop.getId())) {
                        return stop;
                    }
                }
            } catch (Exception e) {
                log.error("Unable to get pokestop", e);
            }
        }
        return null;
    }

    @Override
    public int luresInInventory() {
        PokemonGo api = PokeGoApiServiceImpl.getInstance().getApi();
        if(api != null){
            try {
                for (Item item : api.getInventories().getItemBag().getItems()) {
                    if (ItemIdOuterClass.ItemId.ITEM_TROY_DISK.equals(item.getItemId())) {
                        return item.getCount();
                    }
                }
            }catch(Exception e){
                log.error("Unable to get inventory", e);
            }
        }
        return 0;
    }

}
