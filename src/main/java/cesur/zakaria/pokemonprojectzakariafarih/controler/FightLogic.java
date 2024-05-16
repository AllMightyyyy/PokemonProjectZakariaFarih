package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Fight;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Capacity;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Pokemon;

public class FightLogic {

    private final Fight fight;

    public FightLogic(Fight fight) {
        this.fight = fight;
    }

    /**
     * Executes an attack by the current trainer's active Pokémon using the specified capacity.
     * @param capacity The capacity to use for the attack.
     * @return A result object containing information about the attack.
     */
    public AttackResult executeAttack(Capacity capacity) {
        Pokemon attacker = fight.getCurrentTrainer().getPokemon();
        Pokemon defender = fight.getNonCurrentTrainer().getPokemon();

        // Subtract PP and deal damage
        capacity.subtractPP();
        boolean defenderKnockedOut = defender.takeDmgCap(capacity);

        return new AttackResult(attacker, defender, capacity, defenderKnockedOut);
    }

    public static class AttackResult {
        private final Pokemon attacker;
        private final Pokemon defender;
        private final Capacity capacityUsed;
        private final boolean defenderKnockedOut;

        public AttackResult(Pokemon attacker, Pokemon defender, Capacity capacityUsed, boolean defenderKnockedOut) {
            this.attacker = attacker;
            this.defender = defender;
            this.capacityUsed = capacityUsed;
            this.defenderKnockedOut = defenderKnockedOut;
        }

        public Pokemon getAttacker() {
            return attacker;
        }

        public Pokemon getDefender() {
            return defender;
        }

        public Capacity getCapacityUsed() {
            return capacityUsed;
        }

        public boolean isDefenderKnockedOut() {
            return defenderKnockedOut;
        }
    }
}
