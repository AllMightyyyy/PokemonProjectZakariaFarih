package cesur.zakaria.pokemonprojectzakariafarih.model.fight;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokedex.Pokedex;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.*;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * The League class represents a Pokemon league, consisting of multiple bots that a player must defeat to progress.
 * It provides methods to create a league, retrieve information about bots, and manage battles within the league.
 */
public class League implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;
	private final ArrayList<Bot> Bots;

	/**
	 * Constructs a League object with the given list of bots.
	 *
	 * @param Bots The list of bots participating in the league.
	 */
	public League(ArrayList<Bot> Bots) {
		this.Bots = Bots;
	}

	/**
	 * Gets the index of the current bot in the league that has not been defeated.
	 *
	 * @return The index of the current bot, or -1 if all bots have been defeated.
	 */
	public int getActualBot() {
		for (int i = 0; i < Bots.size(); i++) {
			if (!Bots.get(i).loose()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Gets the bot at the specified index in the league.
	 *
	 * @param index The index of the bot.
	 * @return The bot at the specified index.
	 */
	public Bot getBot(int index) {
		return Bots.get(index);
	}

	/**
	 * Generates a random Pokemon with random capacities.
	 *
	 * @return A randomly generated Pokemon.
	 * @throws IOException if an I/O error occurs while retrieving Pokemon data.
	 */
	public static Pokemon pokemonRandom() throws IOException {
		Random rand = new Random();
		Pokedex pokedex = Pokedex.getPokedex();
		PokemonSpecie pokemonSpecie = pokedex.get(1 + rand.nextInt(pokedex.size() + 1));
		CapacityDeck deck = CapacitiesHelper.getCapacityDeck();
		var capacities = deck.get(pokemonSpecie.getTypes());
		int[] random = new int[4];
		Capacity[] capcitiesPokemon = new Capacity[4];
		for (int i = 0; i < random.length; i++) {
			random[i] = randomValide(4, random, rand.nextInt(capacities.size()));
		}
		Arrays.sort(random);
		int k = 0;
		int l = 0;
		for (var capcity : capacities) {
			if (l < 4 && k == random[l]) {
				capcitiesPokemon[l] = capcity;
				l++;
			}
			k++;
		}
		return new Pokemon(pokemonSpecie, capcitiesPokemon, StatistiquePokemon.RandomStat());
	}

	private static int randomValide(int maxSize, int[] random, int j) {
        for (int k : random) {
            if (j == k) {
                j = randomValide(maxSize, random, (new Random()).nextInt(maxSize));
                break;
            }
        }
		return j;
	}

	/**
	 * Creates a default league with randomly generated bots and their Pokemon teams.
	 *
	 * @return A default League object.
	 * @throws IOException if an I/O error occurs while generating Pokemon data.
	 */
	public static League createDefaultLeague() throws IOException {
		ArrayList<Bot> Bots = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Pokemon[] pokemons = new Pokemon[5];
			for (int j = 0; j < pokemons.length; j++) {
				pokemons[j] = pokemonRandom();
			}
			Bot Bot = new Bot("LeagueBot " + i, pokemons);
			Bots.add(Bot);
		}

		Pokemon[] pokemons = new Pokemon[6];
		for (int j = 0; j < pokemons.length; j++) {
			pokemons[j] = pokemonRandom();
		}
		Bot Bot = new Bot("LeagueMaster", pokemons);
		Bots.add(Bot);

		return new League(Bots);
	}

	/**
	 * Gets the list of bots participating in the league.
	 *
	 * @return The list of bots.
	 */
	public ArrayList<Bot> getBots() {
		return Bots;
	}

	@Override
	public String toString() {
		return "League [Bots=" + Bots + "]";
	}
}