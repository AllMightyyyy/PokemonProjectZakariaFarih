package cesur.zakaria.pokemonprojectzakariafarih.gameplay;

public class Item {
    private String name;
    private String effect;

    // Constructor
    public Item(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    // Methods
    public void applyEffect(Pokemon pokemon) {
        // Applies the item's effect to the given Pokemon
        // Specific logic based on the item's effect
    }

    public void removeEffect(Pokemon pokemon) {
        // Removes the item's effect from the Pokemon
        // Restores Pokemon's stats or abilities to their original state
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
