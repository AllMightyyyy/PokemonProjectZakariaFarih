package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

public class PokemonPlant extends PokemonPlayingCard {
    public static final PokemonPlayingCard.Elements WEAKNESS = Elements.FIRE;
    public static final PokemonPlayingCard.Elements STRENGTH = Elements.WATER;

    public PokemonPlant (String namePokemon, String nameAttack, PlayingCard.Rarity rarity, int hp, int damage, int energyToAttack) {
        super(namePokemon, nameAttack, rarity, hp, damage, energyToAttack);
    }

    @Override
    public boolean attack(PokemonPlayingCard other) {
        Attack attack = super.getAttack();

        // Check if you have energy to make the attack and, if so, consume and continue
        boolean sufficientEnergy = super.consumeEnergy(attack.ENERGY_TO_ATTACK);
        if (!sufficientEnergy) return false;

        // Verify if someone has attack or defense bonus
        final int BONUS_ATTACK = other instanceof PokemonWater ? PokemonPlayingCard.BONUS_ATTACK_DEFENSE : 1;
        final int BONUS_DEFENSE = other instanceof PokemonFire ? PokemonPlayingCard.BONUS_ATTACK_DEFENSE : 1;

        // Calculate damage of attack
        int damageAttack = ( attack.DAMAGE * BONUS_ATTACK ) / BONUS_DEFENSE;

        // give damage to the "other" card
        other.giveDamage(damageAttack);

        return true;
    }

    @Override
    public String toString() {
        return "NOME: " + super.getName() + " | "
                + "RARIDADE: " + super.getRarity() + " | "
                + "HP: " + super.getActualHp() + " | "
                + "ENERGIA CARREGADA: " + super.getActualEnergy() + " | "
                + super.getAttack().toString();
    }
}
