package view;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.*;
import repository.*;

public class Main {
	
	
	private Controladora a  = new Controladora();
	
	public Main() throws IOException {
		
		boolean continuar =true;
		
		while (continuar) {
		
		try {
			String midiaEscolha  = JOptionPane.showInputDialog(" Digite sua escolha");

			switch (midiaEscolha) {
			case "Livro" : 
				addLivro();
				break;
				
			case "Filme":
				addFilme();
				break;
				
			case "Musica":
				addMusica();
			break;
			case "remover":
				
				String remover = JOptionPane.showInputDialog("Qual arquivo deseja excluir ");
				break;
				
			case "listar": 
				listar();
			break;
			
			case "Listar por genero": 
				listarPorGenero();
				break;
				
			default:
				throw new IllegalArgumentException("Unexpected value: " + midiaEscolha);
			}
			
			
			
		} catch (IllegalArgumentException e ) {
			
		}
	}	
}
	
	public void addLivro () throws IOException { 
		
		
		try {
		
		String nome = JOptionPane.showInputDialog("Digite o nome do livro");
		String categoria  = JOptionPane.showInputDialog("Digite seu genero");
		double duracao = Double.parseDouble(JOptionPane.showInputDialog("Digite quantas paginas eles tem"));
		String autor = JOptionPane.showInputDialog("Digite seu autor");
		int tamanho = Integer.parseInt(JOptionPane.showInputDialog("Digite seu tamanho"));
		String arquivo  = JOptionPane.showInputDialog("Digite o arquivo (PDF ou EPUB)"); 
		
		Livro livro =  new Livro (nome,  categoria, duracao, autor , arquivo,tamanho);
		
		a.addMidia(livro);
		livro.salvarMidia();
		
		} catch (IOException e) {
			
		}
		
	}
	
	public void addFilme () throws IOException {
		
		try { 
		
			String nome = JOptionPane.showInputDialog("Digite o nome do filme  ");
			String categoria  = JOptionPane.showInputDialog("Digite seu genero");
			double duracao = Double.parseDouble(JOptionPane.showInputDialog("Digite quantos segundos tem"));
			String idioma = JOptionPane.showInputDialog("Digite seu artista");
			String arquivo  = JOptionPane.showInputDialog("Digite o arquivo (MP3)");
			int tamanho = Integer.parseInt(JOptionPane.showInputDialog("Digite seu tamanho"));

			Filme filme = new Filme(nome, categoria, duracao, idioma, arquivo, tamanho);
			a.addMidia(filme);
			filme.salvarMidia();
		} catch (IOException e ) { 
			
		}
	}
	
	public void addMusica () throws IOException  {
		
		try { 	
		String  nome = JOptionPane.showInputDialog("Digite o nome da musica ");
		String  categoria  = JOptionPane.showInputDialog("Digite seu genero");
		double  duracao = Double.parseDouble(JOptionPane.showInputDialog("Digite quantos segundos tem"));
		  String artista = JOptionPane.showInputDialog("Digite seu artista");
		String  arquivo  = JOptionPane.showInputDialog("Digite o arquivo (MP3)");
		int tamanho = Integer.parseInt(JOptionPane.showInputDialog("Digite seu tamanho"));

		 
		 Musica musica = new Musica(nome, categoria, duracao, artista,arquivo,tamanho);
		a.addMidia(musica);
		musica.salvarMidia();
		
		} catch (IOException e ) {
			
	}	
}
	
	public void listar () throws IOException { 
		try {
		String arq = JOptionPane.showInputDialog("Digite o arquivo que voce deseja acessar ");
		a.listarMidia(arq);
		} catch (IOException e ) { 
			
		}
	}
	
	public void listarPorGenero () throws FileNotFoundException {
		try  {
	
		String arq = JOptionPane.showInputDialog("Digite o arquivo que voce deseja acessar");
		String genero = JOptionPane.showInputDialog("Digite o genero que voce deseja listar");
		
		
		a.listarPorGenero(arq, genero);
		
		} catch (IOException e ) {
			
		}
	}
	public static void main(String[]args) throws IOException {
		new Main();
	}
}
