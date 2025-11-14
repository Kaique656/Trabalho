package repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import model.*;

public class Controladora {
	
	List<Midia> pasta;
	Midia p;
	
	public Controladora () {
		pasta = new ArrayList<Midia>();
	}
	
	public void addMidia (Midia pasta) { 
		this.pasta.add(pasta);
	}
	
	public void removerMidia (String midia) {
		
		for (Midia pa : pasta) {
			
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
		 File arq = new File("dados", arquivo + ".tpoo");

		    if (!arq.exists()) {
		        JOptionPane.showMessageDialog(null, "Arquivo não encontrado: " + arq.getAbsolutePath());
		        return;
		    }

		    try (Scanner keyboard = new Scanner(arq)) {
		        while (keyboard.hasNextLine()) {
		            String linha = keyboard.nextLine();
		            
		            JOptionPane.showMessageDialog(null, linha);
		        }
		    }
		}

	public void  listarPorGenero (String arquivo, String genero) throws IOException { 
		
		File arq = new File("dados",arquivo+".tpoo");
		
		try (BufferedReader br =  new BufferedReader(new FileReader(arq))) {
			String linha;
			
			StringBuilder sb = new StringBuilder("GENERO SELECIONADO : " + genero );
			
			while ((linha = br.readLine())!= null) { 
				if (br.readLine().equals(null)) { 
					break;
				}
				
				if (linha.equalsIgnoreCase(genero)) {
					sb.append(linha).append("\n ");
				}
			}
			
			JOptionPane.showMessageDialog(null, sb.toString());
		}
		
		
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
		} catch	(IOException e ) {
			
		}
			
			Collections.sort(armaz);
			
			StringBuilder sb = new StringBuilder("Ordem alfabetica");
			
			for (String s : armaz) {
			    sb.append(s).append("\n");
			}
			JOptionPane.showMessageDialog(null, sb.toString());
			
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(ark));
				
				for (String s : armaz) {
					pw.println(s);
				}
				
			} catch (IOException e ) {
				
			}
		
	
}
	
}