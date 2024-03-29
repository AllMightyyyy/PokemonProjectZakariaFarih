package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

public abstract class PlayingCard {
    public enum Rarity { COMMON, UNCOMMON, RARE }

    private String name;
    private Rarity rarity;

    public PlayingCard(String name, Rarity rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    public String getName() {
        return this.name;
    }

    public Rarity getRarity() {
        return this.rarity;
    }
}
