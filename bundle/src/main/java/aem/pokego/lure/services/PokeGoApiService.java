package aem.pokego.lure.services;

import aem.pokego.lure.models.PokeStop;
import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.map.fort.Pokestop;

public interface PokeGoApiService {

    boolean login(String username, String password);
    PokemonGo getApi();
    Pokestop getPokeStop(PokeStop pokeStop);
    int luresInInventory();

}
