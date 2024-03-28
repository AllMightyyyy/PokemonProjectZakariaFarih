package cesur.zakaria.pokemonprojectzakariafarih.model.fight;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Capacity;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Pokemon;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Trainer{
	
	public Bot(String name, Pokemon[] pokemons) {
		super(name, pokemons);
	}
	

	public Capacity fight() {
		ArrayList<Capacity> usableCapacities = new ArrayList<Capacity>();
		for (Capacity capacity : getPokemon().getCapacities()) {
			if(capacity!=null && capacity.isUsable()) {
				usableCapacities.add(capacity);
			}
		}
		
		if(usableCapacities.size()==0) {
			return null;
		}
		
		Random random = new Random();
		return usableCapacities.get(random.nextInt(usableCapacities.size()));
	}

	@Override
	public Pokemon changePokemon(int i) {
		ArrayList<Integer> usablePokemons = new ArrayList<Integer>();
		for (int j = 0; j < teamSize(); j++) {
			if(isPokemonAlive(j)) {
				usablePokemons.add(j);
			}
		}
		
		Random random = new Random();
		return super.changePokemon(usablePokemons.get(random.nextInt(usablePokemons.size())));
	}
}
