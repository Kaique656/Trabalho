package midia;

import java.io.File;

public abstract class Pasta {
		 
		private String titulo, categoria;
		private double duracao,tamanho;
		File dados = new File("dados");
		File arquivo = new File(dados, "texto.tpoo");
	 
		public Pasta(String titulo, String categoria, double duracao) {
			setTitulo(titulo);
			setCategoria(categoria);
			setDuracao(duracao);
			setTamanho(arquivo.length());
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
	 
		public abstract String mostrar();
	 
	}


