package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Represents a playing card in the Pokemon game.
 * Each card has an ID, an image ID, a playing card object, and a face-up status.
 */
public class Card {
    /** The unique identifier of the card. */
    private String id;

    /** The image identifier of the card. */
    private String imageId;

    /** The playing card object associated with this card. */
    private PlayingCard playingCard;

    /** Indicates whether the card is face up or face down. */
    private boolean faceUp;

    /** PropertyChangeSupport instance for handling property change events. */
    private final PropertyChangeSupport pcs;

    /**
     * Constructs a new Card object with the specified ID and image ID.
     *
     * @param anId      The unique identifier of the card.
     * @param anImageId The image identifier of the card.
     */
    public Card(String anId, String anImageId) {
        this.id = anId;
        this.imageId = anImageId;
        faceUp = true;
        playingCard = getNewInstance();
        pcs = new PropertyChangeSupport(this);
    }

    /**
     * Returns the ID of the card.
     *
     * @return The ID of the card.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the image ID of the card.
     *
     * @return The image ID of the card.
     */
    public String getImageId() {
        return imageId;
    }

    /**
     * Returns the playing card object associated with this card.
     *
     * @return The playing card object.
     */
    public PlayingCard getValue() {
        return playingCard;
    }

    /**
     * Checks if the card is facing up.
     *
     * @return True if the card is facing up, false otherwise.
     */
    public boolean isFacingUp() {
        return faceUp;
    }

    /**
     * Flips the card to change its face-up status.
     * Notifies property change listeners about the change.
     */
    public void flipCard() {
        boolean old = faceUp;
        faceUp = !faceUp;
        pcs.firePropertyChange("facedUp", old, faceUp);
    }

    /**
     * Retrieves a new instance of a playing card based on the image ID.
     *
     * @return A new instance of a playing card.
     */
    private PlayingCard getNewInstance() {
        switch (imageId) {
            case "img1":  return new PokemonWater("Blastoise", "Hydro Pump", PlayingCard.Rarity.RARE, 140, 60, 4);
            case "img2":  return new PokemonWater("Croconaw", "Bite", PlayingCard.Rarity.RARE, 90, 60, 3);
            case "img3":  return new PokemonWater("Feraligatr", "Hydro Splash", PlayingCard.Rarity.RARE, 160, 130, 4);
            case "img4":  return new PokemonWater("Squirtle", "Water Gun", PlayingCard.Rarity.COMMON, 50, 20, 2);
            case "img5":  return new PokemonWater("Totodile", "Water Gun", PlayingCard.Rarity.COMMON, 70, 10, 1);
            case "img6":  return new PokemonWater("Vaporeon", "Hydro Pump", PlayingCard.Rarity.RARE, 110, 60, 3);
            case "img7":  return new PokemonWater("Wartortle", "Double Spin", PlayingCard.Rarity.UNCOMMON, 80, 30, 3);
            case "img8":  return new PokemonFire("Charizard", "Flare Blitz", PlayingCard.Rarity.RARE, 170, 170, 4);
            case "img9":  return new PokemonFire("Charmander", "Tail on Fire", PlayingCard.Rarity.COMMON, 60, 10, 1);
            case "img10": return new PokemonFire("Charmeleon", "Flamethrower", PlayingCard.Rarity.RARE, 90, 100, 4);
            case "img11": return new PokemonFire("Cyndaquil", "Ember", PlayingCard.Rarity.COMMON, 60, 30, 2);
            case "img12": return new PokemonFire("Flareon", "Kindle", PlayingCard.Rarity.RARE, 110, 120, 3);
            case "img13": return new PokemonFire("Quilava", "Mini Eruption", PlayingCard.Rarity.UNCOMMON, 80, 30, 2);
            case "img14": return new PokemonFire("Typhlosion", "Flare Destroy", PlayingCard.Rarity.RARE, 150, 130, 3);
            case "img15": return new PokemonNeutral("Aipom", "Double Hit", PlayingCard.Rarity.COMMON, 60, 10, 1);
            case "img16": return new PokemonNeutral("Audino", "Doubleslap", PlayingCard.Rarity.COMMON, 80, 30, 2);
            case "img17": return new PokemonNeutral("Dodrio", "Endeavor", PlayingCard.Rarity.UNCOMMON, 90, 50, 3);
            case "img18": return new PokemonNeutral("Doduo", "Double Hit", PlayingCard.Rarity.COMMON, 70, 30, 3);
            case "img19": return new PokemonNeutral("Eevee", "Bite", PlayingCard.Rarity.COMMON, 60, 30, 3);
            case "img20": return new PokemonNeutral("Fearow", "Drill Run Double", PlayingCard.Rarity.RARE, 100, 70, 2);
            case "img21": return new PokemonNeutral("Kangaskhan", "Parental Fury", PlayingCard.Rarity.COMMON, 120, 40, 2);
            case "img22": return new PokemonNeutral("Meowth", "Pay Day", PlayingCard.Rarity.COMMON, 70, 10, 1);
            case "img23": return new PokemonNeutral("Miltank", "Sitdown Splash", PlayingCard.Rarity.COMMON, 100, 50, 3);
            case "img24": return new PokemonNeutral("Pidgey", "Quick Attack", PlayingCard.Rarity.COMMON, 60, 10, 1);
            case "img25": return new PokemonNeutral("Raticate", "Shadowy Bite", PlayingCard.Rarity.UNCOMMON, 60, 60, 1);
            case "img26": return new PokemonNeutral("Rattatta", "Bite", PlayingCard.Rarity.COMMON, 40, 10, 1);
            case "img27": return new PokemonNeutral("Sentret", "Tail Smack", PlayingCard.Rarity.COMMON, 50, 20, 2);
            case "img28": return new PokemonNeutral("Spearow", "Glide", PlayingCard.Rarity.COMMON, 60, 10, 1);
            case "img29": return new PokemonPlant("Bayleef", "Vine Whip", PlayingCard.Rarity.UNCOMMON, 90, 30, 2);
            case "img30": return new PokemonPlant("Bulbassaur", "Vine Whip", PlayingCard.Rarity.COMMON, 70, 10, 1);
            case "img31": return new PokemonPlant("Chikorita", "Tackle", PlayingCard.Rarity.COMMON, 60, 10, 1);
            case "img32": return new PokemonPlant("Ivysaur", "Razor Leaf", PlayingCard.Rarity.RARE, 100, 60, 3);
            case "img33": return new PokemonPlant("Leafeon", "Grass Knot", PlayingCard.Rarity.RARE, 110, 50, 2);
            case "img34": return new PokemonPlant("Meganium", "Green Force", PlayingCard.Rarity.RARE, 150, 50, 3);
            case "img35": return new PokemonPlant("Venusaur", "Solar Beam", PlayingCard.Rarity.RARE, 180, 130, 4);
            case "img36": return new EnergyCard();
            default:      throw new IllegalArgumentException("Invalid image Id");
        }
    }

    /**
     * Adds a property change listener to this card.
     *
     * @param listener The property change listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener from this card.
     *
     * @param listener The property change listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
