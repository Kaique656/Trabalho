package model_test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.TipoMidia;

class TipoMidiaTest {

    @Test
    void testQuantidadeDeValores() {
        assertEquals(3, TipoMidia.values().length, 
            "O enum TipoMidia deve conter exatamente 3 valores.");
    }

    @Test
    void testValoresExistentes() {
        assertNotNull(TipoMidia.valueOf("FILME"));
        assertNotNull(TipoMidia.valueOf("MUSICA"));
        assertNotNull(TipoMidia.valueOf("LIVRO"));
    }

    @Test
    void testValueOfCorreto() {
        assertEquals(TipoMidia.FILME, TipoMidia.valueOf("FILME"));
        assertEquals(TipoMidia.MUSICA, TipoMidia.valueOf("MUSICA"));
        assertEquals(TipoMidia.LIVRO, TipoMidia.valueOf("LIVRO"));
    }

    @Test
    void testValueOfInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            TipoMidia.valueOf("VIDEO"); // valor inexistente
        });
    }
}
