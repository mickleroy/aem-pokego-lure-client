package aem.pokego.lure.services.impl;

import aem.pokego.lure.services.PokeGoApiService;
import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.auth.CredentialProvider;
import com.pokegoapi.auth.PtcCredentialProvider;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by siebes on 10/09/2016.
 */
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
}
