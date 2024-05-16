package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Pokemon;

public class PokemonStatUpdater {

    /**
     * Updates the specified stat for a Pokemon.
     * @param pokemon The Pokemon whose stat is to be updated.
     * @param statType The type of stat to update ("HP", "Attack", "Defense").
     * @param pointChange The amount by which the stat is to be updated.
     */
    public void updateStat(Pokemon pokemon, String statType, int pointChange) {
        switch (statType) {
            case "HP":
                pokemon.getStat().setPv(pokemon.getStat().getPv() + pointChange);
                break;
            case "Attack":
                pokemon.getStat().setDmg(pokemon.getStat().getDmg() + pointChange);
                break;
            case "Defense":
                pokemon.getStat().setDef(pokemon.getStat().getDef() + pointChange);
                break;
        }
    }
}
