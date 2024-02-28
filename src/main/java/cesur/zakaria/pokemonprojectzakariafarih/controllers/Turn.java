package cesur.zakaria.pokemonprojectzakariafarih.controllers;

public class Turn {
    private int battleNumber; // Identifies the battle
    private int turnNumber; // The sequence number of this turn within the battle
    private String playerAction; // Describes the player's trainer action
    private String opponentAction; // Describes the opponent's action

    // Constructor
    public Turn(int battleNumber, int turnNumber, String playerAction, String opponentAction) {
        this.battleNumber = battleNumber;
        this.turnNumber = turnNumber;
        this.playerAction = playerAction;
        this.opponentAction = opponentAction;
    }

    // Methods
    // In this case, as Turn primarily serves as a data structure,
    // you might not need additional methods beyond getters and setters.

    // Getters and Setters
    public int getBattleNumber() {
        return battleNumber;
    }

    public void setBattleNumber(int battleNumber) {
        this.battleNumber = battleNumber;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public String getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(String playerAction) {
        this.playerAction = playerAction;
    }

    public String getOpponentAction() {
        return opponentAction;
    }

    public void setOpponentAction(String opponentAction) {
        this.opponentAction = opponentAction;
    }
}

