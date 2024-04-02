package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

/**
 * Abstract class representing a Pokémon playing card.
 */
public abstract class PokemonPlayingCard extends PlayingCard {
    /**
     * The enum Elements.
     */
    public enum Elements {
        /**
         * Water elements.
         */
        WATER,
        /**
         * Fire elements.
         */
        FIRE,
        /**
         * Plant elements.
         */
        PLANT };

    /**
     * The constant BONUS_ATTACK_DEFENSE.
     */
    public static final int BONUS_ATTACK_DEFENSE = 2;
    /**
     * The Max hp.
     */
    public final int MAX_HP;
    private int actualHp;
    private Attack attack;
    private int actualEnergy;

    /**
     * Constructs a Pokémon playing card with specified attributes.
     *
     * @param namePokemon    The name of the Pokémon.
     * @param nameAttack     The name of the attack.
     * @param rarity         The rarity of the card.
     * @param hp             The hit points of the Pokémon.
     * @param damage         The damage value of the attack.
     * @param energyToAttack The energy required to perform the attack.
     */
    public PokemonPlayingCard(String namePokemon, String nameAttack, PlayingCard.Rarity rarity, int hp, int damage, int energyToAttack) {
        super(namePokemon, rarity);

        if (hp > 0) this.MAX_HP = actualHp = hp;
        else this.MAX_HP = actualHp = 100;

        if (damage > 0 && energyToAttack > 0) attack = new Attack(nameAttack, damage, energyToAttack);
        else attack = new Attack(nameAttack, 0, 0);
    }

    /**
     * Gets the current hit points of the Pokémon.
     *
     * @return The current hit points.
     */
    public int getActualHp() {
        return this.actualHp;
    }

    /**
     * Inflicts damage to the Pokémon.
     *
     * @param damage The amount of damage to inflict.
     */
    public void giveDamage(int damage) {
        if (damage <= this.actualHp) this.actualHp -= damage;
        else this.actualHp = 0;
    }

    /**
     * Gets the current energy level of the Pokémon.
     *
     * @return The current energy level.
     */
    public int getActualEnergy() {
        return this.actualEnergy;
    }

    /**
     * Charges energy for the Pokémon.
     *
     * @param energy The amount of energy to charge.
     * @return True if the energy was successfully charged, false otherwise.
     */
    public boolean chargeEnergy(int energy) {
        if (energy > 0) {
            this.actualEnergy += energy;
            return true;
        }
        return false;
    }

    /**
     * Consumes energy for performing an attack.
     *
     * @param consumedEnergy The amount of energy to consume.
     * @return True if there was sufficient energy and it was consumed, false otherwise.
     */
    public boolean consumeEnergy(int consumedEnergy) {
        if (this.actualEnergy >= consumedEnergy) {
            this.actualEnergy -= consumedEnergy;
            return true;
        }
        return false;
    }

    /**
     * Gets the attack associated with the Pokémon.
     *
     * @return The attack.
     */
    public Attack getAttack() {
        return this.attack;
    }

    /**
     * Abstract method representing an attack action by the Pokémon.
     *
     * @param other The Pokémon to attack.
     * @return True if the attack was successful, false otherwise.
     */
    public abstract boolean attack(PokemonPlayingCard other);
}
