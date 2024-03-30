package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

public class PokemonNeutral extends PokemonPlayingCard {

    public PokemonNeutral(String namePokemon, String nameAttack, PlayingCard.Rarity rarity, int hp, int damage, int energyToAttack) {
        super(namePokemon, nameAttack, rarity, hp, damage, energyToAttack);
    }

    @Override
    public boolean attack(PokemonPlayingCard other) {
        Attack attack = super.getAttack();

        // Check if you have energy to make the attack and, if so, consume and continue
        boolean sufficientEnergy = super.consumeEnergy(attack.ENERGY_TO_ATTACK);
        if (!sufficientEnergy) return false;

        // No one has attack/defense bonus
        final int BONUS_ATTACK = 1;
        final int BONUS_DEFENSE = 1;

        // Calculate the damage to other pokemon
        int damageAttack = ( attack.DAMAGE * BONUS_ATTACK ) / BONUS_DEFENSE;

        // Give damage to other card
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
