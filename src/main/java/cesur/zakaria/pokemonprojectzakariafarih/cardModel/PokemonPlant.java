package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

/**
 * Class representing a plant-type Pokémon playing card.
 */
public class PokemonPlant extends PokemonPlayingCard {
    /**
     * The constant WEAKNESS.
     */
    public static final PokemonPlayingCard.Elements WEAKNESS = Elements.FIRE;
    /**
     * The constant STRENGTH.
     */
    public static final PokemonPlayingCard.Elements STRENGTH = Elements.WATER;

    /**
     * Constructs a plant-type Pokémon playing card with specified attributes.
     *
     * @param namePokemon    The name of the Pokémon.
     * @param nameAttack     The name of the attack.
     * @param rarity         The rarity of the card.
     * @param hp             The hit points of the Pokémon.
     * @param damage         The damage value of the attack.
     * @param energyToAttack The energy required to perform the attack.
     */
    public PokemonPlant(String namePokemon, String nameAttack, PlayingCard.Rarity rarity, int hp, int damage, int energyToAttack) {
        super(namePokemon, nameAttack, rarity, hp, damage, energyToAttack);
    }

    /**
     * Overrides the attack method to implement plant-type Pokémon attack behavior.
     * @param other The Pokémon to attack.
     * @return True if the attack was successful, false otherwise.
     */
    @Override
    public boolean attack(PokemonPlayingCard other) {
        Attack attack = super.getAttack();

        // Check if there is sufficient energy to make the attack and, if so, consume it and continue
        boolean sufficientEnergy = super.consumeEnergy(attack.ENERGY_TO_ATTACK);
        if (!sufficientEnergy) return false;

        // Verify if someone has an attack or defense bonus
        final int BONUS_ATTACK = other instanceof PokemonWater ? PokemonPlayingCard.BONUS_ATTACK_DEFENSE : 1;
        final int BONUS_DEFENSE = other instanceof PokemonFire ? PokemonPlayingCard.BONUS_ATTACK_DEFENSE : 1;

        // Calculate the damage of the attack
        int damageAttack = (attack.DAMAGE * BONUS_ATTACK) / BONUS_DEFENSE;

        // Inflict damage to the "other" card
        other.giveDamage(damageAttack);

        return true;
    }

    /**
     * Overrides the toString method to provide a string representation of the plant-type Pokémon playing card.
     * @return A string representing the plant-type Pokémon playing card.
     */
    @Override
    public String toString() {
        return "NOME: " + super.getName() + " | " +
                "RARIDADE: " + super.getRarity() + " | " +
                "HP: " + super.getActualHp() + " | " +
                "ENERGIA CARREGADA: " + super.getActualEnergy() + " | " +
                super.getAttack().toString();
    }
}
