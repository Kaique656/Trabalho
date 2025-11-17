package repository_test;

import model.*;
import repository.PersistenciaCSV;

import org.junit.jupiter.api.*;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersistenciaCSVTest {

    private static final String PASTA = "dados";
    private static final String ARQUIVO = "dados/midias.csv";

    private List<Midia> listaOriginal;

    @BeforeEach
    void setUp() {
        // Limpa o arquivo antes de cada teste
        File f = new File(ARQUIVO);
        if (f.exists()) f.delete();

        listaOriginal = new ArrayList<>();
        listaOriginal.add(new Filme("c:/f1.mp4", 1500, "Matrix", 120, "Ação", "Português"));
        listaOriginal.add(new Musica("c:/m1.mp3", 500, "Numb", 3, "Rock", "Linkin Park"));
        listaOriginal.add(new Livro("c:/l1.pdf", 3200, "O Hobbit", 310, "Fantasia", "Tolkien"));
    }

    // -----------------------------
    // TESTE: salvar cria pasta e arquivo
    // -----------------------------
    @Test
    void testSalvarCriaPastaEArquivo() {
        PersistenciaCSV.salvar(listaOriginal);

        File pasta = new File(PASTA);
        File arquivo = new File(ARQUIVO);

        assertTrue(pasta.exists(), "A pasta ./dados deveria ser criada");
        assertTrue(arquivo.exists(), "O arquivo midias.csv deveria ter sido criado");
    }

    // -----------------------------
    // TESTE: salvar e carregar retornam dados iguais
    // -----------------------------
    @Test
    void testSalvarECarregar() {
        PersistenciaCSV.salvar(listaOriginal);

        List<Midia> carregada = PersistenciaCSV.carregar();

        assertEquals(listaOriginal.size(), carregada.size(), "Quantidade de itens carregados incorreta");

        for (int i = 0; i < listaOriginal.size(); i++) {
            assertEquals(listaOriginal.get(i).paraCSV(), carregada.get(i).paraCSV(),
                    "Linha CSV não bate após salvar/carregar");
        }
    }

    // -----------------------------
    // TESTE: carregar quando arquivo não existe
    // -----------------------------
    @Test
    void testCarregarArquivoNaoExiste() {
        // garante que arquivo não existe
        File f = new File(ARQUIVO);
        if (f.exists()) f.delete();

        List<Midia> carregada = PersistenciaCSV.carregar();

        assertNotNull(carregada);
        assertTrue(carregada.isEmpty(), "Carregar sem arquivo deve retornar lista vazia");
    }

    // -----------------------------
    // TESTE: salvar com lista vazia
    // -----------------------------
    @Test
    void testSalvarListaVazia() {
        PersistenciaCSV.salvar(new ArrayList<>());

        List<Midia> carregada = PersistenciaCSV.carregar();

        assertTrue(carregada.isEmpty(), "Uma lista vazia deve carregar vazia");
    }

    // -----------------------------
    // TESTE: comportamento com valores especiais (robustez)
    // -----------------------------
    @Test
    void testSalvarECarregarCamposEspeciais() {
        List<Midia> lista = new ArrayList<>();
        lista.add(new Filme("c:/x.mp4", 0, "Título, com vírgula", 10, "Drama", "En_US"));
        lista.add(new Livro("c:/y.pdf", 100, "", 0, "", null));

        PersistenciaCSV.salvar(lista);
        List<Midia> carregada = PersistenciaCSV.carregar();

        assertEquals(2, carregada.size());
    }

    // -----------------------------
    // LIMPEZA FINAL
    // -----------------------------
    @AfterAll
    static void cleanUp() {
        File f = new File(ARQUIVO);
        if (f.exists()) f.delete();
    }
}
