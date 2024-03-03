package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.type;

import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.type.TypeEffectiveness;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeData {
    private int generation;

    // Renamed to match the JSON structure
    private Map<String, TypeEffectiveness> typeData;

    public TypeData() {
    }

    // Adjusted to use the correct JSON property name
    @JsonProperty("type_data")
    public Map<String, TypeEffectiveness> getTypeEffectivenessMap() {
        return typeData;
    }

    @JsonProperty("type_data")
    public void setTypeEffectivenessMap(Map<String, TypeEffectiveness> typeData) {
        this.typeData = typeData;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }
}
