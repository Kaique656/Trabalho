package model;

/**
 * Classe abstrata que representa uma mídia genérica.
 * <p>
 * Todas as mídias possuem atributos comuns como caminho do arquivo,
 * tamanho, título, duração e categoria. As subclasses ({@link Filme},
 * {@link Musica}, {@link Livro}) definem seus atributos específicos
 * e implementações concretas dos métodos abstratos.
 * </p>
 */
public abstract class Midia {

    /** Caminho completo do arquivo no sistema. */
    protected String caminho;

    /** Tamanho do arquivo em bytes. */
    protected long tamanho;

    /** Título da mídia. */
    protected String titulo;

    /** Duração (minutos, segundos ou páginas, dependendo do tipo). */
    protected int duracao;

    /** Categoria da mídia (gênero, tema, etc.). */
    protected String categoria;

    /**
     * Construtor base para qualquer mídia.
     *
     * @param caminho   Caminho do arquivo.
     * @param tamanho   Tamanho em bytes.
     * @param titulo    Nome ou título da mídia.
     * @param duracao   Duração numérica (minutos, segundos ou páginas).
     * @param categoria Categoria (gênero ou tema).
     */
    public Midia(String caminho, long tamanho, String titulo, int duracao, String categoria) {
        setCaminho(caminho);
        setTamanho(tamanho);
        setTitulo(titulo);
        setDuracao(duracao);
        setCategoria(categoria);
    }

    /**
     * Retorna o tipo da mídia.
     *
     * @return Tipo definido no enum {@link TipoMidia}.
     */
    public abstract TipoMidia getTipo();

    /**
     * Retorna uma descrição detalhada dos atributos específicos
     * da mídia (implementação depende da subclasse).
     *
     * @return String com detalhes específicos.
     */
    public abstract String atributosEspecificos();

    /**
     * Retorna o dado extra que será salvo no CSV,
     * específico para cada tipo de mídia.
     *
     * @return Dado extra em formato texto.
     */
    public abstract String dadoExtraCSV();

    /**
     * Converte a mídia para uma linha em formato CSV.
     *
     * @return Linha CSV contendo tipo, atributos comuns e extra.
     */
    public String paraCSV() {
        return getTipo() + ";" + caminho + ";" + tamanho + ";" + titulo + ";" +
               duracao + ";" + categoria + ";" + dadoExtraCSV();
    }

    /**
     * Constrói uma mídia a partir de uma linha CSV.
     * Este método atua como um "factory method" que identifica
     * automaticamente qual tipo de mídia deve ser criado.
     *
     * @param linha Linha CSV com todos os atributos.
     * @return Objeto {@code Midia} correspondente à linha,
     *         ou {@code null} em caso de erro.
     */
    public static Midia fromCSV(String linha) {
        try {
            String[] p = linha.split(";", -1);

            TipoMidia tipo = TipoMidia.valueOf(p[0]);
            String caminho = p[1];
            long tamanho = Long.parseLong(p[2]);
            String titulo = p[3];
            int duracao = Integer.parseInt(p[4]);
            String categoria = p[5];
            String extra = p.length > 6 ? p[6] : "";

            switch (tipo) {
                case FILME: return new Filme(caminho, tamanho, titulo, duracao, categoria, extra);
                case MUSICA: return new Musica(caminho, tamanho, titulo, duracao, categoria, extra);
                case LIVRO: return new Livro(caminho, tamanho, titulo, duracao, categoria, extra);
                default: return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------------------------
    // Getters e Setters
    // --------------------------------------------------------

    /**
     * Retorna o caminho completo do arquivo.
     *
     * @return Caminho absoluto.
     */
    public String getCaminho() { return caminho; }

    /**
     * Define o caminho do arquivo.
     *
     * @param caminho Caminho completo no sistema.
     */
    public void setCaminho(String caminho) { this.caminho = caminho; }

    /**
     * Retorna o título/nome da média.
     *
     * @return Título.
     */
    public String getTitulo() { return titulo; }

    /**
     * Define o título da mídia.
     *
     * @param titulo Novo título.
     */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /**
     * Retorna o tamanho do arquivo.
     *
     * @return Tamanho em bytes.
     */
    public long getTamanho() { return tamanho; }

    /**
     * Define o tamanho do arquivo.
     *
     * @param tamanho Tamanho em bytes.
     */
    public void setTamanho(long tamanho) { this.tamanho = tamanho; }

    /**
     * Retorna a duração da mídia.
     * Interpretação depende do tipo:
     * - Filme/Música: minutos ou segundos  
     * - Livro: número de páginas  
     *
     * @return Duração numérica.
     */
    public int getDuracao() { return duracao; }

    /**
     * Define a duração da mídia.
     *
     * @param duracao Valor numérico.
     */
    public void setDuracao(int duracao) { this.duracao = duracao; }

    /**
     * Retorna a categoria/tema da mídia.
     *
     * @return Categoria.
     */
    public String getCategoria() { return categoria; }

    /**
     * Define a categoria/tema da mídia.
     *
     * @param categoria Texto da categoria.
     */
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
