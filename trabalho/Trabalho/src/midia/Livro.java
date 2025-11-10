package midia;

public class Livro extends Pasta {
 
	private String autores;
	
	public Livro(String titulo, String categoria, double duracao,String autores) {
		super(titulo, categoria, duracao);
		setAutores(autores);
	}
	
	public String getAutores() {
		return autores;
	}
	public void setAutores(String autores) {
		this.autores = autores;
	}
	
	@Override
	public String mostrar() {
		return null;
	}
 
	
 
}
 