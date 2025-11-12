package midias;

import java.io.IOException;

public class Livro extends Pasta {

	private String autores;
	
	public Livro(String titulo, String categoria, double duracao,String autores,String nomeArquivo, int tamanho ) throws IOException {
		super(titulo, categoria, tamanho ,duracao,nomeArquivo);
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
	    return "Título: " + getTitulo() + ", Categoria: " + getCategoria() +
	           ", Páginas: " + getDuracao() + ", Autor: " + getAutores();
	}


	

}
