package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.utility;

import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.entity.Pokemon;

public interface PokemonSelectionListener {
    void onPokemonAdded(Pokemon pokemon);
    void onPokemonRemoved(Pokemon pokemon);
}
