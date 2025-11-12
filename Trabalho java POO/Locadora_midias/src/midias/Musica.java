package midias;

public class Musica extends Pasta {
	
	private String artista;

	public Musica(String titulo, String categoria, double duracao,String artista,String nomeArquivo) {
		super(titulo, categoria, duracao,nomeArquivo);
		setArtista(artista);
	}

	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	@Override
	public String mostrar() {
	    return "Título: " + getTitulo() + ", Categoria: " + getCategoria() +
	           ", Duração: " + getDuracao() + "s, Artista: " + getArtista();
	}

}
