package cesur.zakaria.pokemonprojectzakariafarih.model.card;

import java.util.List;

public class Card {
    protected String id; // Card Unique ID
    protected String name; // Card Name
    protected String image; // Card Image URL
    protected String category; // Card category (Pokemon, Energy, Trainer)
    protected String illustrator; // Card illustrator
    protected String rarity; // Card rarity
    protected Integer hp; // The Pokémon HP (applicable to Pokémon cards)
    protected List<String> types; // The types of the Pokémon
    protected String evolveFrom; // The Pokémon name it evolves from (if any)
    protected String description; // Card description
    protected Integer stage; // The Pokémon Stage (e.g., Basic, Stage 1, Stage 2)
    // Additional fields from API can be added here as needed

    // Constructor
    public Card(String id, String name, String image, String category, String illustrator, String rarity, Integer hp, List<String> types, String evolveFrom, String description, Integer stage) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.illustrator = illustrator;
        this.rarity = rarity;
        this.hp = hp;
        this.types = types;
        this.evolveFrom = evolveFrom;
        this.description = description;
        this.stage = stage;
    }

    public Card(String id, String name, String image, String pokemon) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = pokemon;
    }

    // Getters and Setters
    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getEvolveFrom() {
        return evolveFrom;
    }

    public void setEvolveFrom(String evolveFrom) {
        this.evolveFrom = evolveFrom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getCardType() {
        return "Card";
    }

    // Note: Depending on your application design, you might want to create subclasses for different types of cards (e.g., PokemonCard, EnergyCard, TrainerCard) to handle category-specific attributes.
}
