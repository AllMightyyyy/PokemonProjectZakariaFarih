package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.type;

import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.type.TypeEffectiveness;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Represents data for Pokémon types and their effectiveness.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeData {
    private int generation;

    // Renamed to match the JSON structure
    private Map<String, TypeEffectiveness> typeData;

    // Default constructor
    public TypeData() {
    }

    /**
     * Gets the map containing type effectiveness data.
     *
     * @return the map of type effectiveness data.
     */
    @JsonProperty("type_data")
    public Map<String, TypeEffectiveness> getTypeEffectivenessMap() {
        return typeData;
    }

    /**
     * Sets the map containing type effectiveness data.
     *
     * @param typeData the map of type effectiveness data to set.
     */
    @JsonProperty("type_data")
    public void setTypeEffectivenessMap(Map<String, TypeEffectiveness> typeData) {
        this.typeData = typeData;
    }

    /**
     * Gets the generation associated with the type data.
     *
     * @return the generation.
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Sets the generation associated with the type data.
     *
     * @param generation the generation to set.
     */
    public void setGeneration(int generation) {
        this.generation = generation;
    }
}
