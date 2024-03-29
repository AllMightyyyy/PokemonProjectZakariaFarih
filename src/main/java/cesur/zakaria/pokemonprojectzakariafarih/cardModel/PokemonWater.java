package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

public class PokemonWater extends PokemonPlayingCard {
    public static final PokemonPlayingCard.Elements WEAKNESS = Elements.PLANT;
    public static final PokemonPlayingCard.Elements STRENGTH = Elements.FIRE;

    public PokemonWater(String namePokemon, String nameAttack, PlayingCard.Rarity rarity, int hp, int damage, int energyToAttack) {
        super(namePokemon, nameAttack, rarity, hp, damage, energyToAttack);
    }

    @Override
    public boolean attaque(PokemonPlayingCard other) {
        Attack attack = super.getAttack();

        // check if you have energy to make the attack and, if so, consume and continue
        boolean sufficientEnergy = super.consumeEnergy(attack.ENERGY_TO_ATTACK);
        if (!sufficientEnergy) return false;

        // check if someone has an attack/defense bonus
        final int BONUS_ATTACK = other instanceof PokemonFire ? PokemonPlayingCard.BONUS_ATTACK_DEFENSE : 1;
        final int BONUS_DEFENSE = other instanceof PokemonPlant ? PokemonPlayingCard.BONUS_ATTACK_DEFENSE : 1;

        //calculate damage of attack
        int damageAttack = ( attack.DAMAGE * BONUS_ATTACK ) / BONUS_DEFENSE;

        //deal damage to the "other" card
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
