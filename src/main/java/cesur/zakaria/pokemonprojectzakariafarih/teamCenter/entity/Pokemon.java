package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/*
    "bulbasaur": {
        "base_id": 1,
        "form_id": 0,
        "name": "Bulbasaur",
        "pokemon_type": [ "grass", "poison" ],
        "egg_group": [ "monster", "grass" ],
        "gender": [ "mf" ],
        "gender_ratio": 1,
        "shape": 5,
        "color": "green",
        "experience_group": "Medium Slow",
        "generation": 1,
        "evolution_ids": [ [ 2, 0 ] ]
    }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    @JsonProperty("base_id")
    private int baseId;

    @JsonProperty("has_gigantamax")
    private boolean hasGigantamax;

    @JsonProperty("form_id")
    private int formId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("pokemon_type")
    private List<String> pokemonType;

    @JsonProperty("egg_group")
    private List<String> eggGroup;

    @JsonProperty("shape")
    private int shape;


    @JsonProperty("gender")
    private List<String> gender;

    @JsonProperty("gender_ratio")
    private int genderRatio;

    @JsonProperty("color")
    private String color;

    @JsonProperty("experience_group")
    private String experienceGroup;

    @JsonProperty("generation")
    private int generation;

    @JsonProperty("evolution_ids")

    private List<List<Integer>> evolutionIds;
    private List<String> resistances = new ArrayList<>();
    private List<String> weaknesses = new ArrayList<>();
    private List<String> immunities = new ArrayList<>();
    private String imagePath;
    private String shapeImagePath;
    private List<String> typeImagePaths;


    // Constructors, getters, and setters

    public Pokemon() {
    }

    public int getBaseId() {
        return baseId;
    }

    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(List<String> pokemonType) {
        this.pokemonType = pokemonType;
    }

    public void setShapeImagePath(String shapeImagePath) {
        this.shapeImagePath = shapeImagePath;
    }

    public List<String> getEggGroup() {
        return eggGroup;
    }

    public void setEggGroup(List<String> eggGroup) {
        this.eggGroup = eggGroup;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public int getGenderRatio() {
        return genderRatio;
    }

    public void setGenderRatio(int genderRatio) {
        this.genderRatio = genderRatio;
    }

    public String getShapeImagePath() {
        return shapeImagePath;
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getExperienceGroup() {
        return experienceGroup;
    }

    public void setExperienceGroup(String experienceGroup) {
        this.experienceGroup = experienceGroup;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public List<List<Integer>> getEvolutionIds() {
        return evolutionIds;
    }

    public void setEvolutionIds(List<List<Integer>> evolutionIds) {
        this.evolutionIds = evolutionIds;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isHasGigantamax() {
        return hasGigantamax;
    }

    public void setHasGigantamax(boolean hasGigantamax) {
        this.hasGigantamax = hasGigantamax;
    }

    public List<String> getResistances() {
        return resistances;
    }

    public void setResistances(List<String> resistances) {
        this.resistances = resistances;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<String> getImmunities() {
        return immunities;
    }

    public void setImmunities(List<String> immunities) {
        this.immunities = immunities;
    }

    public List<String> getTypeImagePaths() {
        return typeImagePaths;
    }

    public void setTypeImagePaths(List<String> typeImagePaths) {
        this.typeImagePaths = typeImagePaths;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "baseId=" + baseId +
                ", formId=" + formId +
                ", name='" + name + '\'' +
                ", pokemonType=" + pokemonType +
                ", eggGroup=" + eggGroup +
                ", gender=" + gender +
                ", genderRatio=" + genderRatio +
                ", shape=" + shape +
                ", color='" + color + '\'' +
                ", experienceGroup='" + experienceGroup + '\'' +
                ", generation=" + generation +
                ", evolutionIds=" + evolutionIds +
                '}';
    }

    /**
     * Generates the image file name based on the Pokémon's properties.
     *
     * @return the constructed image file name.
     */
    public String generateImageName() {
        String baseIdFormatted = String.format("%04d", this.baseId);
        String formIdFormatted = String.format("%03d", this.formId);
        String genderAbbreviation = this.gender.contains("mf") ? "mf" : this.gender.get(0);
        String formAbbreviation = "n";

        return baseIdFormatted + "_" + formIdFormatted + "_" + genderAbbreviation + "_" + formAbbreviation + ".png";
    }

    /**
     * Generates the image file path for each type of the Pokémon.
     *
     * @return the list of constructed image file paths for each type.
     */
    public List<String> generateTypeImagePaths() {
        List<String> paths = new ArrayList<>();
        for (String type : this.pokemonType) {
            paths.add("path/to/types/" + type.toLowerCase() + ".svg"); // Replace with the actual path
        }
        return paths;
    }


}
