package cesur.zakaria.pokemonprojectzakariafarih.controllers;

import cesur.zakaria.pokemonprojectzakariafarih.gameplay.Item;
import cesur.zakaria.pokemonprojectzakariafarih.gameplay.Pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trainer {
    private String name;
    private List<Pokemon> mainTeam;
    private List<Pokemon> pokemonBox;
    private int pokeDollars;
    private Map<Item, Integer> items;

    // Constructor
    public Trainer(String name, int pokeDollars) {
        this.name = name;
        this.pokeDollars = pokeDollars;
        this.mainTeam = new ArrayList<>();
        this.pokemonBox = new ArrayList<>();
        this.items = new HashMap<>();
    }

    // Methods
    public void movePokemonToBox(Pokemon pokemon) {
        // Implementation needed
    }

    public void movePokemonToMainTeam(Pokemon pokemon) {
        // Implementation needed
    }

    public void trainPokemon(Pokemon pokemon) {
        // Implementation needed
    }

    public void capturePokemon(Pokemon pokemon) {
        // Implementation needed
    }

    public Pokemon breedPokemon(Pokemon pokemon1, Pokemon pokemon2) {
        // Implementation needed
        return null; // Placeholder return
    }

    public void buyItem(Item item, int quantity) {
        // Implementation needed
    }

    public void useItem(Pokemon pokemon, Item item) {
        // Implementation needed
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pokemon> getMainTeam() {
        return mainTeam;
    }

    public void setMainTeam(List<Pokemon> mainTeam) {
        this.mainTeam = mainTeam;
    }

    public List<Pokemon> getPokemonBox() {
        return pokemonBox;
    }

    public void setPokemonBox(List<Pokemon> pokemonBox) {
        this.pokemonBox = pokemonBox;
    }

    public int getPokeDollars() {
        return pokeDollars;
    }

    public void setPokeDollars(int pokeDollars) {
        this.pokeDollars = pokeDollars;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }
}
//trainer class