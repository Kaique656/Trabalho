package repository;

import model.*;
import java.util.*;
import java.util.stream.*;

/**
 * Classe responsável por gerenciar a coleção de mídias cadastradas no sistema.
 */
public class Controladora {
    private List<Midia> midias = new ArrayList<>();

    public void adicionar(Midia m) { 
        midias.add(m); 
    }

    public void remover(Midia m) { 
        midias.remove(m); 
    }

    public List<Midia> listar() { 
        return midias; 
    }

    public void editar(int indice, Midia nova) {
        if (indice >= 0 && indice < midias.size()) 
        midias.set(indice, nova);
    }

    public List<Midia> filtrar(String tipo, String categoria, boolean ordenarPorNome) {
        Stream<Midia> stream = midias.stream();
        if (tipo != null && !tipo.equalsIgnoreCase("Todos")) {
            stream = stream.filter(m -> m.getTipo().name().equalsIgnoreCase(tipo));
        }
        if (categoria != null && !categoria.equalsIgnoreCase("Todas")) {
            stream = stream.filter(m -> m.getCategoria() != null && m.getCategoria().equalsIgnoreCase(categoria));
        }
        Comparator<Midia> comparador = ordenarPorNome
                ? Comparator.comparing(m -> m.getTitulo().toLowerCase())
                : Comparator.comparingInt(Midia::getDuracao);

        return stream.sorted(comparador).collect(Collectors.toList());
    }
}
