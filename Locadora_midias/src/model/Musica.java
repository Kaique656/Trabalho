package model;

/**
 * Representa uma mídia do tipo música.
 * <p>
 * Além dos atributos básicos herdados de {@link Midia}, uma música possui
 * um artista associado, que é salvo tanto na visualização quanto no CSV.
 * </p>
 */
public class Musica extends Midia {

    /** Nome do artista ou banda responsável pela música. */
    private String artista;

    /**
     * Construtor da classe Musica.
     *
     * @param caminho   Caminho completo do arquivo no sistema.
     * @param tamanho   Tamanho do arquivo em bytes.
     * @param titulo    Título da música.
     * @param duracao   Duração da faixa em segundos.
     * @param categoria Categoria da música (gênero).
     * @param artista   Nome do artista ou banda.
     */
    public Musica(String caminho, long tamanho, String titulo, int duracao,
                  String categoria, String artista) {
        super(caminho, tamanho, titulo, duracao, categoria);
        setArtista(artista);
    }

    /**
     * Retorna o nome do artista.
     *
     * @return Nome do artista.
     */
    public String getArtista() {
        return artista;
    }

    /**
     * Define o artista da música.
     *
     * @param artista Nome do artista ou banda.
     */
    public void setArtista(String artista) {
        this.artista = artista;
    }

    /**
     * Retorna o tipo específico desta mídia.
     *
     * @return {@link TipoMidia#MUSICA}.
     */
    @Override
    public TipoMidia getTipo() {
        return TipoMidia.MUSICA;
    }

    /**
     * Retorna uma representação textual dos atributos específicos da música.
     * Usado pela interface gráfica para exibir detalhes.
     *
     * @return Texto contendo título, artista, duração e outros dados.
     */
    @Override
    public String atributosEspecificos() {
        return "Título: " + titulo +
               "\nArtista: " + artista +
               "\nDuração (segundos): " + duracao +
               "\nCategoria: " + categoria +
               "\nCaminho: " + caminho;
    }

    /**
     * Dado extra da música que será salvo no CSV.
     * No caso da música, o dado adicional é seu artista.
     *
     * @return Nome do artista ou string vazia.
     */
    @Override
    public String dadoExtraCSV() {
        return artista == null ? "" : artista;
    }
}
