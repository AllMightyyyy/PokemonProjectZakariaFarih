package cesur.zakaria.pokemonprojectzakariafarih.controllers;

import cesur.zakaria.pokemonprojectzakariafarih.model.card.EnergyCard;
import cesur.zakaria.pokemonprojectzakariafarih.model.card.PokemonCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Field {
    private Map<PokemonCard, List<EnergyCard>> activePokemon = new HashMap<>();

    // Method to place a Pokémon card on the field
    public void placePokemon(PokemonCard pokemon) {
        activePokemon.putIfAbsent(pokemon, new ArrayList<>());
    }

    // Method to attach an Energy card to a Pokémon
    public void attachEnergy(PokemonCard pokemon, EnergyCard energy) {
        List<EnergyCard> energies = activePokemon.get(pokemon);
        if (energies != null) {
            energies.add(energy);
        }
    }

    // Method to remove a Pokémon from the field
    public void removePokemon(PokemonCard pokemon) {
        activePokemon.remove(pokemon);
    }

    // Method to get the active Pokémon
    public Map<PokemonCard, List<EnergyCard>> getActivePokemon() {
        return activePokemon;
    }
    // Method to play a Pokémon card onto the field
    public void playPokemon(PokemonCard pokemon) {
        if (!activePokemon.containsKey(pokemon)) {
            activePokemon.put(pokemon, new ArrayList<>());
            System.out.println(pokemon.getName() + " has been played onto the field.");
        } else {
            System.out.println("There is already a " + pokemon.getName() + " on the field.");
        }
    }

    // Additional methods as needed, such as removing knocked-out Pokémon
}
