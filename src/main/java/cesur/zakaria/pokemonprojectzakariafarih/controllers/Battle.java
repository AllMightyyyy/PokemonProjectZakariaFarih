package cesur.zakaria.pokemonprojectzakariafarih.controllers;

import java.util.ArrayList;
import java.util.List;

public class Battle {
    private Trainer winner;
    private Trainer player;
    private Trainer opponent;
    private int turnNumber = 1;
    private int playerPokemonKnockedOutCount = 0;
    private int opponentPokemonKnockedOutCount = 0;
    private List<Turn> turns;

    // Constructor
    public Battle(Trainer player, Trainer opponent) {
        this.player = player;
        this.opponent = opponent;
        // Initialize turns list
        this.turns = new ArrayList<>();
    }

    // Methods
    public void startBattle() {
        // Initialize the battle, setting up initial conditions
    }

    public void executeTurn() {
        // Executes actions of a single turn
    }

    public void endBattle() {
        // Ends the battle, determining the winner
    }

    public void forfeit(Trainer trainer) {
        // Allows a trainer to forfeit the battle
    }

    public void calculateWinner() {
        // Determines the winner of the battle
    }

    public void calculateExperience() {
        // Calculates experience gained by participating Pokémon
    }

    public void transferPokeDollars() {
        // Transfers PokéDollars from the losing trainer to the winner
    }

    public void generateOpponentPokemon() {
        // Generates the opponent's Pokémon team
    }

    public void checkWhoGoesFirst() {
        // Determines which Pokémon goes first in the battle
    }

    public void exportTurnsToFile() {
        // Optionally exports the details of each turn to a file
    }

    public Trainer getWinner() {
        return winner;
    }

    public void setWinner(Trainer winner) {
        this.winner = winner;
    }

    public Trainer getPlayer() {
        return player;
    }

    public void setPlayer(Trainer player) {
        this.player = player;
    }

    public Trainer getOpponent() {
        return opponent;
    }

    public void setOpponent(Trainer opponent) {
        this.opponent = opponent;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public int getPlayerPokemonKnockedOutCount() {
        return playerPokemonKnockedOutCount;
    }

    public void setPlayerPokemonKnockedOutCount(int playerPokemonKnockedOutCount) {
        this.playerPokemonKnockedOutCount = playerPokemonKnockedOutCount;
    }

    public int getOpponentPokemonKnockedOutCount() {
        return opponentPokemonKnockedOutCount;
    }

    public void setOpponentPokemonKnockedOutCount(int opponentPokemonKnockedOutCount) {
        this.opponentPokemonKnockedOutCount = opponentPokemonKnockedOutCount;
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public void setTurns(List<Turn> turns) {
        this.turns = turns;
    }
}
//Battle Class

