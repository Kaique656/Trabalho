package model_test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Livro;
import model.TipoMidia;

class LivroTest {

    @Test
    void testAtributosEspecificos() {
        Livro l = new Livro("C:/testes/livro.txt", 2000, "Teste", 100, "Drama", "Autor X");

        String txt = l.atributosEspecificos();

        assertTrue(txt.contains("Título: Teste"));
        assertTrue(txt.contains("Autores: Autor X"));
        assertTrue(txt.contains("Páginas: 100"));
        assertTrue(txt.contains("Categoria: Drama"));
        assertTrue(txt.contains("Caminho: C:/testes/livro.txt"));
    }

    @Test
    void testDadoExtraCSV() {
        Livro l = new Livro("a", 1, "b", 10, "c", "Autor 1");
        assertEquals("Autor 1", l.dadoExtraCSV());

        Livro l2 = new Livro("a", 1, "b", 10, "c", null);
        assertEquals("", l2.dadoExtraCSV());
    }
}