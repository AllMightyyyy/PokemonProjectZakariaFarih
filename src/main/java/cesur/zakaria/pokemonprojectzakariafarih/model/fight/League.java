package cesur.zakaria.pokemonprojectzakariafarih.model.fight;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokedex.Pokedex;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class League implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Bot> Bots;

	public League(ArrayList<Bot> Bots) {
		super();
		this.Bots = Bots;
	}

	public int getActualBot() {
		for (int i = 0; i < Bots.size(); i++) {
			if (!Bots.get(i).loose()) {
				return i;
			}
		}
		return -1;
	}

	public Bot getBot(int index) {
		return Bots.get(index);
	}

	public  static Pokemon pokemonRandom() throws IOException {
		Random rand = new Random();
		Pokedex pokedex = Pokedex.getPokedex();
		PokemonSpecie pokemonSpecie = pokedex.get(1+rand.nextInt(pokedex.size()+1));
		CapacityDeck deck = CapacitiesHelper.getCapacityDeck();
		var capacities = deck.get(pokemonSpecie.getTypes());
		int[] random = new int[4];
		Capacity[] capcitiesPokemon = new Capacity[4];
		for (int i = 0; i < random.length; i++) {
			random[i]=randomValide(4, random, rand.nextInt(capacities.size()));
		}
		for (int capacity : random) {
			System.out.println(capacity);
		}
		Arrays.sort(random);
		int k = 0;
		int l = 0;
		for (var capcity : capacities) {
			if (l<4 && k == random[l]) {
				capcitiesPokemon[l] = capcity;
				l++;
			}
			k++;
		}
		return new Pokemon(pokemonSpecie, capcitiesPokemon, StatistiquePokemon.RandomStat());
	}

	private static int randomValide(int maxSize, int[] random, int j) {
		
		for (int i = 0; i < random.length; i++) {
			if (j == random[i]) {
				j = randomValide(maxSize, random, (new Random()).nextInt(maxSize));
				break;
			}
		}
		return j;
	}
	

	public static League createDefaultLeague() throws IOException {
		ArrayList<Bot> Bots = new ArrayList<Bot>();
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

	public ArrayList<Bot> getBots() {
		return Bots;
	}

	@Override
	public String toString() {
		return "League [Bots=" + Bots + "]";
	}
	
	

}
