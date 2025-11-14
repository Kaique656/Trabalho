package model;

import java.io.IOException;

public class Musica extends Midia {
    private String artista;

    public Musica(String caminho, long tamanho, String titulo, int duracao, String categoria, String artista) {
        super(caminho, tamanho, titulo, duracao, categoria);	
		setArtista(artista);
	}

	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	@Override public TipoMidia getTipo() { return TipoMidia.MUSICA; }

    @Override
    public String atributosEspecificos() {
        return "Título: " + titulo +
               "\nArtista: " + artista +
               "\nDuração (segundos): " + duracao +
               "\nCategoria: " + categoria +
               "\nCaminho: " + caminho;
    }

    @Override public String dadoExtraCSV() { return artista == null ? "" : artista; }

}
