package cesur.zakaria.pokemonprojectzakariafarih.gameplay;

import cesur.zakaria.pokemonprojectzakariafarih.model.card.Card;
import cesur.zakaria.pokemonprojectzakariafarih.model.card.PokemonCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
    /*
    cards a player currently holds, needs to support adding cards ( drawing from deck ) and potentially playing
    cards from deck
     */
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    // Plays a card based on its unique ID, assuming each card has a unique identifier
    public Card playCardById(String id) {
        for (Card card : cards) {
            if (card.getId().equals(id)) {
                cards.remove(card);
                return card;
            }
        }
        return null;
    }

    // Search for cards by type (e.g., "Pokemon", "Energy")
    public List<Card> searchByType(String type) {
        return cards.stream()
                .filter(card -> card.getCategory().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    // Optional: Method to sort the hand, e.g., by card name or type
    public void sortByName() {
        cards.sort(Comparator.comparing(Card::getName));
    }
    public List<PokemonCard> getBasicPokemons() {
        List<PokemonCard> basicPokemons = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof PokemonCard) {
                PokemonCard pokemonCard = (PokemonCard) card;
                // Assuming the existence of a method to check if a PokemonCard is basic
                if (pokemonCard.isBasicPokemon()) {
                    basicPokemons.add(pokemonCard);
                }
            }
        }
        return basicPokemons;
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    // Additional functionalities as needed for gameplay
}
