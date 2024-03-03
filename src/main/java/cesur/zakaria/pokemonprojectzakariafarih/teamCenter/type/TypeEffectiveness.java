package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TypeEffectiveness {

    @JsonProperty("immune2")
    private List<String> immune;
    private List<String> resists;

    @JsonProperty("weak2")
    private List<String> weak;

    public TypeEffectiveness() {
    }

    public TypeEffectiveness(List<String> immune) {
        this.immune = immune;
    }



    public TypeEffectiveness(List<String> immune, List<String> resists, List<String> weak) {
        this.immune = immune;
        this.resists = resists;
        this.weak = weak;
    }

    // Getters and setters

    public List<String> getImmune() {
        return immune;
    }

    public void setImmune(List<String> immune) {
        this.immune = immune;
    }

    public List<String> getResists() {
        return resists;
    }

    public void setResists(List<String> resists) {
        this.resists = resists;
    }

    public List<String> getWeak() {
        return weak;
    }

    public void setWeak(List<String> weak) {
        this.weak = weak;
    }
}
