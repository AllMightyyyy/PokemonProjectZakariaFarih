package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.io.FileReader;
import java.io.IOException;

/**
 * The CapacitiesHelper class provides methods for managing the capacities of Pokemon.
 * It loads capacities from a CSV file and creates a deck of capacities.
 */
public class CapacitiesHelper {

    private static CapacityDeck capacityDeck;

    /**
     * Retrieves the deck of capacities.
     *
     * @return The deck of capacities.
     * @throws IOException if an I/O error occurs while reading the data file.
     */
    public static CapacityDeck getCapacityDeck() throws IOException {
        if (capacityDeck == null)
            capacityDeck = generate();
        return capacityDeck;
    }


    private static CapacityDeck generate() throws IOException{
        if(capacityDeck!=null) {//If the deck his already set we return him
            return capacityDeck;
        }
        capacityDeck = new CapacityDeck();

        FileReader fReader = new FileReader("CSV/moves.csv");
        int i;
        String line;
        StringBuilder lineBuilder= new StringBuilder();
        while ((i=fReader.read())!=-1) {
            char c = (char)i;
            if(c == '\n') {
                line=lineBuilder.toString();

                String[] tab =line.split("[,;]");
                Capacity cap;
                EnumPokemonType type;
                try {
                    type= EnumPokemonType.fromString(tab[2]);

                    cap=Capacity.instance(tab[1], !tab[3].isEmpty() ?Integer.parseInt(tab[3]):0, !tab[4].isEmpty() ?Integer.parseInt(tab[4]):0, !tab[5].isEmpty() ?Integer.parseInt(tab[5]):0, CategoryCapacity.fromString(tab[6]),type);
                    if(capacityDeck.containsKey(type)) {
                        capacityDeck.add(type,cap);
                    }else {
                        capacityDeck.put(type, cap);
                    }
                } catch (Exception ignored) {

                }

                lineBuilder=new StringBuilder();

            }
            else {
                lineBuilder.append(c);
            }
        }
        System.out.println("Cpacity Helpier"+capacityDeck);
        return capacityDeck;
    }

}
