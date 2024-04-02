package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

import java.util.*;

/**
 * Represents a deck of cards in the Pokemon game.
 * It contains both Pokemon cards and Energy cards.
 */
public class CardDeck {
    /**
     * The number of Pokemon cards in the deck.
     */
    public static final int N_POKEMON_CARDS = 5;

    /**
     * The number of Energy cards in the deck.
     */
    public static final int N_ENERGY_CARDS = 5;

    /**
     * The number of Energy cards added to the deck when a Pokemon is killed.
     */
    public static int ENERGY_CARDS_WHEN_KILL = 3;

    /**
     * The total number of cards in the deck.
     */
    public static final int NCARDS = N_POKEMON_CARDS + N_ENERGY_CARDS;

    /** The list of playing cards in the deck. */
    private List<Card> playingCards;

    /** The selected card from the deck. */
    private Card selected;

    /** List of observers for the deck. */
    private List<GameListener> observers;

    /** The number of Pokemon cards remaining in the deck. */
    private int pokemonsNoDeck;

    /** Indicates whether all cards in the deck are facing up. */
    private boolean allFacingUp;

    /**
     * Constructs a CardDeck object.
     *
     * @param table Indicates whether the deck is for the table.
     */
    public CardDeck(boolean table) {
        playingCards = new ArrayList<>();
        selected = null;
        allFacingUp = true;

        if (!table) { // Add cards to the deck
            pokemonsNoDeck = N_POKEMON_CARDS;
            Random r = new Random();
            for (int i = 0; i < N_POKEMON_CARDS; i++) {
                int n = r.nextInt(35) + 1;
                Card c = new Card("C" + n, "img" + n);
                playingCards.add(c);
            }
            for (int i = 0; i < N_ENERGY_CARDS; i++) {
                playingCards.add(new Card("Energy", "img36"));
            }
        } else {
            pokemonsNoDeck = 0;
        }
        observers = new LinkedList<>();
        System.out.println("Deck Constructed");
    }

    /**
     * Returns an unmodifiable list of cards in the deck.
     *
     * @return Unmodifiable list of cards.
     */
    public List<Card> getCards() {
        return Collections.unmodifiableList(playingCards);
    }

    /**
     * Returns the total number of cards in the deck.
     *
     * @return The total number of cards.
     */
    public int getNumberOfCards() {
        return playingCards.size();
    }

    /**
     * Returns the number of Pokemon cards remaining in the deck.
     *
     * @return The number of Pokemon cards.
     */
    public int getPokemonsNoDeck() {
        return pokemonsNoDeck;
    }

    /**
     * Returns the selected card from the deck.
     *
     * @return The selected card.
     */
    public Card getSelectedCard() {
        return selected;
    }

    /**
     * Sets the selected card in the deck.
     *
     * @param card The card to select.
     */
    public void setSelectedCard(Card card) {
        selected = card;
    }

    /**
     * Checks if all cards in the deck are facing up.
     *
     * @return True if all cards are facing up, false otherwise.
     */
    public boolean isFacingUp() {
        return allFacingUp;
    }

    /**
     * Flips all cards in the deck to face up.
     */
    public void flipDeckUp() {
        for (Card c : playingCards) {
            if (!c.isFacingUp()) c.flipCard();
        }
        allFacingUp = true;
    }

    /**
     * Removes the selected card from the deck.
     */
    public void removeSel() {
        if (selected == null) return;
        playingCards.remove(selected);
        selected = null;
        GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.REMOVESEL, "");
        for (var observer : observers) {
            observer.notify(gameEvent);
        }
    }

    /**
     * Removes Pokemon cards from the deck that have zero HP (indicating they are killed).
     *
     * @return The number of Pokemon cards removed.
     */
    public int removeKilled() {
        int pokemonsRemoved = 0;
        for (int i = 0; i < playingCards.size(); i++) {
            Card c = playingCards.get(i);
            if (c.getValue() instanceof PokemonPlayingCard) {
                PokemonPlayingCard playingCard = (PokemonPlayingCard) c.getValue();
                if (playingCard.getActualHp() == 0) {
                    playingCards.remove(i);
                    pokemonsNoDeck--;
                    pokemonsRemoved++;
                }
            }
        }
        return pokemonsRemoved;
    }

    /**
     * Removes an Energy card from the deck.
     *
     * @return True if an Energy card is removed, false otherwise.
     */
    public boolean removeEnergyCard() {
        for (Card c : playingCards) {
            if (c.getValue() instanceof EnergyCard) {
                playingCards.remove(c);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a card to the deck.
     *
     * @param card The card to add.
     */
    public void addCard(Card card) {
        System.out.println("add: " + card);
        playingCards.add(card);
        GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.SHOWTABLE, "");
        for (var observer : observers) {
            observer.notify(gameEvent);
        }
    }

    /**
     * Adds Energy cards to the deck based on the number of kills.
     *
     * @param kills The number of kills.
     */
    public void addEnergyForEachKill(int kills) {
        int n = kills * ENERGY_CARDS_WHEN_KILL;
        for (int i = 0; i < n; i++) {
            playingCards.add(new Card("Energy", "img36"));
        }
    }

    /**
     * Flips a specified number of cards added to the deck.
     *
     * @param nCardsAdded The number of cards added.
     */
    public void flipAddedCards(int nCardsAdded) {
        for (int i = playingCards.size(); i > playingCards.size() - nCardsAdded; i--) {
            Card playingCard = playingCards.remove(i - 1);
            playingCard.flipCard();
            playingCards.add(i - 1, playingCard);
        }
    }

    /**
     * Adds an Energy card to the deck.
     */
    public void addEnergyCard() {
        playingCards.add(new Card("Energy", "img36"));
    }

    /**
     * Adds a game listener to the deck.
     *
     * @param listener The game listener to add.
     */
    public void addGameListener(GameListener listener) {
        observers.add(listener);
    }
}
