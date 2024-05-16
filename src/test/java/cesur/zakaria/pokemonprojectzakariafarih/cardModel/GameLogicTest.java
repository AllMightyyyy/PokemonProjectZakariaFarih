package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    private GameLogic gameLogic;
    private GameListener mockListener;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic();
        mockListener = Mockito.mock(GameListener.class);
        gameLogic.addGameListener(mockListener);
    }

    @Test
    void testEndTurn_ValidEndTurn() {
        gameLogic.endTurn();
        Mockito.verify(mockListener, Mockito.never()).notify(Mockito.any());
    }

    @Test
    void testDownloadPlayingCards_InvalidPhase() {
        gameLogic.downloadPlayingCards(1);
        Mockito.verify(mockListener).notify(Mockito.any());
    }

    @Test
    void testPlay_AttackNotAllowedInCurrentPhase() {
        gameLogic.play();
        Mockito.verify(mockListener).notify(Mockito.any());
    }

    @Test
    void testAddEnergy_InvalidPhase() {
        gameLogic.addEnergy(1);
        Mockito.verify(mockListener).notify(Mockito.any());
    }

    @Test
    void testAddEnergy_NoEnergyCardOnTable() {
        gameLogic.addEnergy(1);
        Mockito.verify(mockListener).notify(Mockito.any());
    }

    @Test
    void testGetPokemonsJ1() {
        assertEquals(0, gameLogic.getPokemonsJ1());
    }

    @Test
    void testGetPokemonsJ2() {
        assertEquals(0, gameLogic.getPokemonsJ2());
    }
}
