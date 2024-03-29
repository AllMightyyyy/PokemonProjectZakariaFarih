package cesur.zakaria.pokemonprojectzakariafarih.controler;

import javafx.scene.layout.BorderPane;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Capacity;

/**
 * A class that represents a button for a specific {@link Capacity}.
 *
 * @author Zakaria
 *
 */
public class CapacityButton extends BorderPane {

    /**
     * The capacity that the button represents.
     */
    private final Capacity capacity;

    /**
     * Creates a new CapacityButton with the specified capacity.
     *
     * @param capacity the capacity that the button represents
     */
    public CapacityButton(Capacity capacity) {
        super();
        this.capacity = capacity;
    }

    /**
     * Returns the capacity that the button represents.
     *
     * @return the capacity that the button represents
     */
    public Capacity getCapacity() {
        return capacity;
    }
}