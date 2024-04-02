package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

import cesur.zakaria.pokemonprojectzakariafarih.cardGui.GameWindow;
import cesur.zakaria.pokemonprojectzakariafarih.cardGui.ScoreboardView;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the game logic and state in the Pokemon card game.
 */
public class Game {
    private static Game game = new Game();
    private CardDeck deckJ1, deckJ2;
    private CardDeck tableJ1, tableJ2;
    private int currentPhase; // 1 = J1 drops cards and 'end turn' | 2 = J2 drops cards and 'end turn' | 3 = J1 attacks/charges energy and 'end turn' | 4 = J2 attacks/charges energy and 'end turn'
    private List<GameListener> observers;
    private int addedCardsMJ1, addedCardsMJ2;
    private boolean mustEndTurn;

    /**
     * Retrieves the singleton instance of the Game class.
     *
     * @return The singleton instance of the Game class.
     */
    public static Game getInstance() {
        return game;
    }

    /**
     * Constructs a new Game object.
     */
    private Game() {
        deckJ1 = new CardDeck(false);
        deckJ2 = new CardDeck(false);
        tableJ1 = new CardDeck(true);
        tableJ2 = new CardDeck(true);
        currentPhase = 1;
        observers = new LinkedList<>();
        addedCardsMJ1 = addedCardsMJ2 = 0;
        mustEndTurn = false;
    }

    /**
     * Advances the game to the next phase.
     */
    private void nextPhase() {
        currentPhase++;
        if (currentPhase == 5) {
            currentPhase = 1;
            // add an energy to each deck at the end of the round
            deckJ1.addEnergyCard();
            deckJ2.addEnergyCard();
            //re-render the table
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.SHOWTABLE, null);
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
        }
    }

    /**
     * Retrieves the total number of Pokémon cards held by player 1.
     *
     * @return The total number of Pokémon cards held by player 1.
     */
    public int getPokemonsJ1() {
        return deckJ1.getPokemonsNoDeck() + tableJ1.getPokemonsNoDeck();
    }

    /**
     * Retrieves the total number of Pokémon cards held by player 2.
     *
     * @return The total number of Pokémon cards held by player 2.
     */
    public int getPokemonsJ2() {
        return deckJ2.getPokemonsNoDeck() + tableJ2.getPokemonsNoDeck();
    }

    /**
     * Retrieves the deck of cards belonging to player 1.
     *
     * @return The deck of cards belonging to player 1.
     */
    public CardDeck getDeckJ1() { return deckJ1; }

    /**
     * Retrieves the deck of cards belonging to player 2.
     *
     * @return The deck of cards belonging to player 2.
     */
    public CardDeck getDeckJ2() { return deckJ2; }

    /**
     * Retrieves the table of cards belonging to player 1.
     *
     * @return The table of cards belonging to player 1.
     */
    public CardDeck getTableJ1() { return tableJ1; }

    /**
     * Retrieves the table of cards belonging to player 2.
     *
     * @return The table of cards belonging to player 2.
     */
    public CardDeck getTableJ2() { return tableJ2; }

    /**
     * Retrieves the current phase of the game.
     *
     * @return The current phase of the game.
     */
    public int getActualPhase() { return currentPhase; }

    /**
     * Initiates a player's turn in the game.
     * Handles various actions based on the current game phase, such as attacks and card downloads.
     */
    public void play() {
        System.out.println("\n\nENTER TO PLAY");
        if (mustEndTurn) {
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.MUSTENDTURN, "");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        if (currentPhase < 3) { // you shouldn't do anything here
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "Attacks cannot be made at this time");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        CardDeck deckAttack = currentPhase == 3? tableJ1 : tableJ2;
        CardDeck deckDefense = currentPhase == 4? tableJ1 : tableJ2;
        if (deckAttack.getSelectedCard() == null || deckDefense.getSelectedCard() == null) {
            System.out.println(deckAttack.getSelectedCard());
            System.out.println(deckDefense.getSelectedCard());
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "Select a Pokémon card from each deck to attack");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        else if (deckAttack.getSelectedCard().getValue() instanceof EnergyCard ||
                deckDefense.getSelectedCard().getValue() instanceof EnergyCard) {
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "Energy cards cannot attack/defend");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        PokemonPlayingCard pokemonAttack = (PokemonPlayingCard) deckAttack.getSelectedCard().getValue();
        PokemonPlayingCard pokemonDefense = (PokemonPlayingCard) deckDefense.getSelectedCard().getValue();
        boolean sufficientEnergy = pokemonAttack.attack(pokemonDefense);
        if (!sufficientEnergy) {
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "The attacking Pokémon does not have enough energy");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        else { // the attack was successful, and there may have been changes
            int nrDead = deckDefense.removeKilled(); //remove dead from defense deck
            deckAttack.addEnergyForEachKill(nrDead); //add energy for each Pokemon killed
            //re-render the table
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.SHOWTABLE, null);
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            GameEvent gameEvent2 = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.SHOWMESSAGE,
                    String.format("Attack carried out successfully.\nNOW, YOU MUST END YOUR SHIFT!\nAttacker: %s\nDefender: %s",
                            pokemonAttack.getName(), pokemonDefense.getName()));
            for (var observer : observers) {
                observer.notify(gameEvent2);
            }
            if (nrDead > 0) {
                GameEvent gameEvent3 = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.SHOWMESSAGE,
                        String.format("You gained %d energy cards for killing %d rival Pokémon",
                                nrDead * CardDeck.ENERGY_CARDS_WHEN_KILL, nrDead));
                for (var observer : observers) {
                    observer.notify(gameEvent3);
                }
            }
            mustEndTurn = true;
        }
    }

    /**
     * Downloads cards for a specified player.
     * Triggered by the "Download cards" button.
     *
     * @param player The player for whom cards should be downloaded (1 for player 1, 2 for player 2).
     */
