package view;

import model.*;
import javax.swing.table.*;
import java.util.*;

/**
 * Modelo de tabela Swing responsável por exibir uma lista de objetos {@link Midia}
 * em uma JTable. Cada linha representa uma mídia e cada coluna exibe um atributo.
 */
public class TabelaMidias extends AbstractTableModel {

    private List<Midia> midias = new ArrayList<>();
    private final String[] colunas = {"Tipo", "Título", "Categoria", "Duração", "Caminho"};
    
    /**
     * Retorna a quantidade de linhas da tabela, equivalente
     * à quantidade de mídias armazenadas.
     *
     * @return número de linhas
     */
    @Override 
    public int getRowCount() { 
        return midias.size(); 
    }

    /**
     * Retorna a quantidade de colunas da tabela.
     *
     * @return número de colunas
     */
    @Override 
    public int getColumnCount() { 
        return colunas.length; 
    }

    /**
     * Retorna o nome da coluna correspondente ao índice informado.
     *
     * @param coluna índice da coluna (0 a n)
     * @return nome da coluna
     */
    @Override 
    public String getColumnName(int coluna) { 
        return colunas[coluna]; 
    }
    
    /**
     * Retorna o valor a ser exibido em uma célula da tabela.
     *
     * @param linha índice da linha (0 a n)
     * @param coluna índice da coluna (0 a n)
     * @return valor da célula correspondente
     */
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

    /**
     * Substitui a lista de mídias exibida pela tabela e notifica
     * que os dados foram alterados, forçando o redesenho.
     *
     * @param lista nova lista de mídias a ser exibida
     */
    public void setMidias(List<Midia> lista) { 
        this.midias = lista; 
        fireTableDataChanged(); 
    }

    /**
     * Retorna o objeto {@link Midia} correspondente à linha informada.
     *
     * @param linha índice da linha
     * @return objeto Midia presente na linha
     */
    public Midia getMidia(int linha) { 
        return midias.get(linha); 
    }
}
