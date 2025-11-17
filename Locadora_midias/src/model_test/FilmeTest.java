package model_test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import model.Filme;
import model.TipoMidia;

class FilmeTest {

    @Test
    void testAtributosEspecificos() {
        Filme f = new Filme("C:/filme.avi", 1000, "Avatar", 160, "Fantasia", "Inglês");

        String atributos = f.atributosEspecificos();

        assertTrue(atributos.contains("Avatar"));
        assertTrue(atributos.contains("Inglês"));
        assertTrue(atributos.contains("160"));
        assertTrue(atributos.contains("Fantasia"));
        assertTrue(atributos.contains("C:/filme.avi"));
    }

    @Test
    void testDadoExtraCSV() {
        Filme f = new Filme("c", 10, "t", 1, "cat", "Italiano");
        assertEquals("Italiano", f.dadoExtraCSV());

        f.setIdioma(null);
        assertEquals("", f.dadoExtraCSV());
    }
}