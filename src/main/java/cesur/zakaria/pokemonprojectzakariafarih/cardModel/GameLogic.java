package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the core game logic and state in the Pokemon card game.
 */
public class GameLogic {
    private CardDeck deckJ1, deckJ2;
    private CardDeck tableJ1, tableJ2;
    private int currentPhase; // 1 = J1 drops cards | 2 = J2 drops cards | 3 = J1 attacks/charges energy | 4 = J2 attacks/charges energy
    private int addedCardsMJ1, addedCardsMJ2;
    private boolean mustEndTurn;
    private List<GameListener> observers;

    /**
     * Constructs a new GameLogic object.
     */
    public GameLogic() {
        deckJ1 = new CardDeck(false);
        deckJ2 = new CardDeck(false);
        tableJ1 = new CardDeck(true);
        tableJ2 = new CardDeck(true);
        currentPhase = 1;
        addedCardsMJ1 = addedCardsMJ2 = 0;
        mustEndTurn = false;
        observers = new ArrayList<>();
    }

    /**
     * Advances the game to the next phase.
     */
    private void nextPhase() {
        currentPhase++;
        if (currentPhase == 5) {
            currentPhase = 1;
            deckJ1.addEnergyCard();
            deckJ2.addEnergyCard();
        }
    }

    /**
     * Ends the current player's turn.
     */
    public void endTurn() {
        if ((currentPhase == 1 && addedCardsMJ1 == 0 && deckJ1.getNumberOfCards() > 0) ||
                (currentPhase == 2 && addedCardsMJ2 == 0 && deckJ2.getNumberOfCards() > 0)) {
            notifyObservers("You need to download at least one card");
            return;
        }

        tableJ1.flipAddedCards(addedCardsMJ1);
        tableJ2.flipAddedCards(addedCardsMJ2);
        addedCardsMJ1 = addedCardsMJ2 = 0;
        mustEndTurn = false;

        checkEndGame();

        int removedJ1 = deckJ1.removeKilled();
        int removedJ2 = deckJ2.removeKilled();

        deckJ1.addEnergyForEachKill(removedJ2);
        deckJ2.addEnergyForEachKill(removedJ1);

        nextPhase();
    }

    /**
     * Checks if the game has ended.
     */
    private void checkEndGame() {
        if (getPokemonsJ1() == 0 || getPokemonsJ2() == 0) {
            notifyObservers("End of Game");
        }
    }

    /**
     * Downloads cards for the specified player.
     * @param player The player (1 or 2) downloading the cards.
     */
    public void downloadPlayingCards(int player) {
        if (currentPhase != player) {
            notifyObservers("It's not your turn to download cards.");
            return;
        }

        CardDeck playerDeck = player == 1 ? deckJ1 : deckJ2;
        CardDeck playerTable = player == 1 ? tableJ1 : tableJ2;

        if (playerDeck.getSelectedCard() == null) {
            notifyObservers("Select a card to download");
            return;
        }

        playerTable.addCard(playerDeck.getSelectedCard());
        playerDeck.removeSel();

        if (player == 1) addedCardsMJ1++;
        else addedCardsMJ2++;
    }

    /**
     * Performs an attack in the game.
     */
    public void play() {
        if (mustEndTurn) {
            notifyObservers("You must end your turn");
            return;
        }

        if (currentPhase < 3) {
            notifyObservers("Attacks cannot be made at this time");
            return;
        }

        CardDeck deckAttack = currentPhase == 3 ? tableJ1 : tableJ2;
        CardDeck deckDefense = currentPhase == 4 ? tableJ1 : tableJ2;

        if (deckAttack.getSelectedCard() == null || deckDefense.getSelectedCard() == null) {
            notifyObservers("Select a Pokémon card from each deck to attack");
            return;
        }

        if (deckAttack.getSelectedCard().getValue() instanceof EnergyCard ||
                deckDefense.getSelectedCard().getValue() instanceof EnergyCard) {
            notifyObservers("Energy cards cannot attack/defend");
            return;
        }

        PokemonPlayingCard pokemonAttack = (PokemonPlayingCard) deckAttack.getSelectedCard().getValue();
        PokemonPlayingCard pokemonDefense = (PokemonPlayingCard) deckDefense.getSelectedCard().getValue();
        boolean sufficientEnergy = pokemonAttack.attack(pokemonDefense);

        if (!sufficientEnergy) {
            notifyObservers("The attacking Pokémon does not have enough energy");
            return;
        } else {
            int nrDead = deckDefense.removeKilled();
            deckAttack.addEnergyForEachKill(nrDead);
            notifyObservers("Attack carried out successfully");

            if (nrDead > 0) {
                notifyObservers(String.format("You gained %d energy cards for killing %d rival Pokémon",
                        nrDead * CardDeck.ENERGY_CARDS_WHEN_KILL, nrDead));
            }
            mustEndTurn = true;
        }
    }

    /**
     * Adds energy to the selected card for the specified player.
     * @param player The player adding energy (1 or 2).
     */
    public void addEnergy(int player) {
        if (mustEndTurn) {
            notifyObservers("You must end your turn");
            return;
        }

        if ((player == 1 && currentPhase != 3) || (player == 2 && currentPhase != 4)) {
            notifyObservers("Cannot add energy at this time");
            return;
        }

        CardDeck deckActioned = player == 1 ? tableJ1 : tableJ2;
        Card selectedCard = deckActioned.getSelectedCard();

        if (selectedCard == null || selectedCard.getValue() instanceof EnergyCard) {
            notifyObservers("Select a Pokémon card to apply energy");
            return;
        }

        PokemonPlayingCard playingCardTarget = (PokemonPlayingCard) deckActioned.getSelectedCard().getValue();
        boolean consumedEnergy = deckActioned.removeEnergyCard();

        if (!consumedEnergy) {
            notifyObservers("There are no energy cards on the table");
        } else {
            playingCardTarget.chargeEnergy(1);
            notifyObservers("Energy added successfully");
        }
    }

    /**
     * Gets the total number of Pokémon cards held by player 1.
     * @return The total number of Pokémon cards held by player 1.
     */
    public int getPokemonsJ1() {
        return deckJ1.getPokemonsNoDeck() + tableJ1.getPokemonsNoDeck();
    }

    /**
     * Gets the total number of Pokémon cards held by player 2.
     * @return The total number of Pokémon cards held by player 2.
     */
    public int getPokemonsJ2() {
        return deckJ2.getPokemonsNoDeck() + tableJ2.getPokemonsNoDeck();
    }

    /**
     * Adds a GameListener to the list of observers.
     * @param listener The GameListener to add.
     */
    public void addGameListener(GameListener listener) {
        observers.add(listener);
    }

    /**
     * Notifies all observers with a message.
     * @param message The message to notify observers with.
     */
    private void notifyObservers(String message) {
        GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.SHOWMESSAGE, message);
        for (var observer : observers) {
            observer.notify(gameEvent);
        }
    }
}
