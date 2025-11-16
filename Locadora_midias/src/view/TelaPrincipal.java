package view;

import repository.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;

/**
 * Janela principal da aplicação - camada de visão.
 * Interage com {@link Controladora} e {@link repository.PersistenciaCSV},
 * exibe a lista de mídias e permite operações de CRUD, mover, renomear,
 * salvar CSV e geração de arquivos .tpoo na pasta ./dados/.
 */
public class TelaPrincipal extends JFrame {

    private Controladora controle;
    private JTable tabela;
    private TabelaMidias modeloTabela;

    private JComboBox<String> filtroTipo;
    private JComboBox<String> filtroCategoria;
    private JComboBox<String> filtroOrdenacao;

    /**
     * Construtor da janela principal.
     * Carrega mídias persistidas e inicia a interface.
     */
    public TelaPrincipal() {
        super("Gerenciador de Mídias");
        controle = new Controladora();

        // carregar do CSV sem duplicar: limpa lista interna e adiciona carregadas
        List<Midia> carregadas = PersistenciaCSV.carregar();
        controle.listar().clear();
        controle.listar().addAll(carregadas);

        configurarInterface();
        atualizarCategorias(); // popula combo de categorias dinamicamente
        atualizarTabela();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    /**
     * Configura a interface Swing: componentes, botões e listeners.
     */
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

        filtroTipo = new JComboBox<>(new String[]{"Todos", "FILME", "MUSICA", "LIVRO"});
        filtroCategoria = new JComboBox<>(new String[]{"Todas"}); // será populado dinamicamente
        filtroOrdenacao = new JComboBox<>(new String[]{"Nome", "Duração", "Tamanho"});

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Tipo:")); topo.add(filtroTipo);
        topo.add(new JLabel("Categoria:")); topo.add(filtroCategoria);
        topo.add(new JLabel("Ordenar por:")); topo.add(filtroOrdenacao);

        JPanel botoes = new JPanel();
        botoes.add(btnAdicionar); botoes.add(btnEditar); botoes.add(btnRemover);
        botoes.add(btnMover); botoes.add(btnRenomear); botoes.add(btnAtributos); botoes.add(btnSalvar);

        add(topo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        // Listeners dos botões
        btnAdicionar.addActionListener(e -> adicionarMidia());
        btnEditar.addActionListener(e -> editarMidia());
        btnRemover.addActionListener(e -> removerMidia());
        btnSalvar.addActionListener(e -> salvarCSV());
        btnMover.addActionListener(e -> moverArquivo());
        btnRenomear.addActionListener(e -> renomearArquivo());
        btnAtributos.addActionListener(e -> mostrarAtributos());

        // Listeners dos filtros
        filtroTipo.addActionListener(e -> {
            atualizarCategorias(); // atualiza categorias caso tipo mude
            atualizarTabela();
        });
        filtroCategoria.addActionListener(e -> atualizarTabela());
        filtroOrdenacao.addActionListener(e -> atualizarTabela());
    }

    /**
     * Atualiza a tabela exibida conforme filtros e ordenação selecionados.
     * Converte o valor do combo de ordenação para o enum {@link Controladora.TipoOrdenacao}.
     */
    private void atualizarTabela() {
        String tipo = (String) filtroTipo.getSelectedItem();
        String categoria = (String) filtroCategoria.getSelectedItem();
        if (categoria == null) categoria = "Todas";

        Controladora.TipoOrdenacao ordenacao = Controladora.TipoOrdenacao.NOME;
        String sOrden = (String) filtroOrdenacao.getSelectedItem();
        if ("Duração".equalsIgnoreCase(sOrden)) ordenacao = Controladora.TipoOrdenacao.DURACAO;
        else if ("Tamanho".equalsIgnoreCase(sOrden)) ordenacao = Controladora.TipoOrdenacao.TAMANHO;
        else ordenacao = Controladora.TipoOrdenacao.NOME;

        List<Midia> lista = controle.filtrar(tipo, categoria, ordenacao);
        modeloTabela.setMidias(lista);
    }

    /**
     * Adiciona uma nova mídia via diálogo. Após a inclusão, gera o .tpoo e atualiza a UI.
     */
    private void adicionarMidia() {
        DialogoMidia d = new DialogoMidia(this, null);
        Midia nova = d.mostrar();
        if (nova != null) {
            controle.adicionar(nova);
            escreverTpoo(nova);
            atualizarCategorias();
            atualizarTabela();
        }
    }

    /**
     * Edita a mídia selecionada. Garante remoção do .tpoo antigo caso o nome do arquivo mude.
     */
    private void editarMidia() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma mídia para editar.");
            return;
        }

        Midia selecionada = modeloTabela.getMidia(linha);
        String nomeTpooAntigo = gerarNomeTpoo(selecionada);

