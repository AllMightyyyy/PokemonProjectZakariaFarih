package cesur.zakaria.pokemonprojectzakariafarih.controllers;

import cesur.zakaria.pokemonprojectzakariafarih.model.Player;
import cesur.zakaria.pokemonprojectzakariafarih.model.card.Card;
import cesur.zakaria.pokemonprojectzakariafarih.model.card.PokemonCard;

import java.util.List;

public class Turn {
    private Player player;

    public Turn(Player player) {
        this.player = player;
    }

    // Draw phase
    public void drawPhase() {
        Card drawnCard = player.drawCard();
        if (drawnCard != null) {
            // If a card is successfully drawn, you can proceed with any additional logic here
            System.out.println(player.getName() + " drew a card: " + drawnCard.getName());
        } else {
            // Handle the case where the player's deck is empty
            System.out.println(player.getName() + "'s deck is empty!");
        }
    }

    // Preparation phase
    public void preparationPhase() {
        // Implement preparation phase logic
        // 1. Play Basic Pokémon
        // Implement logic to allow the player to play basic Pokémon cards from their hand
        List<PokemonCard> basicPokemonsInHand = player.getHand().getBasicPokemons();
        for (PokemonCard basicPokemon : basicPokemonsInHand) {
            if (player.getField().getActivePokemon().size() < Player.MAX_ACTIVE_POKEMON) {
                player.getField().placePokemon(basicPokemon);
                player.getHand().removeCard(basicPokemon);
                System.out.println(player.getName() + " played " + basicPokemon.getName() + " onto the field.");
            } else {
                System.out.println("Cannot play " + basicPokemon.getName() + ". Field is full.");
                break;
            }
        }

        // 2. Attach Energy Cards
        // Implement logic to allow the player to attach energy cards from their hand to their Pokémon

        // 3. Evolve Pokémon
        // Implement logic to allow the player to evolve their Pokémon if conditions are met

        // 4. Use Trainer Cards
        // Implement logic to allow the player to play Trainer cards with beneficial effects
    }

    // Action phase
    public void actionPhase() {
        // Implement action phase logic
    }

    // End phase
    public void endPhase() {
        // Implement end phase logic
    }
}
