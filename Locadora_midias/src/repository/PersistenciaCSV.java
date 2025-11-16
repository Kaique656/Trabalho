package repository;

import model.*;
import java.io.*;
import java.util.*;

/**
 * Classe responsável por salvar e carregar mídias em arquivo CSV localizado em ./dados/midias.csv
 */
public class PersistenciaCSV {
    private static final String PASTA_DADOS = "dados";
    private static final String ARQUIVO = PASTA_DADOS + File.separator + "midias.csv";

    public static void salvar(List<Midia> lista) {
        File pasta = new File(PASTA_DADOS);
        if (!pasta.exists()) pasta.mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Midia m : lista) pw.println(m.paraCSV());
        } catch (IOException e) { e.printStackTrace(); }
    }

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
        } catch (IOException e) { e.printStackTrace(); }
        return lista;
    }
}
