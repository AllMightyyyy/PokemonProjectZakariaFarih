package cesur.zakaria.pokemonprojectzakariafarih.pokedex.model;

import java.awt.*;

/**
 * The PokemonColor enum represents the color associated with different types of Pokemon.
 */
public enum PokemonColor {

    /**
     * The Normal.
     */
    NORMAL(new Color(168, 167, 122)),
    /**
     * The Fire.
     */
    FIRE(new Color(251, 146, 60)),
    /**
     * The Water.
     */
    WATER(new Color(37, 99, 235)),
    /**
     * The Electric.
     */
    ELECTRIC(new Color(252, 212, 2)),
    /**
     * The Grass.
     */
    GRASS(new Color(34, 197, 94)),
    /**
     * The Ice.
     */
    ICE(new Color(96, 165, 250)),
    /**
     * The Fighting.
     */
    FIGHTING(new Color(194, 46, 40)),
    /**
     * The Poison.
     */
    POISON(new Color(163, 62, 161)),
    /**
     * The Ground.
     */
    GROUND(new Color(226, 191, 101)),
    /**
     * The Flying.
     */
    FLYING(new Color(169, 143, 243)),
    /**
     * The Psychic.
     */
    PSYCHIC(new Color(249, 85, 135)),
    /**
     * The Bug.
     */
    BUG(new Color(166, 185, 26)),
    /**
     * The Rock.
     */
    ROCK(new Color(182, 161, 54)),
    /**
     * The Ghost.
     */
    GHOST(new Color(115, 87, 151)),
    /**
     * The Dragon.
     */
    DRAGON(new Color(111, 53, 252)),
    /**
     * The Dark.
     */
    DARK(new Color(112, 87, 70)),
    /**
     * The Steel.
     */
    STEEL(new Color(183, 183, 206)),
    /**
     * The Fairy.
     */
    FAIRY(new Color(214, 133, 173));

    private final Color rgbColor;

    /**
     * Constructs a PokemonColor object with the specified RGB color.
     *
     * @param rgbColor The RGB color associated with the Pokemon type.
     */
    PokemonColor(Color rgbColor) {
        this.rgbColor = rgbColor;
    }

    /**
     * Retrieves the RGB color associated with the Pokemon type.
     *
     * @return The RGB color associated with the Pokemon type.
     */
    public Color getRgbColor() {
        return rgbColor;
    }

}
