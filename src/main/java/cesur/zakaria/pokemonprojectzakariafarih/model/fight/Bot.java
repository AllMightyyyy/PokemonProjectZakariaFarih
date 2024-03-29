package cesur.zakaria.pokemonprojectzakariafarih.model.fight;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Capacity;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Pokemon;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Bot class represents a computer-controlled opponent in a Pokemon battle.
 * It extends the Trainer class and provides methods for the bot to choose actions during a fight.
 */
public class Bot extends Trainer {

	/**
	 * Constructs a new Bot with the given name and Pokemon team.
	 *
	 * @param name     The name of the bot.
	 * @param pokemons An array of Pokemon representing the bot's team.
	 */
	public Bot(String name, Pokemon[] pokemons) {
		super(name, pokemons);
	}

	/**
	 * Simulates a fight action by the bot.
	 * The bot randomly selects one of its usable capacities and returns it.
	 *
	 * @return A Capacity representing the capacity chosen by the bot for the fight action.
	 */
	public Capacity fight() {
		ArrayList<Capacity> usableCapacities = new ArrayList<>();
		for (Capacity capacity : getPokemon().getCapacities()) {
			if (capacity != null && capacity.isUsable()) {
				usableCapacities.add(capacity);
			}
		}

		if (usableCapacities.isEmpty()) {
			return null;
		}

		Random random = new Random();
		return usableCapacities.get(random.nextInt(usableCapacities.size()));
	}

	@Override
	/*
	  Chooses a Pokemon from the bot's team for switching during battle.
	  The bot selects a random usable Pokemon from its team and returns it.

	  @param i The index of the current active Pokemon in the bot's team.
	 * @return A Pokemon representing the bot's choice for the new active Pokemon.
	 */
	public Pokemon changePokemon(int i) {
		ArrayList<Integer> usablePokemons = new ArrayList<>();
		for (int j = 0; j < teamSize(); j++) {
			if (isPokemonAlive(j)) {
				usablePokemons.add(j);
			}
		}

		Random random = new Random();
		return super.changePokemon(usablePokemons.get(random.nextInt(usablePokemons.size())));
	}
}
