package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

/**
 * Class representing a neutral-type Pokémon playing card.
 */
public class PokemonNeutral extends PokemonPlayingCard {

    /**
     * Constructs a neutral-type Pokémon playing card with specified attributes.
     *
     * @param namePokemon    The name of the Pokémon.
     * @param nameAttack     The name of the attack.
     * @param rarity         The rarity of the card.
     * @param hp             The hit points of the Pokémon.
     * @param damage         The damage value of the attack.
     * @param energyToAttack The energy required to perform the attack.
     */
    public PokemonNeutral(String namePokemon, String nameAttack, PlayingCard.Rarity rarity, int hp, int damage, int energyToAttack) {
        super(namePokemon, nameAttack, rarity, hp, damage, energyToAttack);
    }

    /**
     * Overrides the attack method to implement neutral-type Pokémon attack behavior.
     * @param other The Pokémon to attack.
     * @return True if the attack was successful, false otherwise.
     */
    @Override
    public boolean attack(PokemonPlayingCard other) {
        Attack attack = super.getAttack();

        // Check if there is sufficient energy to make the attack and, if so, consume it and continue
        boolean sufficientEnergy = super.consumeEnergy(attack.ENERGY_TO_ATTACK);
        if (!sufficientEnergy) return false;

        // No one has an attack/defense bonus
        final int BONUS_ATTACK = 1;
        final int BONUS_DEFENSE = 1;

        // Calculate the damage to the other Pokémon
        int damageAttack = (attack.DAMAGE * BONUS_ATTACK) / BONUS_DEFENSE;

        // Inflict damage to the other card
        other.giveDamage(damageAttack);

        return true;
    }

    /**
     * Overrides the toString method to provide a string representation of the neutral-type Pokémon playing card.
     * @return A string representing the neutral-type Pokémon playing card.
     */
    @Override
    public String toString() {
        return "NOME: " + super.getName() + " | " +
                "RARITY: " + super.getRarity() + " | " +
                "HP: " + super.getActualHp() + " | " +
                "ENERGY CHARGED: " + super.getActualEnergy() + " | " +
                super.getAttack().toString();
    }
}
