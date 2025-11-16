package view;

import model.*;
import javax.swing.table.*;
import java.util.*;

/**
 * Modelo de tabela Swing responsável por exibir a lista de mídias.
 */
public class TabelaMidias extends AbstractTableModel {
    private List<Midia> midias = new ArrayList<>();
    private final String[] colunas = {"Tipo", "Título", "Categoria", "Duração", "Caminho"};

    @Override public int getRowCount() { return midias.size(); }
    @Override public int getColumnCount() { return colunas.length; }
    @Override public String getColumnName(int coluna) { return colunas[coluna]; }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Midia m = midias.get(linha);
        switch (coluna) {
            case 0: return m.getTipo();
            case 1: return m.getTitulo();
            case 2: return m.getCategoria();
            case 3: return m.getDuracao();
            case 4: return m.getCaminho();
            default: return "";
        }
    }

    public void setMidias(List<Midia> lista) { this.midias = lista; fireTableDataChanged(); }
    public Midia getMidia(int linha) { return midias.get(linha); }
}
