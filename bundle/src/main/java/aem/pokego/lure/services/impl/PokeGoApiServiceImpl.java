package aem.pokego.lure.services.impl;

import POGOProtos.Inventory.Item.ItemIdOuterClass;
import aem.pokego.lure.models.PokeStop;
import aem.pokego.lure.services.PokeGoApiService;
import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.inventory.Item;
import com.pokegoapi.api.map.fort.Pokestop;
import com.pokegoapi.auth.CredentialProvider;
import com.pokegoapi.auth.GoogleCredentialProvider;
import com.pokegoapi.auth.GoogleUserCredentialProvider;
import com.pokegoapi.auth.PtcCredentialProvider;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This service provides access to the Pokemon Go Api and associated methods
 */
public class PokeGoApiServiceImpl implements PokeGoApiService{

    private static final Logger log = LoggerFactory.getLogger(PokeGoApiServiceImpl.class);
    private static PokeGoApiService instance;

    private PokemonGo api;

    /* Google OAuth refresh token storage. */
    private String refreshToken;

    /* PTC Credential storage. */
    private String username;
    private String password;


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

    /**
     * Attempts to login and generate a com.pokegoapi.api.PokemonGo instance using a Pokemon Trainer Club username + password
     * @param username
     * @param password
     * @return the result of the login (successful/unsuccessful)
     */
    @Override
    public boolean login(String username, String password) {
        OkHttpClient http = new OkHttpClient();
        try {

            CredentialProvider credentialProvider = new PtcCredentialProvider(http, username, password);

            api = new PokemonGo(credentialProvider, http);

            this.username = username;
            this.password = password;

        } catch (LoginFailedException | RemoteServerException e) {
            log.error("Could not login", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean login(String token) {
        OkHttpClient http = new OkHttpClient();

        try {

            GoogleUserCredentialProvider credentialProvider = new GoogleUserCredentialProvider(http);
            credentialProvider.login(token);

            api = new PokemonGo(credentialProvider, http);
            this.refreshToken = credentialProvider.getRefreshToken();

        } catch (LoginFailedException|RemoteServerException e) {
            log.error("Token login failed.", e);
            return false;
        }

        return true;
    }

    @Override
    public void logout() {
        this.api          = null;
        this.refreshToken = null;
        this.username     = null;
        this.password     = null;
    }

    @Override
    public boolean refreshSession() {
        OkHttpClient http = new OkHttpClient();

        if (refreshToken != null) {
            try {

                /* Reconnect to Google with stored refresh token. */
                GoogleUserCredentialProvider credentialProvider = new GoogleUserCredentialProvider(http);
                credentialProvider.refreshToken(refreshToken);
                api = new PokemonGo(credentialProvider, http);

            } catch (LoginFailedException|RemoteServerException e) {
                log.error("Token refresh failed.", e);
                return false;
            }
        } else if (username != null && password != null) {

            /* PTC re-login as normal. */
            return login(username, password);

        }

        return false;
    }

    @Override
    public PokemonGo getApi() {
        return api;
    }

    /**
     * Converts aem.pokego.lure.models.Pokestop into com.pokegoapi.api.map.fort.Pokestop
     * @param pokeStop
     * @return com.pokegoapi.api.map.fort.Pokestop
     */
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

    /**
     * Returns the Pokemon trainer name
     * @return
     */
    @Override
    public String getTrainerName() {
        PokemonGo api = PokeGoApiServiceImpl.getInstance().getApi();

        if(api != null) {
            try {
                return api.getPlayerProfile().getPlayerData().getUsername();
            } catch(Exception e) {
                log.error("Unable to get trainer data", e);
            }
        }
        return null;
    }
    /**
     * Returns the Pokemon stops
     * @return
     */
    @Override
    public List<PokeStop> getNearbyPokestops(double latitude, double longitude, double altitude) {
        PokemonGo api = PokeGoApiServiceImpl.getInstance().getApi();

        Collection<Pokestop> pokestopCollection;
        List<PokeStop> pokeStopList = new ArrayList<>();
        if(api != null) {
            try {
                api.setLocation(latitude, longitude, altitude);
                pokestopCollection = api.getMap().getMapObjects().getPokestops();

                for (Pokestop pokestop : pokestopCollection) {
                    pokeStopList.add(PokeStop.fromPokeGoApi(pokestop));
                }
            } catch(Exception e) {
                log.error("Unable to get poke stops data", e);
            }
        }
        return pokeStopList;
    }

    /**
     * Returns the amount of lures in the logged in players inventory
     * @return amount of lures in inventory
     */
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
