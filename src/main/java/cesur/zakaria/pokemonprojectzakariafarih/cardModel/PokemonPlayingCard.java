package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

public abstract class PokemonPlayingCard extends PlayingCard {
    public enum Elements { WATER, FIRE, PLANT };

    public static final int BONUS_ATTACK_DEFENSE = 2;
    public final int MAX_HP;
    private int actualHp;
    private Attack attack;
    private int actualEnergy;

    public PokemonPlayingCard(String namePokemon, String nameAttack, PlayingCard.Rarity rarity, int hp, int damage, int energyToAttack) {
        super(namePokemon, rarity);

        if (hp > 0) this.MAX_HP = actualHp = hp;
        else this.MAX_HP = actualHp = 100;

        if (damage > 0 && energyToAttack > 0) attack = new Attack(nameAttack, damage, energyToAttack);
        else attack = new Attack(nameAttack, 0, 0);
    }

    public int getActualHp() {
        return this.actualHp;
    }

    public void giveDamage(int damage) {
        if (damage <= this.actualHp) this.actualHp -= damage;
        else this.actualHp = 0;
    }

    public int getActualEnergy() {
        return this.actualEnergy;
    }

    public boolean chargeEnergy(int energy) {
        if (energy > 0) {
            this.actualEnergy += energy;
            return true;
        }
        return false;
    }

    public boolean consumeEnergy(int consumedEnergy) {
        if (this.actualEnergy >= consumedEnergy) {
            this.actualEnergy -= consumedEnergy;
            return true;
        }
        return false;
    }

    public Attack getAttack() {
        return this.attack;
    }

    public abstract boolean attack(PokemonPlayingCard other);
}
