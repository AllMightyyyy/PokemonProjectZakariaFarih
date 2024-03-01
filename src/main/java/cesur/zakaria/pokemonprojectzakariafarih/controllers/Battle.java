package cesur.zakaria.pokemonprojectzakariafarih.controllers;

import cesur.zakaria.pokemonprojectzakariafarih.model.card.EnergyCard;
import cesur.zakaria.pokemonprojectzakariafarih.model.card.PokemonCard;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Battle {
    // Method to execute an attack from one Pokémon to another
    public void executeAttack(PokemonCard attacker, PokemonCard defender, Attack attack) {
        // Check if the attacker has enough Energy to perform the attack
        if (hasEnoughEnergy(attacker, attack)) {
            // Apply damage to the defending Pokémon
            defender.setHp(defender.getHp() - attack.getDamage());

            // Apply status condition if the attack specifies one
            if (!attack.getEffect().isEmpty()) {
                applyEffect(defender, attack.getEffect());
            }

            // Check for knockout
            if (defender.getHp() <= 0) {
                // Remove the knocked out Pokémon from the field
                field.removePokemon(defender);
                System.out.println(defender.getName() + " was knocked out!");
            }
        } else {
            System.out.println(attacker.getName() + " doesn't have enough Energy to perform " + attack.getName() + "!");
        }
    }

    // Method to check if the attacker has enough Energy attached to perform the attack
    private boolean hasEnoughEnergy(PokemonCard attacker, Attack attack) {
        List<EnergyCard> attachedEnergies = field.getActivePokemon().get(attacker);
        if (attachedEnergies != null) {
            Map<String, Long> energyCount = attachedEnergies.stream()
                    .collect(Collectors.groupingBy(EnergyCard::getType, Collectors.counting()));

            for (String requiredEnergy : attack.getCost()) {
                long count = energyCount.getOrDefault(requiredEnergy, 0L);
                if (count <= 0) {
                    return false; // Not enough energy of this type attached
                }
            }
            return true; // All required energies are available
        }
        return false; // No energies attached to the Pokémon
    }

    private void applyEffect(PokemonCard defender, String effect) {
        switch (effect.toLowerCase()) {
            case "paralyzed":
                defender.setStatus(StatusCondition.PARALYZED);
                break;
            case "asleep":
                defender.setStatus(StatusCondition.ASLEEP);
                break;
            case "poisoned":
                defender.setStatus(StatusCondition.POISONED);
                break;
            case "heal":
                healPokemon(defender);
                break;
            case "switch":
                switchPokemon(); // Implement logic for switching active Pokémon
                break;
            case "discard":
                discardEnergy(defender); // Assuming discarding energy from the defending Pokémon
                break;
            default:
                // Handle other effects or ignore if not recognized
                break;
        }
    }

    // Placeholder method for healing the defending Pokémon
    private void healPokemon(PokemonCard defender) {
        // Implement healing logic here, such as restoring HP or removing status conditions
        defender.setHp(defender.getHp() + 20);
    }

    // Placeholder method for switching active Pokémon
    private void switchPokemon() {
        // Implement logic for switching active Pokémon
    }

    // Placeholder method for discarding energy cards
    private void discardEnergy(PokemonCard defender) {
        // Implement logic for discarding energy cards from the defending Pokémon
        List<EnergyCard> attachedEnergies = field.getActivePokemon().get(defender);
        if (attachedEnergies != null) {
            // Remove all attached energy cards
            attachedEnergies.clear();
            // Additional logic if needed, such as updating UI or triggering effects
        }
    }

    public void startTurn(PokemonCard activePokemon) {
        switch (activePokemon.getStatus()) {
            case PARALYZED:
                // Handle paralysis, e.g., skip turn or have a chance to recover
                break;
            case ASLEEP:
                // Handle sleep, e.g., skip turn or have a chance to wake up
                break;
            // Handle other status conditions similarly
            default:
                // Proceed with normal turn if no debilitating condition
                break;
        }
        /*
        //TODO
         */
    }
    private void knockoutPokemon(PokemonCard pokemon) {
        field.removePokemon(pokemon);
    }


    // Field reference or parameter might be needed to access attached energies
    private Field field;

    // Constructor or setter to initialize the field reference
    public Battle(Field field) {
        this.field = field;
    }
}

