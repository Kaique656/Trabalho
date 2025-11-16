package repository;

import model.*;
import java.io.*;
import java.util.*;

/**
 * Classe responsável pela persistência de mídias em arquivo CSV.
 * <p>
 * Os dados são gravados e lidos a partir do arquivo <b>./dados/midias.csv</b>.
 * A classe oferece métodos estáticos para salvar e carregar listas de
 * {@link Midia}, utilizando seus métodos auxiliares {@code paraCSV()} e
 * {@code fromCSV()} para conversão.
 * </p>
 */
public class PersistenciaCSV {

    /** Nome da pasta onde o arquivo CSV será armazenado. */
    private static final String PASTA_DADOS = "dados";

    /** Caminho completo do arquivo CSV contendo os registros das mídias. */
    private static final String ARQUIVO = PASTA_DADOS + File.separator + "midias.csv";

    /**
     * Salva a lista de mídias em um arquivo CSV.
     * <p>
     * Caso a pasta <b>./dados</b> não exista, ela será criada automaticamente.
     * Cada mídia é convertida para texto usando o método {@link Midia#paraCSV()}.
     * </p>
     *
     * @param lista Lista contendo objetos {@link Midia} que serão gravados.
     */
    public static void salvar(List<Midia> lista) {
        File pasta = new File(PASTA_DADOS);
        if (!pasta.exists()) pasta.mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Midia m : lista)
                pw.println(m.paraCSV());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega as mídias armazenadas no arquivo CSV.
     * <p>
     * Caso o arquivo <b>midias.csv</b> não exista, uma lista vazia será retornada.
     * Cada linha do arquivo é convertida em um objeto {@link Midia} utilizando
     * o método {@link Midia#fromCSV(String)}.
     * </p>
     *
     * @return Lista de {@link Midia} carregada do arquivo ou lista vazia caso o arquivo não exista.
     */
    public static List<Midia> carregar() {
        List<Midia> lista = new ArrayList<>();
        File f = new File(ARQUIVO);

        if (!f.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Midia m = Midia.fromCSV(linha);
                if (m != null) lista.add(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
