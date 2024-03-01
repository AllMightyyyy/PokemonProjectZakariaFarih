package cesur.zakaria.pokemonprojectzakariafarih.model;

import cesur.zakaria.pokemonprojectzakariafarih.controllers.Field;
import cesur.zakaria.pokemonprojectzakariafarih.gameplay.Deck;
import cesur.zakaria.pokemonprojectzakariafarih.gameplay.Hand;
import cesur.zakaria.pokemonprojectzakariafarih.model.card.Card;

public class Player {
    private String name;
    private Deck deck;
    private Hand hand;
    private PrizeCard[] prizeCards;
    private Field field;
    public static final int MAX_ACTIVE_POKEMON = 6; //MAXIMUM NUMBER OF POKEMONS ALLOWED

    public Player(String name, Deck deck) {
        this.name = name;
        this.deck = deck;
        this.hand = new Hand();
        this.prizeCards = new PrizeCard[6]; // Assuming a maximum of 6 prize cards
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Hand getHand() {
        return hand;
    }
    public Field getField() {
        return field;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public PrizeCard[] getPrizeCards() {
        return prizeCards;
    }

    public void setPrizeCards(PrizeCard[] prizeCards) {
        this.prizeCards = prizeCards;
    }

    // Implement methods to draw cards, play cards, etc.
    public Card drawCard() {
        return deck.draw();
    }
}
