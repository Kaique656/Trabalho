package midia;

public class Filme extends Pasta {
	
	private String idioma;
 
	public Filme(String titulo, String categoria, double duracao, String idioma) {
		super(titulo, categoria, duracao);
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