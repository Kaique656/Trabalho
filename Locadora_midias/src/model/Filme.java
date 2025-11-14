package model;

import java.io.IOException;

public class Filme extends Midia {
	
	private String idioma;

	public Filme(String caminho, long tamanho, String titulo, int duracao, String categoria, String idiomaAudio) {
        super(caminho, tamanho, titulo, duracao, categoria);
		setIdioma(idioma);
	}
	
	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	@Override public TipoMidia getTipo() { return TipoMidia.FILME; }
	
	@Override
    public String atributosEspecificos() {
        return "Título: " + titulo +
               "\nIdioma do áudio: " + idioma +
               "\nDuração (minutos): " + duracao +
               "\nCategoria: " + categoria +
               "\nCaminho: " + caminho;
    }

    @Override public String dadoExtraCSV() { return idioma == null ? "" : idioma; }

}
