package cesur.zakaria.pokemonprojectzakariafarih.controllers;

import java.util.List;
import java.util.Optional;

public class Attack {
    private String name;
    private List<String> cost; // Energy cost remains as a list of energy types
    private String effect;
    private int damage;
    private Optional<Integer> accuracy; // Optional to represent chance-based attacks
    private StatusCondition statusEffect; // Enum defined elsewhere
    private SecondaryEffect secondaryEffect;

    public Attack(String name, List<String> cost, String effect, int damage, Optional<Integer> accuracy, StatusCondition statusEffect, SecondaryEffect secondaryEffect) {
        this.name = name;
        this.cost = cost;
        this.effect = effect;
        this.damage = damage;
        this.accuracy = accuracy;
        this.statusEffect = statusEffect == null ? StatusCondition.NONE : statusEffect;
        this.secondaryEffect = secondaryEffect;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public List<String> getCost() {
        return cost;
    }

    public String getEffect() {
        return effect;
    }

    public int getDamage() {
        return damage;
    }

    public Optional<Integer> getAccuracy() {
        return accuracy;
    }

    public StatusCondition getStatusEffect() {
        return statusEffect;
    }

    public SecondaryEffect getSecondaryEffect() {
        return secondaryEffect;
    }

    // Example setter
    public void setName(String name) {
        this.name = name;
    }

    // Additional methods as needed
}

// Class or Enum for secondary effects, depending on your design
class SecondaryEffect {
    // Define secondary effect properties and behaviors
}
