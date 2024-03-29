package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the effectiveness of a Pokémon type against other types.
 */
public class TypeEffectiveness {

    @JsonProperty("immune2")
    private List<String> immune;
    private List<String> resists;

    @JsonProperty("weak2")
    private List<String> weak;

    // Default constructor
    public TypeEffectiveness() {
    }

    /**
     * Constructor with only immune types.
     *
     * @param immune the list of immune types.
     */
    public TypeEffectiveness(List<String> immune) {
        this.immune = immune;
    }

    /**
     * Constructor with all effectiveness types.
     *
     * @param immune   the list of immune types.
     * @param resists  the list of types resisted.
     * @param weak     the list of weak types.
     */
    public TypeEffectiveness(List<String> immune, List<String> resists, List<String> weak) {
        this.immune = immune;
        this.resists = resists;
        this.weak = weak;
    }

    // Getters and setters

    /**
     * Gets the list of immune types.
     *
     * @return the list of immune types.
     */
    public List<String> getImmune() {
        return immune;
    }

    /**
     * Sets the list of immune types.
     *
     * @param immune the list of immune types to set.
     */
    public void setImmune(List<String> immune) {
        this.immune = immune;
    }

    /**
     * Gets the list of types resisted.
     *
     * @return the list of types resisted.
     */
    public List<String> getResists() {
        return resists;
    }

    /**
     * Sets the list of types resisted.
     *
     * @param resists the list of types resisted to set.
     */
    public void setResists(List<String> resists) {
        this.resists = resists;
    }

    /**
     * Gets the list of weak types.
     *
     * @return the list of weak types.
     */
    public List<String> getWeak() {
        return weak;
    }

    /**
     * Sets the list of weak types.
     *
     * @param weak the list of weak types to set.
     */
    public void setWeak(List<String> weak) {
        this.weak = weak;
    }
}
