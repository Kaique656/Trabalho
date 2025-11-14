package model;

import java.io.IOException;

public class Filme extends Midia {
	
	private String idioma;

	public Filme(String titulo, String categoria, double duracao, String idioma,String nomeArquivo, int tamanho) throws IOException {
		super(titulo, categoria,tamanho ,duracao,nomeArquivo);
		setIdioma(idioma);
	}
	
	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	@Override
	public String mostrar() {
		return "Titulo: " + getTitulo() + " Categoria: " + getCategoria() + "duracao: " + getDuracao() + "Minutos " + "idioma: " + getIdioma();
	}

}
