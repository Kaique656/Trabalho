package repository;

import model.*;
import java.util.*;
import java.util.stream.*;

/**
 * Classe responsável por gerenciar a coleção de mídias cadastradas no sistema.
 * <p>
 * Atua como repositório em memória, oferecendo operações de CRUD e filtros
 * avançados para listagem de mídias. A classe também permite ordenar os
 * resultados por nome, duração ou tamanho em disco.
 * </p>
 */
public class Controladora {

    /**
     * Tipos disponíveis de ordenação.
     */
    public enum TipoOrdenacao {
        NOME,
        DURACAO,
        TAMANHO
    }

    /** Lista interna que armazena todas as mídias cadastradas. */
    private final List<Midia> midias = new ArrayList<>();

    /**
     * Adiciona uma nova mídia ao sistema.
     *
     * @param m Objeto {@link Midia} que será adicionado.
     */
    public void adicionar(Midia m) {
        if (m != null) {
            midias.add(m);
        }
    }

    /**
     * Remove uma mídia já cadastrada.
     *
     * @param m Objeto {@link Midia} a ser removido.
     */
    public void remover(Midia m) {
        midias.remove(m);
    }

    /**
     * Lista todas as mídias cadastradas no sistema.
     *
     * @return Lista completa de mídias.
     */
    public List<Midia> listar() {
        return midias;
    }

    /**
     * Atualiza a mídia localizada no índice especificado.
     *
     * @param indice Índice da mídia no repositório.
     * @param nova   Nova instância de {@link Midia} que substituirá a antiga.
     */
    public void editar(int indice, Midia nova) {
        if (indice >= 0 && indice < midias.size() && nova != null) {
            midias.set(indice, nova);
        }
    }

    /**
     * Filtra e ordena as mídias cadastradas com base nos parâmetros fornecidos.
     *
     * @param tipo         Tipo de mídia (FILME, MUSICA, LIVRO ou "Todos").
     * @param categoria    Categoria desejada (ex.: ação, aventura, rock, ou "Todas").
     * @param ordenacao    Tipo de ordenação desejada (nome, duração ou tamanho).
     *
     * @return Lista de mídias filtradas e ordenadas.
     */
    public List<Midia> filtrar(String tipo, String categoria, TipoOrdenacao ordenacao) {

        Stream<Midia> stream = midias.stream();

        // Filtra por tipo
        if (tipo != null && !tipo.equalsIgnoreCase("Todos")) {
            stream = stream.filter(m -> m.getTipo().name().equalsIgnoreCase(tipo));
        }

        // Filtra por categoria
        if (categoria != null && !categoria.equalsIgnoreCase("Todas")) {
            stream = stream.filter(m -> 
                m.getCategoria() != null &&
                m.getCategoria().equalsIgnoreCase(categoria)
            );
        }

        // Definição dos comparadores
        Comparator<Midia> compNome =
                Comparator.comparing(m -> m.getTitulo().toLowerCase());

        Comparator<Midia> compDuracao =
                Comparator.comparingInt(Midia::getDuracao);

        Comparator<Midia> compTamanho =
                Comparator.comparingLong(Midia::getTamanho);

        // Escolhe o comparador de acordo com o parâmetro
        Comparator<Midia> comparador = compNome;

        if (ordenacao != null) {
            switch (ordenacao) {
                case DURACAO: comparador = compDuracao; break;
                case TAMANHO: comparador = compTamanho; break;
                case NOME:
                default:
                    comparador = compNome;
                    break;
            }
        }

        return stream.sorted(comparador).collect(Collectors.toList());
    }
}
