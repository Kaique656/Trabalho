package model_test;

import static org.junit.Assert.*;
import org.junit.Test;
import model.Midia;
import model.TipoMidia;
import model.Filme;
import model.Musica;
import model.Livro;

public class MidiaTest {

    // Classe auxiliar para testar Midia
    private static class MidiaFake extends Midia {

        public MidiaFake(String caminho, long tamanho, String titulo, int duracao, String categoria) {
            super(caminho, tamanho, titulo, duracao, categoria);
        }

        @Override
        public TipoMidia getTipo() {
            return TipoMidia.MUSICA; // qualquer tipo serve para testar o CSV
        }

        @Override
        public String atributosEspecificos() {
            return "fake";
        }

        @Override
        public String dadoExtraCSV() {
            return "extraFake";
        }
    }

    @Test
    public void testParaCSV() {
        Midia m = new MidiaFake("C:/file.mp3", 999, "Titulo", 123, "Rock");

        String csv = m.paraCSV();

        assertEquals("MUSICA;C:/file.mp3;999;Titulo;123;Rock;extraFake", csv);
    }

    @Test
    public void testFromCSVFilme() {
        Midia m = Midia.fromCSV("FILME;C:/f.mp4;1000;Matrix;120;Ação;Wachowski");

        assertTrue(m instanceof Filme);
        assertEquals("Matrix", m.getTitulo());
        assertEquals("Ação", m.getCategoria());
    }

    @Test
    public void testFromCSVMusica() {
        Midia m = Midia.fromCSV("MUSICA;C:/m.mp3;3000;One;420;Metal;Metallica");

        assertTrue(m instanceof Musica);
        assertEquals("One", m.getTitulo());
    }

    @Test
    public void testFromCSVLivro() {
        Midia m = Midia.fromCSV("LIVRO;C:/l.pdf;500;Duna;600;Ficção;Frank Herbert");

        assertTrue(m instanceof Livro);
        assertEquals(600, m.getDuracao());
    }

    @Test
    public void testFromCSVInvalidoRetornaNull() {
        Midia m = Midia.fromCSV("TIPOINEXISTENTE;abc;10;errado;1;X;extra");

        assertNull(m);
    }

    @Test
    public void testFromCSVComLinhaCorrompida() {
        Midia m = Midia.fromCSV("MUSICA;faltam;campos");

        assertNull(m);
    }
}
