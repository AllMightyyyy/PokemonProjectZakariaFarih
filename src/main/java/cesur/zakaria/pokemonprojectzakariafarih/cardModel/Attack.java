package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

/**
 * Represents an attack in the Pokemon game.
 * Each attack has a name, damage value, and energy cost.
 */
public class Attack {
    /**
     * The damage value of the attack.
     */
    public final int DAMAGE;

    /**
     * The energy cost required to perform the attack.
     */
    public final int ENERGY_TO_ATTACK;

    /** The name of the attack. */
    private final String NAME;

    /**
     * Constructs a new Attack object with the specified name, damage, and energy cost.
     *
     * @param name   The name of the attack.
     * @param damage The damage value of the attack.
     * @param energy The energy cost required to perform the attack.
     */
    public Attack(String name, int damage, int energy) {
        this.DAMAGE = damage;
        this.ENERGY_TO_ATTACK = energy;
        this.NAME = name.toUpperCase();
    }

    /**
     * Returns a string representation of the Attack object.
     *
     * @return A string representation of the Attack object.
     */
    @Override
    public String toString() {
        return "Attack{" +
                "DAMAGE=" + DAMAGE +
                ", ENERGY_TO_ATTACK=" + ENERGY_TO_ATTACK +
                ", NAME='" + NAME + '\'' +
                '}';
    }
}
