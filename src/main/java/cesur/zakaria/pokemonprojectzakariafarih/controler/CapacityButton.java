package cesur.zakaria.pokemonprojectzakariafarih.controler;

import javafx.scene.layout.BorderPane;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Capacity;

public class CapacityButton extends BorderPane {
	private final Capacity capacity;
	public CapacityButton(Capacity capacity) {
		super();
		this.capacity = capacity;
	}
	public Capacity getCapacity() {
		return capacity;
	}
}
