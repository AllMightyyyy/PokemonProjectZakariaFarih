package cesur.zakaria.pokemonprojectzakariafarih.cardGui;

import cesur.zakaria.pokemonprojectzakariafarih.cardModel.*;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * Represents a view for displaying a deck of cards.
 */
public class DeckView extends HBox implements CardViewListener, GameListener {
    private int player;
    private CardDeck cDeck;
    private Card selectedCard;

    /**
     * Constructs a new DeckView for the specified player.
     *
     * @param nroJog The player number.
     */
    public DeckView(int nroJog) {
        super(4);
        this.setAlignment(Pos.CENTER);

        player = nroJog;
        selectedCard = null;

        cDeck = null;
        if (player == 1) {
            cDeck = Game.getInstance().getDeckJ1();
        } else if (player == 2) {
            cDeck = Game.getInstance().getDeckJ2();
        } else if (player == -1) {
            cDeck = Game.getInstance().getTableJ1();
        } else if (player == -2) {
            cDeck = Game.getInstance().getTableJ2();
        }

        cDeck.addGameListener(this);

        for (Card card : cDeck.getCards()) {
            CardView cv = new CardView(card);
            cv.setCardViewObserver(this);
            this.getChildren().add(cv);
        }

        Game.getInstance().addGameListener(this);
    }

    private void removeSel() {
        ObservableList<Node> cards = getChildren();
        for (int i = 0; i < cards.size(); i++) {
            CardView cv = (CardView) cards.get(i);
            if (cv.getCard() == selectedCard) {
                getChildren().remove(cv);
                selectedCard = null;
            }
        }
    }

    private void showDeck() {
        this.getChildren().clear();

        System.out.println("m1.len>" + cDeck.getNumberOfCards());
        for (Card card : cDeck.getCards()) {
            System.out.println("show>" + card);
            CardView cv = new CardView(card);
            cv.setCardViewObserver(this);
            this.getChildren().add(cv);
        }
        System.out.println("Updated deck");
    }

    @Override
    public void notify(GameEvent event) {
        System.out.println("ev: "+ event);
        if (event.getTarget() != GameEvent.Target.DECK) {
            return;
        }
        if (event.getAction() == GameEvent.Action.REMOVESEL) {
            removeSel();
        }
        if (event.getAction() == GameEvent.Action.SHOWTABLE) {
            showDeck();
        }
        if (event.getAction() == GameEvent.Action.RESTART) {
            if (player == 1) {
                cDeck = Game.getInstance().getDeckJ1();
                System.out.println("\n\nJ1 deck rebooted");
            } else if (player == 2) {
                cDeck = Game.getInstance().getDeckJ2();
                System.out.println("\n\nJ2 deck rebooted");
            } else if (player == -1) {
                cDeck = Game.getInstance().getTableJ1();
                System.out.println("\n\nTable J1 restarted");
            } else if (player == -2) {
                cDeck = Game.getInstance().getTableJ2();
                System.out.println("\n\nTable J2 restarted");
            }

            cDeck.addGameListener(this);
            showDeck();
        }
    }

    @Override
    public void handle(CardViewEvent event) {
        CardView cv = event.getCardView();
        selectedCard = cv.getCard();
        cDeck.setSelectedCard(selectedCard);
    }
}