// Triggered by the 'Download cards' button
    public void downloadPlayingCards(int player) {
        System.out.println("\n\ndownloadLetters() triggered");
        if (currentPhase != 1 && currentPhase != 2) { // Cards can only be downloaded in phases 1 (J1) and 2 (J2)
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "Cards cannot be downloaded at this time.");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        else if (player == 1 && currentPhase != 1) {
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, String.format("It's not %s's turn to download cards.", GameWindow.getNameJ1()));
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        else if (player == 2 && currentPhase != 2) {
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, String.format("It's not %s's turn to download cards.", GameWindow.getNameJ2()));
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        if (player != 1 && player != 2) return;
        CardDeck playerDeck = player == 1? deckJ1 : deckJ2;
        CardDeck playerTable = player == 1? tableJ1 : tableJ2;
        if (playerDeck.getSelectedCard() == null) {
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "Select a card to download");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        playerTable.addCard( playerDeck.getSelectedCard() );
        playerDeck.removeSel();
        if (player == 1) addedCardsMJ1++;
        else addedCardsMJ2++;
    }

    /**
     * Ends the current player's turn.
     * Triggered by the "End Turn" button.
     */
// Triggered by the "End Turn" button
    public void endTurn() {
        if ( (currentPhase == 1 && addedCardsMJ1 == 0 && deckJ1.getNumberOfCards() > 0) ||
                (currentPhase == 2 && addedCardsMJ2 == 0 && deckJ2.getNumberOfCards() > 0)) { // if it's J1's turn to download cards, he doesn't download them and has cards to download
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "You need to download at least one card");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        tableJ1.flipAddedCards(addedCardsMJ1);
        tableJ2.flipAddedCards(addedCardsMJ2);
        addedCardsMJ1 = addedCardsMJ2 = 0;
        mustEndTurn = false;

        // check end of game
        if (this.getPokemonsJ1() == 0 || this.getPokemonsJ2() == 0) {
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.ENDGAME, "");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
        }

        int removedJ1 = deckJ1.removeKilled();
        int removedJ2 = deckJ2.removeKilled();

        // add energy as a bonus for beating an opposing pokemon
        deckJ1.addEnergyForEachKill(removedJ2);
        deckJ2.addEnergyForEachKill(removedJ1);

        nextPhase();
        this.updateScoreboardView();
        if (currentPhase == 1 && deckJ1.getNumberOfCards() == 0) {
            nextPhase();
            this.updateScoreboardView();
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.SHOWMESSAGE, String.format("Since %s has no cards in his deck, his turn to set cards has ended.", GameWindow.getNameJ1()));
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
        }
        else if (currentPhase == 2 && deckJ2.getNumberOfCards() == 0) {
            nextPhase();
            this.updateScoreboardView();
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.SHOWMESSAGE, String.format("Since %s has no cards in his deck, his turn to set cards has ended.", GameWindow.getNameJ2()));
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
        }

        if (currentPhase == 3) {
            tableJ1.flipDeckUp();
            tableJ2.flipDeckUp();
        }
    }

    /**
     * Updates the scoreboard view based on the current game phase.
     */
    public void updateScoreboardView() {
        String text;

        if (currentPhase == 1) text = GameWindow.getNameJ1() + " download cards";
        else if (currentPhase == 2) text = GameWindow.getNameJ2() + " download cards";
        else if (currentPhase == 3) text = GameWindow.getNameJ1() + " attacks";
        else text = GameWindow.getNameJ2() + " attacks";

        ScoreboardView.getInstance().setFieldActualPhase(text);
    }

    /**
     * Restarts the game, resetting all decks and stats.
     */
    public void restart() {
        deckJ1 = new CardDeck(false);
        deckJ2 = new CardDeck(false);
        tableJ1 = new CardDeck(true);
        tableJ2 = new CardDeck(true);
        currentPhase = 1;
        this.updateScoreboardView();

        GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.RESTART, "");
        for (var observer : observers) {
            observer.notify(gameEvent);
        }

        GameEvent gameEvent2 = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.SHOWMESSAGE, "The game has been restarted\nAll decks and stats have been reset");
        for (var observer : observers) {
            observer.notify(gameEvent2);
        }
    }

    /**
     * Adds energy to a Pokémon card on the table for a specified player.
     *
     * @param player The player for whom energy should be added (1 for player 1, 2 for player 2).
     */
    public void addEnergy(int player) {
        if (mustEndTurn) {
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.MUSTENDTURN, "");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }

        System.out.println("\n\n\nENTER ADDENERGY()");
        System.out.println("Current player: " + player);
        System.out.println("Phase: " + currentPhase);

        if (player != 1 && player != 2) return;
        else if (player == 1 && currentPhase != 3) { // J1 adding power out of turn
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, String.format("%s cannot attack/add energy at this time", GameWindow.getNameJ1()));
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        else if (player == 2 && currentPhase != 4) { // J2 adding power out of turn
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, String.format("%s cannot attack/add energy at this time", GameWindow.getNameJ2()));
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }

        CardDeck deckActioned = player == 1? tableJ1 : tableJ2;
        Card selectedCard = deckActioned.getSelectedCard();
        if (selectedCard == null || selectedCard.getValue() instanceof EnergyCard) { // player does not select the target card or it is an energy card
            System.out.println("Selected Playing Card (addEnergy): " + selectedCard);
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "Select a Pokémon card to apply energy");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }

        PokemonPlayingCard playingCardTarget = (PokemonPlayingCard) deckActioned.getSelectedCard().getValue(); // add energy to this letter
        System.out.println("All tests OK. Charging energy");
        System.out.println("Target card: " + playingCardTarget.getName());
        boolean consumedEnergy = deckActioned.removeEnergyCard();
        if (!consumedEnergy) {
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.GWIN, GameEvent.Action.INVPLAY, "There are no energy cards on the table");
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
            return;
        }
        else {
            System.out.println("Energy being added");
            playingCardTarget.chargeEnergy(1); // charge the energy in the card
            System.out.println(playingCardTarget.getActualEnergy());
            // re-render the table
            GameEvent gameEvent = new GameEvent(this, GameEvent.Target.DECK, GameEvent.Action.SHOWTABLE, null);
            for (var observer : observers) {
                observer.notify(gameEvent);
            }
        }
    }

    /**
     * Adds a GameListener to the list of observers.
     *
     * @param listener The GameListener to add.
     */
    public void addGameListener(GameListener listener) { observers.add(listener); }
}
