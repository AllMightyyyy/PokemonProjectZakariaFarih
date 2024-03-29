package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

public class Attack {
    public final int DAMAGE;
    public final int ENERGY_TO_ATTACK;
    private final String NAME;

    public Attack(String name, int damage, int energy) {
        this.DAMAGE = damage;
        this.ENERGY_TO_ATTACK = energy;
        this.NAME = name.toUpperCase();
    }

    @Override
    public String toString() {
        return "Attack{" +
                "DAMAGE=" + DAMAGE +
                ", ENERGY_TO_ATTACK=" + ENERGY_TO_ATTACK +
                ", NAME='" + NAME + '\'' +
                '}';
    }
}
