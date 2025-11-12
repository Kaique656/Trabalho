package model;

public class Filme extends Pasta {
	
	private String idioma;

	public Filme(String titulo, String categoria, double duracao, String idioma,String nomeArquivo) {
		super(titulo, categoria, duracao,nomeArquivo);
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
