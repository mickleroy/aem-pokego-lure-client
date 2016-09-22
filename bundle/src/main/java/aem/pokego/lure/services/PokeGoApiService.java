package aem.pokego.lure.services;

import aem.pokego.lure.models.PokeStop;
import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.map.fort.Pokestop;

import java.util.List;

public interface PokeGoApiService {

    boolean login(String username, String password);
    PokemonGo getApi();
    Pokestop getPokeStop(PokeStop pokeStop);
    String getTrainerName();
    List<PokeStop> getNearbyPokestops(double latitude, double longitude, double altitude);
    int luresInInventory();

}
