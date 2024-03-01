package cesur.zakaria.pokemonprojectzakariafarih.model.card;

import cesur.zakaria.pokemonprojectzakariafarih.controllers.Attack;
import cesur.zakaria.pokemonprojectzakariafarih.controllers.StatusCondition;

import java.util.List;

public class PokemonCard extends Card {
    private int hp;
    private List<String> types;
    private String evolveFrom;
    private List<Attack> attacks;
    private StatusCondition status = StatusCondition.NONE;
    public PokemonCard(String id, String name, String image, String category, String illustrator, String rarity, Integer hp, List<String> types, String evolveFrom, String description, Integer stage) {
        super(id, name, image, "Pokemon");
        this.hp = hp;
        this.types = types;
        this.evolveFrom = evolveFrom;
    }
    public PokemonCard(String id, String name, String image, int hp, List<String> types, String evolveFrom, List<Attack> attacks) {
        super(id, name, image, "Pokemon");
        this.hp = hp;
        this.types = types;
        this.evolveFrom = evolveFrom;
        this.attacks = attacks;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public List<String> getTypes() {
        return types;
    }

    @Override
    public void setTypes(List<String> types) {
        this.types = types;
    }

    @Override
    public String getEvolveFrom() {
        return evolveFrom;
    }

    @Override
    public void setEvolveFrom(String evolveFrom) {
        this.evolveFrom = evolveFrom;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public StatusCondition getStatus() {
        return status;
    }

    public void setStatus(StatusCondition status) {
        this.status = status;
    }
    public boolean isBasicPokemon() {
        // Check if the stage of the Pokemon is "Basic"
        return getStage() == 0; // Assuming 0 represents the stage for a basic Pokemon
    }

    @Override
    public String getCardType() {
        return "PokemonCard";
    }
    /*
    can now use :
    TCGdexAPI api = new TCGdexAPI();
    String json = api.getSingleCard("en", "swsh3-136"); Example for fetching a single card
    You would parse this JSON to create a PokemonCard or EnergyCard instance
    display cards :
    ImageView cardImage = new ImageView(new Image(pokemonCard.getImage()));
    Label nameLabel = new Label(pokemonCard.getName());
    // Add labels for HP, types, etc., and arrange them in a layout
    and then create a VBOX or GridPane to arrange them
     */
}
