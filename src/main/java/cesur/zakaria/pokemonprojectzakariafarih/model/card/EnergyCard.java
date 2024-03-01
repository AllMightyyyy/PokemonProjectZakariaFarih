package cesur.zakaria.pokemonprojectzakariafarih.model.card;

public class EnergyCard extends Card {
    private String type; // Represents the type of energy (e.g., "Fire", "Water")

    public EnergyCard(String id, String name, String image, String type) {
        super(id, name, image, "Energy");
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // Setter for type if necessary
    public void setType(String type) {
        this.type = type;
    }

    // Other methods specific to energy cards if needed
    @Override
    public String getCardType() {
        return "Energy";
    }
}
