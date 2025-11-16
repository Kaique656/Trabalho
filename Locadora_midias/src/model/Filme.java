package model;

/**
 * Classe que representa uma mídia do tipo Filme.
 * <p>
 * Esta classe estende {@link Midia} adicionando o atributo específico
 * {@code idioma}, que representa o idioma do áudio do filme.
 * </p>
 */
public class Filme extends Midia {

    /** Idioma do áudio do filme. */
    private String idioma;

    /**
     * Construtor para criação de um objeto {@code Filme}.
     *
     * @param caminho      Caminho completo do arquivo do filme.
     * @param tamanho      Tamanho do arquivo em bytes.
     * @param titulo       Título do filme.
     * @param duracao      Duração do filme em minutos.
     * @param categoria    Categoria/genre do filme.
     * @param idiomaAudio  Idioma do áudio do filme.
     */
    public Filme(String caminho, long tamanho, String titulo, int duracao, String categoria, String idiomaAudio) {
        super(caminho, tamanho, titulo, duracao, categoria);
        setIdioma(idiomaAudio); // Corrigido
    }

    /**
     * Retorna o idioma do áudio do filme.
     *
     * @return Idioma do filme.
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Define o idioma do áudio do filme.
     *
     * @param idioma Novo idioma do filme.
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Retorna o tipo da mídia, neste caso {@code FILME}.
     *
     * @return {@link TipoMidia#FILME}
     */
    @Override
    public TipoMidia getTipo() {
        return TipoMidia.FILME;
    }

    /**
     * Retorna uma string detalhada contendo atributos específicos do filme.
     *
     * @return Texto formatado com informações específicas do filme.
     */
    @Override
    public String atributosEspecificos() {
        return "Título: " + titulo +
               "\nIdioma do áudio: " + idioma +
               "\nDuração (minutos): " + duracao +
               "\nCategoria: " + categoria +
               "\nCaminho: " + caminho;
    }

    /**
     * Retorna os dados extras para exportação em CSV.
     * <p>
     * Para filmes, o dado extra é o idioma.
     * </p>
     *
     * @return Idioma do filme ou string vazia se for {@code null}.
     */
    @Override
    public String dadoExtraCSV() {
        return idioma == null ? "" : idioma;
    }
}
