package model_test;

import static org.junit.Assert.*;
import org.junit.Test;
import model.Musica;
import model.TipoMidia;

public class MusicaTest {

    @Test
    public void testGetTipo() {
        Musica m = new Musica("C:/musica.mp3", 1234, "Minha Música",
                210, "Rock", "Metallica");

        assertEquals(TipoMidia.MUSICA, m.getTipo());
    }

    @Test
    public void testAtributosEspecificos() {
        Musica m = new Musica("C:/musica.mp3", 1234, "Fade to Black",
                420, "Metal", "Metallica");

        String texto = m.atributosEspecificos();

        assertTrue(texto.contains("Fade to Black"));
        assertTrue(texto.contains("Metallica"));
        assertTrue(texto.contains("420"));
        assertTrue(texto.contains("Metal"));
        assertTrue(texto.contains("C:/musica.mp3"));
    }

    @Test
    public void testDadoExtraCSV() {
        Musica m = new Musica("C:/musica.mp3", 1234, "Título",
                100, "Pop", "Anitta");

        assertEquals("Anitta", m.dadoExtraCSV());
    }

    @Test
    public void testDadoExtraCSVComArtistaNulo() {
        Musica m = new Musica("C:/musica.mp3", 1234, "Título",
                100, "Pop", null);

        assertEquals("", m.dadoExtraCSV());
    }
}
