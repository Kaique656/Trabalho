package model;

/**
 * Enumeração que representa os tipos de mídia suportados pelo sistema.
 * <p>
 * Os tipos são utilizados para identificar a classe específica
 * (Filme, Música ou Livro) e também são gravados no arquivo CSV.
 * </p>
 */
public enum TipoMidia {

    /**
     * Representa um arquivo de vídeo (MP4 ou MKV).
     */
    FILME,

    /**
     * Representa um arquivo de áudio no formato MP3.
     */
    MUSICA,

    /**
     * Representa um arquivo de livro digital (PDF ou EPUB).
     */
    LIVRO
}
