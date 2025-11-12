package view;

import java.io.IOException;

import javax.swing.JOptionPane;

import Armazenar.Armazenar;

import midias.*;
import Armazenar.*;

public class Main {
	
	
	private Armazenar a  = new Armazenar();
	
	public Main() throws IOException {
		
		boolean continuar =true;
		
		while (continuar) {
			
			
		try {
			String midiaEscolha  = JOptionPane.showInputDialog(" Digite sua escolha");
			
			switch (midiaEscolha) {
			case "Livro" : 
				
				String nome = JOptionPane.showInputDialog("Digite o nome do livro");
				String categoria  = JOptionPane.showInputDialog("Digite seu genero");
				double duracao = Double.parseDouble(JOptionPane.showInputDialog("Digite quantas paginas eles tem"));
				String autor = JOptionPane.showInputDialog("Digite seu autor");
				int tamanho = Integer.parseInt(JOptionPane.showInputDialog("Digite seu tamanho"));
				String arquivo  = JOptionPane.showInputDialog("Digite o arquivo (PDF ou EPUB)"); 
				
				Livro livro =  new Livro (nome,  categoria, duracao, autor , arquivo,tamanho);
				
				a.addMidia(livro);
				livro.salvarMidia();
				
				break;
				
			case "Filme":
				
				 nome = JOptionPane.showInputDialog("Digite o nome do filme");
				 categoria  = JOptionPane.showInputDialog("Digite seu genero");
				 duracao = Double.parseDouble(JOptionPane.showInputDialog("Digite quantos minutos ele tem"));
				 String idioma = JOptionPane.showInputDialog("Digite seu idioma");
				 arquivo  = JOptionPane.showInputDialog("Digite o arquivo (MP4 ou MKV)");
				 
				 Filme filme = new Filme(nome, categoria, duracao, idioma, arquivo);
				 
				 a.addMidia(filme);
				 filme.salvarMidia();
				break;
				
			case "Musica":
				
				 nome = JOptionPane.showInputDialog("Digite o nome da musica ");
				 categoria  = JOptionPane.showInputDialog("Digite seu genero");
				 duracao = Double.parseDouble(JOptionPane.showInputDialog("Digite quantos segundos tem"));
				  String artista = JOptionPane.showInputDialog("Digite seu artista");
				 arquivo  = JOptionPane.showInputDialog("Digite o arquivo (MP3)");
				 
				 Musica musica = new Musica(nome, categoria, duracao, artista,arquivo);
				a.addMidia(musica);
				musica.salvarMidia();
				break;
				
				
			case "remover":
				
				String remover = JOptionPane.showInputDialog("Qual arquivo deseja excluir ");
				break;
				
			case "listar": 
				String arq = JOptionPane.showInputDialog("Digite o arquivo que voce deseja acessar ");
				a.listarMidia(arq);
			break;
			
			case "Listar por genero": 
				arq = JOptionPane.showInputDialog("Digite o arquivo que voce deseja acessar");
				String genero = JOptionPane.showInputDialog("Digite o genero que voce deseja listar");
				
				a.listarPorGenero(arq, genero);
				break;
				
			default:
				throw new IllegalArgumentException("Unexpected value: " + midiaEscolha);
			}
			
			
			
		} catch (IllegalArgumentException e ) {
			
		}
		
		  
		
	}
		
}
	
	public static void main(String[]args) throws IOException {
		new Main();
	}
}
