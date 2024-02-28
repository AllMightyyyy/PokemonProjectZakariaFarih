package cesur.zakaria.pokemonprojectzakariafarih.gameplay;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private String name;
    private String nickname;
    private int vitality;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private int stamina;
    private int level = 1;
    private int experience = 0;
    private int fertility;
    private Gender gender;
    private PokemonType type1;
    private PokemonType type2; // This can be null to indicate that the Pokémon has only one type.
    private List<State> states;
    private Item item;
    private List<Move> moves;
    private List<Move> possibleMoves;

    // Constructor
    public Pokemon(String name, String nickname, int vitality, int attack, int defense, int specialAttack, int specialDefense, int speed, int stamina, int fertility, Gender gender, PokemonType type1, PokemonType type2, Item item) {
        this.name = name;
        this.nickname = nickname;
        this.vitality = vitality;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.stamina = stamina;
        this.fertility = fertility;
        this.gender = gender;
        this.type1 = type1;
        this.type2 = type2;
        this.item = item;
        // Initialize the states, moves, and possibleMoves lists
        this.states = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.possibleMoves = new ArrayList<>();
    }

    // Methods
    public void levelUp() {
        // Implementation needed
    }

    public void attack(Pokemon opponentPokemon, Move move) {
        // Implementation needed
    }

    public String checkAdvantage(Pokemon opponentPokemon) {
        // Implementation needed
        return "";
    }

    public void learnMove(Move newMove) {
        // Implementation needed
    }

    public void restoreMoves() {
        // Implementation needed
    }

    public void manageBreeding() {
        // Implementation needed
    }

    public void gainExperience(int amount) {
        // Implementation needed
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getFertility() {
        return fertility;
    }

    public void setFertility(int fertility) {
        this.fertility = fertility;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public PokemonType getType1() {
        return type1;
    }

    public void setType1(PokemonType type1) {
        this.type1 = type1;
    }

    public PokemonType getType2() {
        return type2;
    }

    public void setType2(PokemonType type2) {
        this.type2 = type2;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<Move> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<Move> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
    public void equipItem(Item item) {
        if (this.item != null) {
            this.item.removeEffect(this); // Remove the effect of the currently equipped item
        }
        this.item = item;
        item.applyEffect(this); // Apply the new item's effect
    }

    // Method to unequip the current item from this Pokemon
    public void unequipItem() {
        if (this.item != null) {
            this.item.removeEffect(this); // Remove the item's effect
            this.item = null; // Remove the item
        }
    }
}
//pokemon Class
