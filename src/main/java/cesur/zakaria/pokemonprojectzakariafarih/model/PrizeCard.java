package cesur.zakaria.pokemonprojectzakariafarih.model;

import cesur.zakaria.pokemonprojectzakariafarih.model.card.Card;

public class PrizeCard {
    private Card card;

    public PrizeCard(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
