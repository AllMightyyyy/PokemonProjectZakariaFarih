package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CapacityDeck{

	private static final long serialVersionUID = 1L;
	private final HashMap<EnumPokemonType,Set<Capacity>> hashMap =new HashMap<EnumPokemonType, Set<Capacity>>();

	public void add(EnumPokemonType type, Capacity cap) {
		var x=hashMap.get(type);
				x.add(cap);	
	}
	
	public boolean containsKey(EnumPokemonType e) {
		return hashMap.containsKey(e);
	}

	public Set<Capacity> put(EnumPokemonType key, Capacity value) {
		// TODO Auto-generated method stub
		var x=new HashSet<Capacity>();
		x.add(value);
		return hashMap.put(key, x);
	}
	

	public Set<Capacity> get(PokemonType types){
		Set<Capacity> capacities=new HashSet<Capacity>();
		
		while (types.hasNext()) {
			EnumPokemonType type=types.next();
			capacities.addAll(hashMap.get(type));
			
		}
		capacities.addAll(hashMap.get(EnumPokemonType.NORMAL));
		types.resetIterator();
		return capacities;
	}
	
	
	
}