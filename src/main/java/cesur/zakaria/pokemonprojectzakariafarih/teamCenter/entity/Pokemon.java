/**
 * Represents a Pokémon entity with its properties.
 */
package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Pokemon.
 */
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
    private List<String> pokemonTypes;


    // Constructors, getters, and setters

    /**
     * Instantiates a new Pokemon.
     */
    public Pokemon() {
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets pokemon type.
     *
     * @return the pokemon type
     */
    public List<String> getPokemonType() {
        return pokemonType;
    }

    /**
     * Sets pokemon type.
     *
     * @param pokemonType the pokemon type
     */
    public void setPokemonType(List<String> pokemonType) {
        this.pokemonType = pokemonType;
    }

    /**
     * Sets shape image path.
     *
     * @param shapeImagePath the shape image path
     */
    public void setShapeImagePath(String shapeImagePath) {
        this.shapeImagePath = shapeImagePath;
    }

    /**
     * Gets egg group.
     *
     * @return the egg group
     */
    public List<String> getEggGroup() {
        return eggGroup;
    }

    /**
     * Sets egg group.
     *
     * @param eggGroup the egg group
     */
    public void setEggGroup(List<String> eggGroup) {
        this.eggGroup = eggGroup;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public List<String> getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    /**
     * Gets gender ratio.
     *
     * @return the gender ratio
     */
    public int getGenderRatio() {
        return genderRatio;
    }

    /**
     * Sets gender ratio.
     *
     * @param genderRatio the gender ratio
     */
    public void setGenderRatio(int genderRatio) {
        this.genderRatio = genderRatio;
    }

    /**
     * Gets shape image path.
     *
     * @return the shape image path
     */
    public String getShapeImagePath() {
        return shapeImagePath;
    }

    /**
     * Gets shape.
     *
     * @return the shape
     */
    public int getShape() {
        return shape;
    }

    /**
     * Sets shape.
     *
     * @param shape the shape
     */
    public void setShape(int shape) {
        this.shape = shape;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets experience group.
     *
     * @return the experience group
     */
    public String getExperienceGroup() {
        return experienceGroup;
    }

    /**
     * Sets experience group.
     *
     * @param experienceGroup the experience group
     */
    public void setExperienceGroup(String experienceGroup) {
        this.experienceGroup = experienceGroup;
    }

    /**
     * Gets generation.
     *
     * @return the generation
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Sets generation.
     *
     * @param generation the generation
     */
    public void setGeneration(int generation) {
        this.generation = generation;
    }

    /**
     * Gets evolution ids.
     *
     * @return the evolution ids
     */
    public List<List<Integer>> getEvolutionIds() {
        return evolutionIds;
    }

    /**
     * Sets evolution ids.
     *
     * @param evolutionIds the evolution ids
     */
    public void setEvolutionIds(List<List<Integer>> evolutionIds) {
        this.evolutionIds = evolutionIds;
    }

    /**
     * Gets image path.
     *
     * @return the image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets image path.
     *
     * @param imagePath the image path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Is has gigantamax boolean.
     *
     * @return the boolean
     */
    public boolean isHasGigantamax() {
        return hasGigantamax;
    }

    /**
     * Sets has gigantamax.
     *
     * @param hasGigantamax the has gigantamax
     */
    public void setHasGigantamax(boolean hasGigantamax) {
        this.hasGigantamax = hasGigantamax;
    }

    /**
     * Gets resistances.
     *
     * @return the resistances
     */
    public List<String> getResistances() {
        return resistances;
    }

    /**
     * Sets resistances.
     *
     * @param resistances the resistances
     */
    public void setResistances(List<String> resistances) {
        this.resistances = resistances;
    }

    /**
     * Gets weaknesses.
     *
     * @return the weaknesses
     */
    public List<String> getWeaknesses() {
        return weaknesses;
    }

    /**
     * Sets weaknesses.
     *
     * @param weaknesses the weaknesses
     */
    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    /**
     * Gets immunities.
     *
     * @return the immunities
     */
    public List<String> getImmunities() {
        return immunities;
    }

    /**
     * Sets immunities.
     *
     * @param immunities the immunities
     */
    public void setImmunities(List<String> immunities) {
        this.immunities = immunities;
    }

    /**
     * Gets type image paths.
     *
     * @return the type image paths
     */
    public List<String> getTypeImagePaths() {
        return typeImagePaths;
    }

    /**
     * Sets type image paths.
     *
     * @param typeImagePaths the type image paths
     */
    public void setTypeImagePaths(List<String> typeImagePaths) {
        this.typeImagePaths = typeImagePaths;
    }

    /**
     * Gets pokemon types.
     *
     * @return the pokemon types
     */
    public List<String> getPokemonTypes() {
        return pokemonTypes;
    }

    /**
     * Sets pokemon types.
     *
     * @param pokemonTypes the pokemon types
     */
    public void setPokemonTypes(List<String> pokemonTypes) {
        this.pokemonTypes = pokemonTypes;
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
            paths.add("/cesur/zakaria/pokemonprojectzakariafarih/images/types/" + type.toLowerCase() + ".png"); // Replace with the actual path
        }
        return paths;
    }


    /**
     * Sets base id.
     *
     * @param baseId the base id
     */
    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    /**
     * Sets form id.
     *
     * @param formId the form id
     */
    public void setFormId(int formId) {
        this.formId = formId;
    }
}
