package cesur.zakaria.pokemonprojectzakariafarih.controler;

import javafx.scene.layout.BorderPane;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.PokemonSpecie;

public class PokemonButton extends BorderPane{
	private final PokemonSpecie pokemonSpecie;

	public PokemonButton(PokemonSpecie pokemonSpecie) {
		super();
		this.pokemonSpecie = pokemonSpecie;
	}
	
	public PokemonSpecie getPokemonSpecie() {
		return pokemonSpecie;
	}
}
