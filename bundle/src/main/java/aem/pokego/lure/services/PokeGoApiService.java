package aem.pokego.lure.services;

import com.pokegoapi.api.PokemonGo;

/**
 * Created by siebes on 10/09/2016.
 */
public interface PokeGoApiService {

    boolean login(String username, String password);
    PokemonGo getApi();

}
