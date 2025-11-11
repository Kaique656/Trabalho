package Armazenar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.BufferOverflowException;
import java.nio.file.Files;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import midias.*;

public class Armazenar {
	
	List<Pasta> pasta;
	Pasta p;
	
	public Armazenar () {
		pasta = new ArrayList<Pasta>();
	}
	
	public void addMidia (Pasta pasta) { 
		this.pasta.add(pasta);
	}
	
	public void removerMidia (String midia) {
		
		for (Pasta pa : pasta) {
			
			if (pa.getTitulo().equalsIgnoreCase(midia)) {
				pasta.remove(midia);
			}
			
		}
		
		// facam o remover, por que nao consegui :(
	}
	
	
	public void moverMidia(String origem, String destino) {
	    File arquivoOrigem = new File(origem);
	    File arquivoDestino = new File(destino);
	    
	    if (!arquivoOrigem.exists()) {
	        JOptionPane.showMessageDialog(null, "Arquivo de origem não encontrado.");
	        return;
	    }

	    boolean sucesso = arquivoOrigem.renameTo(arquivoDestino);
	    if (sucesso) {
	        JOptionPane.showMessageDialog(null, "Arquivo movido com sucesso!");
	    } else {
	        JOptionPane.showMessageDialog(null, "Falha ao mover o arquivo.");
	    }
	}
	
	
	public void renomearArquivo (String arquivoVelho, String arquivoNovo) {
		
		File arquivoV = new File(arquivoVelho);
		File arquivoN = new File(arquivoNovo);
	
		if (arquivoV.renameTo(arquivoN)) {
			JOptionPane.showMessageDialog(null, "Nome do arquivo mudado com sucesso");
		} else {
			JOptionPane.showMessageDialog(null, "Falha ao tentar achar o arquivo");
		}
	}
	
	public void listarMidia (String arquivo) throws IOException {
		File aquivo = new File(arquivo); 
		
		try (Scanner keyboard = new Scanner(aquivo)) {
			
			while (keyboard.hasNextLine()) {
				String linha = keyboard.nextLine();
				JOptionPane.showMessageDialog(null, linha);
			}
			
		} catch (IOException e ) {
			JOptionPane.showMessageDialog(null, "O arquivo nao foi encontrado");
		}
	}
	public StringBuilder listarPorGenero (String arquivo, String genero) throws FileNotFoundException { 
		
		StringBuilder armazenar = new StringBuilder();
		File ark = new File(arquivo);
		
		for (Pasta pa : pasta) {
			
			 try {
				 
				 Scanner keyboard = new Scanner(ark);
				 
				 if (pa.getCategoria().equalsIgnoreCase(genero)) {
				 			while (keyboard.hasNextLine()) {
					armazenar.append(keyboard.nextLine()).append("\n");
				 }
				
		 }
	
			 } catch (IllegalArgumentException e ) {
				 
		}
			
}
		return armazenar;
	}
	
	
	public void ordemAlfabetica (String arquivo) throws IOException {
		ArrayList <String >armaz = new ArrayList<String>();
		File ark = new File(arquivo);
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(ark));
			String linha;
			
			while ((linha = br.readLine()) != null) {
				
				armaz.add(linha);
			}
			
			Collections.sort(armaz);
			
			PrintWriter pw = new PrintWriter (new FileWriter(arquivo));
			
			for (String s : armaz) {
				
				pw.println(s);
				
			}
		} catch (IOException e ) {
			
	}
		
	
		
		
	}
}

