package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

/**
 * Represents a Pokémon card with Water type.
 */
public class PokemonWater extends PokemonPlayingCard {
    /**
     * The constant WEAKNESS.
     */
    public static final PokemonPlayingCard.Elements WEAKNESS = Elements.PLANT;
    /**
     * The constant STRENGTH.
     */
    public static final PokemonPlayingCard.Elements STRENGTH = Elements.FIRE;

    /**
     * Constructs a Pokémon Water card with specified attributes.
     *
     * @param namePokemon    The name of the Pokémon.
     * @param nameAttack     The name of the attack.
     * @param rarity         The rarity of the card.
     * @param hp             The hit points of the Pokémon.
     * @param damage         The damage value of the attack.
     * @param energyToAttack The energy required to perform the attack.
     */
    public PokemonWater(String namePokemon, String nameAttack, PlayingCard.Rarity rarity, int hp, int damage, int energyToAttack) {
        super(namePokemon, nameAttack, rarity, hp, damage, energyToAttack);
    }

    /**
     * Performs an attack action by the Pokémon against another Pokémon.
     * @param other The Pokémon to attack.
     * @return True if the attack was successful, false otherwise.
     */
    @Override
    public boolean attack(PokemonPlayingCard other) {
        Attack attack = super.getAttack();

        // Check if there is sufficient energy to make the attack, and if so, consume it
        boolean sufficientEnergy = super.consumeEnergy(attack.ENERGY_TO_ATTACK);
        if (!sufficientEnergy) return false;

        // Check if there is any attack/defense bonus
        final int BONUS_ATTACK = other instanceof PokemonFire ? PokemonPlayingCard.BONUS_ATTACK_DEFENSE : 1;
        final int BONUS_DEFENSE = other instanceof PokemonPlant ? PokemonPlayingCard.BONUS_ATTACK_DEFENSE : 1;

        // Calculate the damage of the attack
        int damageAttack = ( attack.DAMAGE * BONUS_ATTACK ) / BONUS_DEFENSE;

        // Deal damage to the target Pokémon
        other.giveDamage(damageAttack);

        return true;
    }

    /**
     * Generates a string representation of the Pokémon Water card.
     * @return The string representation of the card.
     */
    @Override
    public String toString() {
        return "NAME: " + super.getName() + " | "
                + "RARITY: " + super.getRarity() + " | "
                + "HP: " + super.getActualHp() + " | "
                + "CHARGED ENERGY: " + super.getActualEnergy() + " | "
                + super.getAttack().toString();
    }
}
