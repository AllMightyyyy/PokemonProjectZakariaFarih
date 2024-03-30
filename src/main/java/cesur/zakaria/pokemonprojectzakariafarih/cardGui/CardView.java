package cesur.zakaria.pokemonprojectzakariafarih.cardGui;

import cesur.zakaria.pokemonprojectzakariafarih.cardModel.Card;
import cesur.zakaria.pokemonprojectzakariafarih.cardModel.ImageFactory;
import cesur.zakaria.pokemonprojectzakariafarih.cardModel.PokemonPlayingCard;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CardView extends Button implements PropertyChangeListener {
    private Card card;
    private CardView thisCardView;
    private CardViewListener observer;
    private Tooltip tip;
    private boolean facingUp;
    public CardView(Card aCard) {
        super("", ImageFactory.getInstance().createImage(aCard.getImageId()));
        facingUp = true;
        card = aCard;
        card.addPropertyChangeListener(this);
        thisCardView = this;
        this.setOnAction(e -> {
            if (observer != null) {
                observer.handle(new CardViewEvent(thisCardView));
            }
        });
        tip = new Tooltip();
        this.setTooltip(tip);
        tip.setOnShowing(e -> updateTooltip());
    }
    public Card getCard() { return card; }
    public void flip() {
        if (facingUp) {
            this.setGraphic(ImageFactory.getInstance().createImage("imgBck"));
            this.setTooltip(null); // disable tooltip
        }
        else {
            this.setGraphic(ImageFactory.getInstance().createImage(card.getImageId()));
            this.setTooltip(tip); // enable tooltip
        }

        facingUp = !facingUp;
    }
    public void updateTooltip() {
        if (card.getValue() instanceof PokemonPlayingCard) {
            PokemonPlayingCard carta = (PokemonPlayingCard) card.getValue();
            System.out.println(carta.getActualEnergy());
            tip.setText(String.format("HP: %d\nEnergy: %d\nRarities: %s", carta.getActualHp(), carta.getActualEnergy(), carta.getRarity().toString()));
        }
        else {
            tip.setText(String.format("Provides 1 energy\nRarity: %s", card.getValue().getRarity().toString()));
        }
    }
    public void setCardViewObserver(CardViewListener obs) {
        observer = obs;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.flip();
    }
}
