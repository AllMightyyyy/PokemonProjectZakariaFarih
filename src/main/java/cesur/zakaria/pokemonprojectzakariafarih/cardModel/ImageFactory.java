package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating images based on image IDs.
 */
public class ImageFactory {
    private static ImageFactory imgf = new ImageFactory();
    private Map<String, Image> images;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private ImageFactory() {
        images = new HashMap<>();
    }

    /**
     * Returns the singleton instance of ImageFactory.
     * @return The singleton instance of ImageFactory.
     */
    public static ImageFactory getInstance() {
        return imgf;
    }

    /**
     * Maps image IDs to their corresponding file paths.
     * @param imgId The ID of the image.
     * @return The file path of the image corresponding to the given ID.
     * @throws IllegalArgumentException if the provided image ID is invalid.
     */
    private String id2File(String imgId) {
        switch (imgId) {
            case "img1":   return ("Agua/Blastoise.png");
            case "img2":   return ("Agua/Croconaw.png");
            case "img3":   return ("Agua/Feraligatr.png");
            case "img4":   return ("Agua/Squirtle.png");
            case "img5":   return ("Agua/Totodile.png");
            case "img6":   return ("Agua/Vaporeon.png");
            case "img7":   return ("Agua/Wartortle.png");
            case "img8":   return ("Fogo/Charizard.png");
            case "img9":   return ("Fogo/Charmander.png");
            case "img10":  return ("Fogo/Charmeleon.png");
            case "img11":  return ("Fogo/Cyndaquil.png");
            case "img12":  return ("Fogo/Flareon.png");
            case "img13":  return ("Fogo/Quilava.png");
            case "img14":  return ("Fogo/Typhlosion.png");
            case "img15":  return ("Normal/Aipom.png");
            case "img16":  return ("Normal/Audino.png");
            case "img17":  return ("Normal/Dodrio.png");
            case "img18":  return ("Normal/Doduo.png");
            case "img19":  return ("Normal/Eevee.png");
            case "img20":  return ("Normal/Fearow.png");
            case "img21":  return ("Normal/Kangaskhan.png");
            case "img22":  return ("Normal/Meowth.png");
            case "img23":  return ("Normal/Miltank.png");
            case "img24":  return ("Normal/Pidgey.png");
            case "img25":  return ("Normal/Raticate.png");
            case "img26":  return ("Normal/Rattatta.png");
            case "img27":  return ("Normal/Sentret.png");
            case "img28":  return ("Normal/Spearow.png");
            case "img29":  return ("Planta/Bayleef.png");
            case "img30":  return ("Planta/Bulbasaur.png");
            case "img31":  return ("Planta/Chikorita.png");
            case "img32":  return ("Planta/Ivysaur.png");
            case "img33":  return ("Planta/Leafeon.png");
            case "img34":  return ("Planta/Meganium.png");
            case "img35":  return ("Planta/Venusaur.png");
            case "img36":  return ("Energia.png");
            case "imgBck": return ("imgBck.png");
            default:       throw new IllegalArgumentException("Invalid image Id: " + imgId);
        }
    }

    /**
     * Creates an ImageView for the specified image ID.
     * @param imgId The ID of the image.
     * @return An ImageView object for the specified image.
     */
    public ImageView createImage(String imgId) {
        Image img = images.get(imgId);
        if (img == null) {
            String resourcePath = id2File(imgId);
            URL resourceUrl = getClass().getResource(resourcePath);
            img = new Image(resourceUrl.toExternalForm(),400,250,true,true);
            images.put(imgId, img);
        }
        ImageView imgv = new ImageView(img);
        return imgv;
    }
}
