package view;

import repository.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

/**
 * Janela principal da aplicação - camada de visão.
 * Interage com ControleMidia e PersistenciaCSV, e grava arquivos .tpoo em ./dados/
 */
public class TelaPrincipal extends JFrame {

    private Controladora controle;
    private JTable tabela;
    private TabelaMidias modeloTabela;

    private JComboBox<String> filtroTipo;
    private JComboBox<String> filtroCategoria;
    private JComboBox<String> filtroOrdenacao;

    public TelaPrincipal() {
        super("Gerenciador de Mídias");
        controle = new Controladora();
        controle.listar().addAll(PersistenciaCSV.carregar());
        configurarInterface();
        atualizarTabela();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    private void configurarInterface() {
        modeloTabela = new TabelaMidias();
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnSalvar = new JButton("Salvar em CSV");
        JButton btnMover = new JButton("Mover arquivo");
        JButton btnRenomear = new JButton("Renomear arquivo");
        JButton btnAtributos = new JButton("Mostrar atributos");

        filtroTipo = new JComboBox<>(new String[]{"Todos","FILME","MUSICA","LIVRO"});
        filtroCategoria = new JComboBox<>(new String[]{"Todas","Ação","Aventura","Rock","Drama","Pop"});
        filtroOrdenacao = new JComboBox<>(new String[]{"Nome", "Duração", "Tamanho"});

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Tipo:")); topo.add(filtroTipo);
        topo.add(new JLabel("Categoria:")); topo.add(filtroCategoria);
        topo.add(new JLabel("Ordenar por:"));
        topo.add(filtroOrdenacao);

        JPanel botoes = new JPanel();
        botoes.add(btnAdicionar); botoes.add(btnEditar); botoes.add(btnRemover); botoes.add(btnMover); botoes.add(btnRenomear); botoes.add(btnAtributos); botoes.add(btnSalvar);

        add(topo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> adicionarMidia());
        btnEditar.addActionListener(e -> editarMidia());
        btnRemover.addActionListener(e -> removerMidia());
        btnSalvar.addActionListener(e -> salvarCSV());
        btnMover.addActionListener(e -> moverArquivo());
        btnRenomear.addActionListener(e -> renomearArquivo());
        btnAtributos.addActionListener(e -> mostrarAtributos());

        filtroTipo.addActionListener(e -> atualizarTabela());
        filtroCategoria.addActionListener(e -> atualizarTabela());
        filtroOrdenacao.addActionListener(e -> atualizarTabela());

    }

    private void atualizarTabela() {
    String tipo = (String) filtroTipo.getSelectedItem();
    String categoria = (String) filtroCategoria.getSelectedItem();

    Controladora.TipoOrdenacao ordenacao = Controladora.TipoOrdenacao.NOME;

    switch (filtroOrdenacao.getSelectedItem().toString()) {
        case "Duração":
            ordenacao = Controladora.TipoOrdenacao.DURACAO;
            break;
        case "Tamanho":
            ordenacao = Controladora.TipoOrdenacao.TAMANHO;
            break;
        case "Nome":
        default:
            ordenacao = Controladora.TipoOrdenacao.NOME;
            break;
    }

    List<Midia> lista = controle.filtrar(tipo, categoria, ordenacao);
    modeloTabela.setMidias(lista);
}


    private void adicionarMidia() {
        DialogoMidia d = new DialogoMidia(this, null);
        Midia nova = d.mostrar();
        if (nova != null) {
            controle.adicionar(nova);
            escreverTpoo(nova);
            atualizarTabela();
        }
    }

    private void editarMidia() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { JOptionPane.showMessageDialog(this, "Selecione uma mídia para editar."); return; }
        Midia selecionada = modeloTabela.getMidia(linha);
        DialogoMidia d = new DialogoMidia(this, selecionada);
        Midia editada = d.mostrar();
        if (editada != null) {
            List<Midia> todas = controle.listar();
            int idx = todas.indexOf(selecionada);
            if (idx >= 0) controle.editar(idx, editada);
            escreverTpoo(editada);
            atualizarTabela();
        }
    }

    private void removerMidia() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { JOptionPane.showMessageDialog(this, "Selecione uma mídia para remover."); return; }
        Midia selecionada = modeloTabela.getMidia(linha);
        int opc = JOptionPane.showConfirmDialog(this, "Remover " + selecionada.getTitulo() + "?");
        if (opc == JOptionPane.YES_OPTION) {
            controle.remover(selecionada);
            File t = new File("dados" + File.separator + gerarNomeTpoo(selecionada));
            if (t.exists()) t.delete();
            atualizarTabela();
        }
    }

    private void moverArquivo() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { JOptionPane.showMessageDialog(this, "Selecione uma mídia para mover."); return; }
        Midia m = modeloTabela.getMidia(linha);
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = chooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File destino = chooser.getSelectedFile();
            File src = new File(m.getCaminho());
            File dst = new File(destino, src.getName());
            try { Files.move(src.toPath(), dst.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                m.setCaminho(dst.getAbsolutePath());
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Arquivo movido.");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao mover: " + ex.getMessage()); }
        }
    }

    private void renomearArquivo() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { JOptionPane.showMessageDialog(this, "Selecione uma mídia para renomear."); return; }
        Midia m = modeloTabela.getMidia(linha);
        String novo = JOptionPane.showInputDialog(this, "Novo nome (apenas nome do arquivo):", new File(m.getCaminho()).getName());
        if (novo != null && !novo.trim().isEmpty()) {
            File src = new File(m.getCaminho());
            File dst = new File(src.getParentFile(), novo);
            if (dst.exists()) { JOptionPane.showMessageDialog(this, "Arquivo já existe."); return; }
            boolean ok = src.renameTo(dst);
            if (ok) {
                m.setCaminho(dst.getAbsolutePath());
                atualizarTabela();
            } else { JOptionPane.showMessageDialog(this, "Falha ao renomear."); }
        }
    }

    private void mostrarAtributos() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { JOptionPane.showMessageDialog(this, "Selecione uma mídia."); return; }
        Midia m = modeloTabela.getMidia(linha);
        JOptionPane.showMessageDialog(this, m.atributosEspecificos(), "Atributos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void salvarCSV() {
        PersistenciaCSV.salvar(controle.listar());
        JOptionPane.showMessageDialog(this, "Dados salvos em ./dados/midias.csv");
    }

    private void escreverTpoo(Midia m) {
        try {
            File pasta = new File("dados"); if (!pasta.exists()) pasta.mkdirs();
            String nome = gerarNomeTpoo(m);
            File f = new File(pasta, nome);
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("tipo=" + m.getTipo());
                pw.println("caminho=" + m.getCaminho());
                pw.println("tamanho=" + m.getTamanho());
                pw.println("titulo=" + m.getTitulo());
                pw.println("duracao=" + m.getDuracao());
                pw.println("categoria=" + m.getCategoria());
                pw.println("extra=" + m.dadoExtraCSV());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private String gerarNomeTpoo(Midia m) {
        String base = new File(m.getCaminho()).getName();
        return base + ".tpoo";
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true)); }
}
