package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

import java.util.*;

public class CardDeck {
    public static final int N_POKEMON_CARDS = 5;
    public static final int N_ENERGY_CARDS = 5;
    public static int ENERGY_CARDS_WHEN_KILL = 3;
    public static final int NCARDS = N_POKEMON_CARDS + N_ENERGY_CARDS;
    private List<Card> playingCards;
    private Card selected;
    private List<GameListener> observers;
    private int pokemonsNoDeck;
    private boolean allFacingUp;

    public CardDeck(boolean table) {
        playingCards = new ArrayList<>();
        selected = null;
        allFacingUp = true;

        if (!table) { //add cards
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
        }
        else {
            pokemonsNoDeck = 0;
        }
        observers = new LinkedList<>();
        System.out.println("Deck Constructed");
    }

    public List<Card> getCards() { return Collections.unmodifiableList(playingCards); }
    public int getNumberOfCards() { return playingCards.size(); }
    public int getPokemonsNoDeck() { return pokemonsNoDeck; }
    public Card getSelectedCard() { return selected; }
    public void setSelectedCard(Card card) { selected = card; }
    public boolean isFacingUp() { return allFacingUp; }
    public void flipDeckUp() {
        for (Card c : playingCards) {
            if (!c.isFacingUp()) c.flipCard();
        }
        allFacingUp = true;
    }
    public void removeSel() {
        if (selected == null) return;
        playingCards.remove(selected);
        selected = null;
        GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.REMOVESEL, "");
        for (var observer : observers) {
            observer.notify(gameEvent);
        }
    }
    public int removeKilled() {
        int pokemonsRemoved = 0;
        for (int i = 0; i < playingCards.size(); i++) { // for each card in the deck
            Card c = playingCards.get(i);
            if ( c.getValue() instanceof PokemonPlayingCard ) { // if it's a pokemon card
                PokemonPlayingCard playingCard = (PokemonPlayingCard) c.getValue(); //cast
                if (playingCard.getActualHp() == 0) { //if dead
                    playingCards.remove(i); //remove
                    pokemonsNoDeck--;
                    pokemonsRemoved++;
                }
            }
        }
        return pokemonsRemoved;
    }

    public boolean removeEnergyCard() {
        for (Card c : playingCards) {
            if (c.getValue() instanceof EnergyCard) {
                playingCards.remove(c);
                return true;
            }
        }
        return false;
    }
    public void addCard(Card card) {
        System.out.println("add: " + card);
        playingCards.add(card);
        GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.SHOWTABLE, "");
        for (var observer : observers) {
            observer.notify(gameEvent);
        }
    }
    public void addEnergyForEachKill(int kills) {
        int n = kills * ENERGY_CARDS_WHEN_KILL;
        for (int i=0; i<n; i++) {
            playingCards.add(new Card("Energy", "img36"));
        }
    }
    public void flipAddedCards(int nCardsAdded) {
        for (int i = playingCards.size(); i > playingCards.size() - nCardsAdded; i--) {
            Card playingCard = playingCards.remove(i-1);
            playingCard.flipCard();
            playingCards.add(i-1, playingCard);
        }
    }
    public void addEnergyCard() {
        playingCards.add(new Card("Energy", "img36"));
    }
    public void addGameListener(GameListener listener) {
        observers.add(listener);
    }
}
