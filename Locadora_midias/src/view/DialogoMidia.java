package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Diálogo para adicionar ou editar uma mídia.
 * Permite selecionar um arquivo do computador, preenchendo automaticamente
 * o caminho, título e tamanho. Os demais campos podem ser ajustados manualmente.
 */
public class DialogoMidia extends JDialog {
    
    private JComboBox<String> campoTipo;

    private JTextField campoCaminho;
    private JTextField campoTitulo; 
    private JTextField campoDuracao;
    private JTextField campoCategoria;
    private JTextField campoExtra;
    private JTextField campoTamanho;

    private Midia midiaResultado;
    
    /**
     * Constrói o diálogo para criação ou edição de uma mídia.
     *
     * @param dono   janela principal que abriu o diálogo
     * @param midia  mídia existente para edição, ou null para nova
     */
    public DialogoMidia(JFrame dono, Midia midia) {
        super(dono, "Informações da Mídia", true);
        configurarInterface();
        if (midia != null) preencherCampos(midia);
        setSize(420, 360);
        setLocationRelativeTo(dono);
    }
    
    /**
     * Configura toda a interface gráfica do diálogo,
     * incluindo campos, botões e seleção automática de arquivo.
     */
    private void configurarInterface() {
        setLayout(new GridLayout(9, 2, 5, 5));
        
        campoTipo = new JComboBox<>(new String[]{"FILME", "MUSICA", "LIVRO"});
        campoCaminho = new JTextField();
        campoTitulo = new JTextField();
        campoDuracao = new JTextField();
        campoCategoria = new JTextField();
        campoExtra = new JTextField();
        campoTamanho = new JTextField();

        add(new JLabel("Tipo:")); 
        add(campoTipo);

        // Botão para escolher arquivo local
        JButton btnEscolherArquivo = new JButton("Escolher arquivo...");
        btnEscolherArquivo.addActionListener(e -> {
            JFileChooser seletor = new JFileChooser();
            seletor.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int resultado = seletor.showOpenDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File arquivo = seletor.getSelectedFile();
                campoCaminho.setText(arquivo.getAbsolutePath());
                campoTitulo.setText(arquivo.getName());
                campoTamanho.setText(String.valueOf(arquivo.length()));

                // Sugere tipo pela extensão
                String nome = arquivo.getName().toLowerCase();
                if (nome.endsWith(".mp3")) campoTipo.setSelectedItem("MUSICA");
                else if (nome.endsWith(".mp4") || nome.endsWith(".mkv")) campoTipo.setSelectedItem("FILME");
                else if (nome.endsWith(".pdf") || nome.endsWith(".epub")) campoTipo.setSelectedItem("LIVRO");
            }
        });

        add(new JLabel("Caminho:"));
        add(btnEscolherArquivo);

        add(new JLabel("Título:"));
        add(campoTitulo);

        add(new JLabel("Duração (min/seg/pág):"));
        add(campoDuracao);

        add(new JLabel("Categoria:"));
        add(campoCategoria);

        add(new JLabel("Tamanho (bytes):"));
        add(campoTamanho);

        add(new JLabel("Artista / Idioma / Autor(es):"));
        add(campoExtra);

        JButton btnOk = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnOk);
        add(btnCancelar);

        btnOk.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> { midiaResultado = null; dispose(); });
    }

    /**
     * Preenche os campos do formulário com base em uma mídia existente.
     *
     * @param m mídia cujos dados devem ser exibidos no diálogo
     */
    private void preencherCampos(Midia m) {
        campoTipo.setSelectedItem(m.getTipo().name());
        campoCaminho.setText(m.getCaminho());
        campoTitulo.setText(m.getTitulo());
        campoDuracao.setText(String.valueOf(m.getDuracao()));
        campoCategoria.setText(m.getCategoria());
        campoTamanho.setText(String.valueOf(m.getTamanho()));

        campoExtra.setText(m.dadoExtraCSV());
    }

    /**
     * Lê os campos do formulário, valida os dados e cria a instância correta
     * de {@link Midia}. Em caso de erro, exibe uma mensagem para o usuário.
     */
    private void salvar() {
        try {
            String tipo = (String) campoTipo.getSelectedItem();
            String caminho = campoCaminho.getText().trim();
            long tamanho = campoTamanho.getText().trim().isEmpty()
                    ? 0L : Long.parseLong(campoTamanho.getText().trim());
            String titulo = campoTitulo.getText().trim();
            int duracao = campoDuracao.getText().trim().isEmpty()
                    ? 0 : Integer.parseInt(campoDuracao.getText().trim());
            String categoria = campoCategoria.getText().trim();
            String extra = campoExtra.getText().trim();

            if (caminho.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Caminho é obrigatório.");
                return;
            }

            if (titulo.isEmpty()) {
                titulo = new File(caminho).getName();
            }

            switch (tipo) {
                case "FILME":
                    midiaResultado = new Filme(caminho, tamanho, titulo, duracao, categoria, extra);
                    break;
                case "MUSICA":
                    midiaResultado = new Musica(caminho, tamanho, titulo, duracao, categoria, extra);
                    break;
                case "LIVRO":
                    midiaResultado = new Livro(caminho, tamanho, titulo, duracao, categoria, extra);
                    break;
            }

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    /**
     * Mostra o diálogo e retorna o objeto {@link Midia} criado ou editado.
     *
     * @return mídia preenchida pelo usuário, ou null se cancelado
     */
    public Midia mostrar() {
        setVisible(true);
        return midiaResultado;
    }
}
