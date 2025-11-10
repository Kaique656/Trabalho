package midia;


public class Musica extends Pasta {
	
	private String artista;
 
	public Musica(String titulo, String categoria, double duracao,String artista) {
		super(titulo, categoria, duracao);
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
		
		return null;
	}

}

 