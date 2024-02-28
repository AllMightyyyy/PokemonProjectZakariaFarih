package cesur.zakaria.pokemonprojectzakariafarih.controllers;

import cesur.zakaria.pokemonprojectzakariafarih.gameplay.Pokemon;

import java.util.List;

public class CaptureMechanism {
    private Pokemon currentPokemon;
    private int numberOfPokeballs = 20;
    private List<String> prohibitedWords;

    // Constructor
    public CaptureMechanism(List<String> prohibitedWords) {
        this.prohibitedWords = prohibitedWords;
        // Assume currentPokemon is initialized elsewhere or in this constructor
    }

    // Methods
    public void randomizePokemon() {
        // Generates a new random Pokémon for capture
    }

    public boolean tryCapture() {
        // Attempts to capture the current Pokemon
        // Uses one Pokeball and has a 2/3 chance of success
        // Returns true if successful, false otherwise
        return false; // Placeholder return
    }

    public void addPokemonToBox(Pokemon pokemon) {
        // Adds the captured Pokémon to the player's storage box
    }

    public boolean validateNickname(String nickname) {
        // Validates the nickname against the list of prohibited words
        // Ensures it contains only letters (no spaces or numbers)
        return false; // Placeholder return
    }

    public void purchasePokeballs(int quantity) {
        // Increases the numberOfPokeballs by the specified amount
    }

    // Getters and Setters
    // Include getters and setters for all attributes
}
