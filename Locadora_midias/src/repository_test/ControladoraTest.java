package repository_test;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import repository.Controladora;
import repository.Controladora.TipoOrdenacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class ControladoraTest {

    private Controladora controladora;
    private Midia filme;
    private Midia musica;
    private Midia livro;

    @BeforeEach
    void setUp() {
        controladora = new Controladora();

        filme = new Filme("c:/filme.mp4", 1500, "Matrix", 120, "Ação", "Wachowski");
        musica = new Musica("c:/musica.mp3", 500, "Numb", 3, "Rock", "Linkin Park");
        livro = new Livro("c:/livro.pdf", 3000, "O Hobbit", 310, "Fantasia", "Tolkien");

        controladora.adicionar(filme);
        controladora.adicionar(musica);
        controladora.adicionar(livro);
    }

  
    @Test
    void testAdicionarEListar() {
        List<Midia> lista = controladora.listar();
        assertEquals(3, lista.size());
        assertTrue(lista.contains(filme));
        assertTrue(lista.contains(musica));
        assertTrue(lista.contains(livro));
    }

    @Test
    void testRemover() {
        controladora.remover(musica);
        assertEquals(2, controladora.listar().size());
        assertFalse(controladora.listar().contains(musica));
    }


    @Test
    void testEditar() {
        Midia novoFilme = new Filme("c:/novo.mp4", 2000, "Novo Filme", 100, "Drama", "Diretor X");

        controladora.editar(0, novoFilme);

        assertEquals("Novo Filme", controladora.listar().get(0).getTitulo());
    }

    @Test
    void testFiltrarPorTipo() {
        List<Midia> filtrado = controladora.filtrar("FILME", "Todas", TipoOrdenacao.NOME);

        assertEquals(1, filtrado.size());
        assertEquals("Matrix", filtrado.get(0).getTitulo());
    }

  
    @Test
    void testFiltrarPorCategoria() {
        List<Midia> filtrado = controladora.filtrar("Todos", "Rock", TipoOrdenacao.NOME);

        assertEquals(1, filtrado.size());
        assertEquals("Numb", filtrado.get(0).getTitulo());
    }

 
    @Test
    void testOrdenacaoNome() {
        List<Midia> ordenado = controladora.filtrar("Todos", "Todas", TipoOrdenacao.NOME);

        assertEquals("Matrix", ordenado.get(0).getTitulo());
        assertEquals("Numb", ordenado.get(1).getTitulo());
        assertEquals("O Hobbit", ordenado.get(2).getTitulo());
    }

    @Test
    void testOrdenacaoDuracao() {
        List<Midia> ordenado = controladora.filtrar("Todos", "Todas", TipoOrdenacao.DURACAO);

        assertEquals("Numb", ordenado.get(0).getTitulo()); // duração 3
        assertEquals("Matrix", ordenado.get(1).getTitulo()); // duração 120
        assertEquals("O Hobbit", ordenado.get(2).getTitulo()); // duração 310
    }

 
    @Test
    void testOrdenacaoTamanho() {
        List<Midia> ordenado = controladora.filtrar("Todos", "Todas", TipoOrdenacao.TAMANHO);

        assertEquals("Numb", ordenado.get(0).getTitulo());     // tamanho 500
        assertEquals("Matrix", ordenado.get(1).getTitulo());   // tamanho 1500
        assertEquals("O Hobbit", ordenado.get(2).getTitulo()); // tamanho 3000
    }


    @Test
    void testFiltrarTodos() {
        List<Midia> filtrado = controladora.filtrar("Todos", "Todas", TipoOrdenacao.NOME);

        assertEquals(3, filtrado.size());
    }


    @Test
    void testParametrosNulos() {
        List<Midia> filtrado = controladora.filtrar(null, null, null);

        // ordena por nome por padrão
        assertEquals(3, filtrado.size());
        assertEquals("Matrix", filtrado.get(0).getTitulo());
    }
}
