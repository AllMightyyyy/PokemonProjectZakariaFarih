package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Pokemon;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.StatistiquePokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PokemonStatUpdaterTest {

    private PokemonStatUpdater statUpdater;
    private Pokemon mockPokemon;
    private StatistiquePokemon mockStat;

    @BeforeEach
    public void setUp() {
        statUpdater = new PokemonStatUpdater();
        mockPokemon = mock(Pokemon.class);
        mockStat = mock(StatistiquePokemon.class);

        when(mockPokemon.getStat()).thenReturn(mockStat);
        when(mockStat.getPv()).thenReturn(100);
        when(mockStat.getDmg()).thenReturn(50);
        when(mockStat.getDef()).thenReturn(70);
    }

    @Test
    public void testUpdateStat_HP() {
        // Act
        statUpdater.updateStat(mockPokemon, "HP", 10);

        // Assert
        verify(mockStat, times(1)).setPv(110);
    }

    @Test
    public void testUpdateStat_Attack() {
        // Act
        statUpdater.updateStat(mockPokemon, "Attack", 10);

        // Assert
        verify(mockStat, times(1)).setDmg(60);
    }

    @Test
    public void testUpdateStat_Defense() {
        // Act
        statUpdater.updateStat(mockPokemon, "Defense", 10);

        // Assert
        verify(mockStat, times(1)).setDef(80);
    }
}
