package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.PokemonSpecie;
import javafx.scene.layout.BorderPane;

/**
 * The type Pokemon button.
 */
public class PokemonButton extends BorderPane {
	private final PokemonSpecie pokemonSpecie;

	/**
	 * Constructs a new PokemonButton with the provided Pokemon species.
	 *
	 * @param pokemonSpecie The Pokemon species associated with this button.
	 */
	public PokemonButton(PokemonSpecie pokemonSpecie) {
		super();
		this.pokemonSpecie = pokemonSpecie;
	}

	/**
	 * Retrieves the Pokemon species associated with this button.
	 *
	 * @return The Pokemon species associated with this button.
	 */
	public PokemonSpecie getPokemonSpecie() {
		return pokemonSpecie;
	}
}