        DialogoMidia d = new DialogoMidia(this, selecionada);
        Midia editada = d.mostrar();
        if (editada != null) {
            List<Midia> todas = controle.listar();
            int idx = todas.indexOf(selecionada);
            if (idx >= 0) {
                controle.editar(idx, editada);

                // escreve novo .tpoo e remove o antigo se mudou o nome
                escreverTpoo(editada);
                String nomeTpooNovo = gerarNomeTpoo(editada);
                if (!nomeTpooAntigo.equals(nomeTpooNovo)) {
                    File antigo = new File("dados", nomeTpooAntigo);
                    if (antigo.exists()) antigo.delete();
                }

                atualizarCategorias();
                atualizarTabela();
            }
        }
    }

    /**
     * Remove a mídia selecionada e elimina o arquivo .tpoo correspondente (se existir).
     */
    private void removerMidia() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma mídia para remover.");
            return;
        }
        Midia selecionada = modeloTabela.getMidia(linha);
        int opc = JOptionPane.showConfirmDialog(this, "Remover " + selecionada.getTitulo() + "?");
        if (opc == JOptionPane.YES_OPTION) {
            controle.remover(selecionada);
            File t = new File("dados", gerarNomeTpoo(selecionada));
            if (t.exists()) t.delete();
            atualizarCategorias();
            atualizarTabela();
        }
    }

    /**
     * Move o arquivo físico para outra pasta (escolhida pelo usuário).
     * Atualiza o caminho na mídia e reescreve o .tpoo.
     */
    private void moverArquivo() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma mídia para mover.");
            return;
        }
        Midia m = modeloTabela.getMidia(linha);
        File src = new File(m.getCaminho());
        if (!src.exists()) {
            JOptionPane.showMessageDialog(this, "Arquivo de origem não existe: " + m.getCaminho());
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = chooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File destino = chooser.getSelectedFile();
            File dst = new File(destino, src.getName());
            try {
                Files.move(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
                m.setCaminho(dst.getAbsolutePath());
                escreverTpoo(m);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Arquivo movido para: " + dst.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao mover: " + ex.getMessage());
            }
        }
    }

    /**
     * Renomeia o arquivo físico (mantendo pasta). Atualiza caminho e .tpoo.
     */
    private void renomearArquivo() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma mídia para renomear.");
            return;
        }
        Midia m = modeloTabela.getMidia(linha);
        File src = new File(m.getCaminho());
        if (!src.exists()) {
            JOptionPane.showMessageDialog(this, "Arquivo não encontrado: " + m.getCaminho());
            return;
        }

        String sugestao = src.getName();
        String novo = JOptionPane.showInputDialog(this, "Novo nome (apenas nome do arquivo):", sugestao);
        if (novo != null && !novo.trim().isEmpty()) {
            File dst = new File(src.getParentFile(), novo);
            if (dst.exists()) {
                JOptionPane.showMessageDialog(this, "Arquivo já existe: " + dst.getName());
                return;
            }
            try {
                Files.move(src.toPath(), dst.toPath(), StandardCopyOption.ATOMIC_MOVE);
                m.setCaminho(dst.getAbsolutePath());
                escreverTpoo(m);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Arquivo renomeado para: " + dst.getName());
            } catch (Exception ex) {
                // fallback: tentar renameTo se ATOMIC_MOVE não for suportado
                boolean ok = src.renameTo(dst);
                if (ok) {
                    m.setCaminho(dst.getAbsolutePath());
                    escreverTpoo(m);
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Arquivo renomeado (fallback) para: " + dst.getName());
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao renomear: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Mostra os atributos específicos da mídia selecionada em um diálogo.
     */
    private void mostrarAtributos() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma mídia.");
            return;
        }
        Midia m = modeloTabela.getMidia(linha);
        JOptionPane.showMessageDialog(this, m.atributosEspecificos(), "Atributos", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Salva a lista atual de mídias em ./dados/midias.csv
     */
    private void salvarCSV() {
        PersistenciaCSV.salvar(controle.listar());
        JOptionPane.showMessageDialog(this, "Dados salvos em ./dados/midias.csv");
    }

    /**
     * Escreve um arquivo auxiliar .tpoo na pasta ./dados/ com as informações da mídia.
     *
     * @param m Mídia que será escrita em .tpoo
     */
    private void escreverTpoo(Midia m) {
        try {
            File pasta = new File("dados");
            if (!pasta.exists()) pasta.mkdirs();

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
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao escrever .tpoo: " + e.getMessage());
        }
    }

    /**
     * Gera um nome de arquivo .tpoo a partir do nome do arquivo original.
     *
     * @param m Mídia de referência
     * @return String com o nome do .tpoo (ex: \"filme.mp4.tpoo\")
     */
    private String gerarNomeTpoo(Midia m) {
        String base = new File(m.getCaminho()).getName();
        return base + ".tpoo";
    }

    /**
     * Atualiza a combobox de categorias com base nas mídias atualmente cadastradas.
     * Mantém a opção "Todas" em primeiro lugar.
     */
    private void atualizarCategorias() {
        Set<String> categorias = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        categorias.add("Todas"); // sempre primeiro
        String tipoSelecionado = (String) filtroTipo.getSelectedItem();

        for (Midia m : controle.listar()) {
            if (m.getCategoria() == null) continue;
            // se o filtro de tipo está ativo, só coleta categorias daquele tipo
            if ("Todos".equalsIgnoreCase(tipoSelecionado) || m.getTipo().name().equalsIgnoreCase(tipoSelecionado)) {
                categorias.add(m.getCategoria());
            }
        }

        String selecionada = (String) filtroCategoria.getSelectedItem();
        filtroCategoria.setModel(new DefaultComboBoxModel<>(categorias.toArray(new String[0])));

        // restaura seleção anterior se possível
        if (selecionada != null) {
            filtroCategoria.setSelectedItem(selecionada);
        } else {
            filtroCategoria.setSelectedIndex(0);
        }
    }

    /**
     * Ponto de entrada da aplicação (execução).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
