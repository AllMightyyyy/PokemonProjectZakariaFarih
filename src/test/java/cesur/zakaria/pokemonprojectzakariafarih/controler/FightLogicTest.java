package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Fight;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Trainer;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Capacity;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FightLogicTest {

    private Fight mockFight;
    private Trainer mockTrainer1;
    private Trainer mockTrainer2;
    private Pokemon mockPokemon1;
    private Pokemon mockPokemon2;
    private Capacity mockCapacity;
    private FightLogic fightLogic;

    @BeforeEach
    public void setUp() {
        mockFight = mock(Fight.class);
        mockTrainer1 = mock(Trainer.class);
        mockTrainer2 = mock(Trainer.class);
        mockPokemon1 = mock(Pokemon.class);
        mockPokemon2 = mock(Pokemon.class);
        mockCapacity = mock(Capacity.class);

        when(mockFight.getCurrentTrainer()).thenReturn(mockTrainer1);
        when(mockFight.getNonCurrentTrainer()).thenReturn(mockTrainer2);
        when(mockTrainer1.getPokemon()).thenReturn(mockPokemon1);
        when(mockTrainer2.getPokemon()).thenReturn(mockPokemon2);
        when(mockCapacity.getPowerPoint()).thenReturn(10);

        fightLogic = new FightLogic(mockFight);
    }

    @Test
    public void testExecuteAttack_PokemonKnockedOut() {
        // Arrange
        when(mockCapacity.getPowerPoint()).thenReturn(10);
        when(mockPokemon2.takeDmgCap(mockCapacity)).thenReturn(true); // Defender is knocked out

        // Act
        FightLogic.AttackResult result = fightLogic.executeAttack(mockCapacity);

        // Assert
        verify(mockCapacity, times(1)).subtractPP();
        verify(mockPokemon2, times(1)).takeDmgCap(mockCapacity);
        assertTrue(result.isDefenderKnockedOut());
        assertEquals(mockPokemon1, result.getAttacker());
        assertEquals(mockPokemon2, result.getDefender());
        assertEquals(mockCapacity, result.getCapacityUsed());
    }

    @Test
    public void testExecuteAttack_PokemonNotKnockedOut() {
        // Arrange
        when(mockCapacity.getPowerPoint()).thenReturn(10);
        when(mockPokemon2.takeDmgCap(mockCapacity)).thenReturn(false); // Defender is not knocked out

        // Act
        FightLogic.AttackResult result = fightLogic.executeAttack(mockCapacity);

        // Assert
        verify(mockCapacity, times(1)).subtractPP();
        verify(mockPokemon2, times(1)).takeDmgCap(mockCapacity);
        assertFalse(result.isDefenderKnockedOut());
        assertEquals(mockPokemon1, result.getAttacker());
        assertEquals(mockPokemon2, result.getDefender());
        assertEquals(mockCapacity, result.getCapacityUsed());
    }
}
