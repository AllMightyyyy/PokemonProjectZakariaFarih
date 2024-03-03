package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.utility;

import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.entity.Pokemon;
import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.type.TypeData;
import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.type.TypeEffectiveness;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataLoader {

    private static final String IMAGE_BASE_PATH = "src/main/resources/cesur/zakaria/pokemonprojectzakariafarih/images/pokemon/";
    private static final String SHAPE_IMAGE_BASE_PATH = "src/main/resources/cesur/zakaria/pokemonprojectzakariafarih/images/shape/";

    public static Map<String, Pokemon> loadPokemonData(String jsonFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Pokemon> pokemonMap = mapper.readValue(new File(jsonFilePath), new TypeReference<Map<String, Pokemon>>() {});
        TypeData typeData = loadTypeData("src/main/resources/cesur/zakaria/pokemonprojectzakariafarih/teamCenter/utility/jsonData/types.json");

        for (Pokemon pokemon : pokemonMap.values()) {
            // Set the main image path
            String imageName = pokemon.generateImageName();
            String imagePath = imageName;
            pokemon.setImagePath(imagePath);

            // Set the shape image path
            String shapeImageName = generateShapeImageName(pokemon.getShape());
            pokemon.setShapeImagePath(SHAPE_IMAGE_BASE_PATH + shapeImageName);

            pokemon.setTypeImagePaths(pokemon.generateTypeImagePaths());

            List<String> typeImages = new ArrayList<>();

            // Set type effectiveness based on the loaded type data
            for (String type : pokemon.getPokemonType()) {
                TypeEffectiveness effectiveness = typeData.getTypeEffectivenessMap().get(type);
                if (effectiveness != null) {
                    pokemon.getWeaknesses().addAll(effectiveness.getWeak());
                    pokemon.getResistances().addAll(effectiveness.getResists());
                    pokemon.getImmunities().addAll(effectiveness.getImmune());
                }
                typeImages.add(constructTypeImagePath(type));
            }
            pokemon.setTypeImagePaths(typeImages);
        }

        return pokemonMap;
    }

    private static String generateShapeImageName(int shape) {
        return shape + ".png"; // Assumes shape images are named as '1.png', '2.png', etc.
    }

    public static TypeData loadTypeData(String jsonFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFilePath), TypeData.class);
    }

    private static String constructTypeImagePath(String type) {
        return "src/main/resources/cesur/zakaria/pokemonprojectzakariafarih/images/types/" + type + ".png";

    }
}
