package model;

/**
 * Classe que representa uma mídia do tipo Livro.
 * <p>
 * Esta classe estende {@link Midia} adicionando o atributo específico
 * {@code autores}, que representa o(s) autor(es) do livro.
 * </p>
 */
public class Livro extends Midia {

    /** Nome dos autores do livro. */
    private String autores;

    /**
     * Construtor para criação de um objeto {@code Livro}.
     *
     * @param caminho   Caminho completo do arquivo do livro.
     * @param tamanho   Tamanho do arquivo em bytes.
     * @param titulo    Título do livro.
     * @param duracao   Quantidade de páginas (armazenada como "duração").
     * @param categoria Categoria/tema do livro.
     * @param autores   Nome dos autores do livro.
     */
    public Livro(String caminho, long tamanho, String titulo, int duracao, String categoria, String autores) {
        super(caminho, tamanho, titulo, duracao, categoria);
        setAutores(autores);
    }

    /**
     * Retorna o(s) autor(es) do livro.
     *
     * @return Nome dos autores.
     */
    public String getAutores() {
        return autores;
    }

    /**
     * Define o(s) autor(es) do livro.
     *
     * @param autores Nome dos autores.
     */
    public void setAutores(String autores) {
        this.autores = autores;
    }

    /**
     * Retorna o tipo da mídia, neste caso {@code LIVRO}.
     *
     * @return {@link TipoMidia#LIVRO}
     */
    @Override
    public TipoMidia getTipo() {
        return TipoMidia.LIVRO;
    }

    /**
     * Retorna uma string detalhada contendo atributos específicos do livro.
     *
     * @return Texto formatado com informações específicas do livro.
     */
    @Override
    public String atributosEspecificos() {
        return "Título: " + titulo +
               "\nAutores: " + autores +
               "\nPáginas: " + duracao +
               "\nCategoria: " + categoria +
               "\nCaminho: " + caminho;
    }

    /**
     * Retorna os dados extras para exportação em CSV.
     * <p>
     * Para livros, o dado extra é o nome dos autores.
     * </p>
     *
     * @return Nome dos autores ou string vazia se for {@code null}.
     */
    @Override
    public String dadoExtraCSV() {
        return autores == null ? "" : autores;
    }
}
