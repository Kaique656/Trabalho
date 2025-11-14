package model;

import java.io.IOException;

public class Livro extends Midia {
    private String autores;

    public Livro(String caminho, long tamanho, String titulo, int duracao, String categoria, String autores) {
        super(caminho, tamanho, titulo, duracao, categoria);
		setAutores(autores);
	}
	
	public String getAutores() {
		return autores;
	}
	public void setAutores(String autores) {
		this.autores = autores;
	}
	
	@Override public TipoMidia getTipo() { return TipoMidia.LIVRO; }

    @Override
    public String atributosEspecificos() {
        return "Título: " + titulo +
               "\nAutores: " + autores +
               "\nPáginas: " + duracao +
               "\nCategoria: " + categoria +
               "\nCaminho: " + caminho;
    }

    @Override public String dadoExtraCSV() { return autores == null ? "" : autores; }
}