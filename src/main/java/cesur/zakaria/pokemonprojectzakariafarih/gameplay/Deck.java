package cesur.zakaria.pokemonprojectzakariafarih.gameplay;

import cesur.zakaria.pokemonprojectzakariafarih.model.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    /*
    will manage collection of cards, allow shuffling, drawing cards . use List<Card>
     */
    private List<Card> cards = new ArrayList<>();

    public Deck(List<Card> initialCards) {
        this.cards.addAll(initialCards);
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    // Adds a card to the bottom of the deck
    public void addToBottom(Card card) {
        cards.add(card);
    }

    // Adds a card to the top of the deck
    public void addToTop(Card card) {
        cards.add(0, card);
    }

    // Optional: Implement methods for dealing with specific card types or conditions
    // For example, finding all energy cards in a deck (useful for certain game mechanics)
}
