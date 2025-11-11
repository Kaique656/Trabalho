package midias;
import java.io.*;

public abstract class Pasta {

	private String titulo, categoria,nomeAarquivo;
	private double duracao,tamanho;
	File dados = new File("dados");
	File arquivo;

	public Pasta(String titulo, String categoria, double duracao,String nomeArquivo) {
		setTitulo(titulo);
		setCategoria(categoria);
		setDuracao(duracao);
		setTamanho(arquivo.length());
		setNomeAarquivo(nomeArquivo);
		
		arquivo = new File (dados,getNomeAarquivo() + ".tpoo"); 
	}
	public String getNomeAarquivo() {
		return nomeAarquivo;
	}
	public void setNomeAarquivo(String nomeAarquivo) {
		this.nomeAarquivo = nomeAarquivo;
	}
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getDuracao() {
		return duracao;
	}

	public void setDuracao(double duracao) {
		this.duracao = duracao;
	}

	public double getTamanho() {
		return tamanho;
	}

	public void setTamanho(double tamanho) {
		this.tamanho = tamanho;
	}

	public File getDados() {
		return dados;
	}

	public void setDados(File dados) {
		this.dados = dados;
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}
	
	public void salvarMidia() {
	    try {
	        if (!dados.exists()) {
	            dados.mkdir(); 
	        }

	        try (PrintWriter out = new PrintWriter(new FileWriter(arquivo))) {
	           out.println("Título: " + getTitulo());
	            out.println("Categoria: " + getCategoria());
	            out.println("Duração: " + getDuracao());
	            out.println("Tamanho: " + getTamanho());
	        }

	        System.out.println("Arquivo salvo em: " + arquivo.getAbsolutePath());

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public abstract String mostrar();
}
