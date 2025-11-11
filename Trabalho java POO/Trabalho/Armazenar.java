package Armazenar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
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
	
	public void addArquivo() {

		try {
			if (!p.getDados().exists()) {
				p.getDados().mkdir();
				System.out.println("O arquivo foi criado");
			}

			if (!p.getArquivo().exists()) {
				p.getArquivo().createNewFile();
				System.out.println("O arquivo foi criado");
			}

			PrintWriter pw = new PrintWriter(new FileWriter(p.getArquivo(), true));
			pw.print(p.mostrar());
			pw.close();
		} catch (IOException e) {

		}

	}
	
/*	public void removerMidia (String excluir)throws IOException{
		File[] armaz = p.getArquivo().listFiles();
		
		for (File file : armaz) {
			
			if (excluir.equalsIgnoreCase(file.getName())) {
				
				List<String> nome = new ArrayList<String>();
				
				try (BufferedReader br = new BufferedReader(new FileReader(p.getDados()))){ 
					
					String linha = "";
					boolean tadentro = false;
					
					for (String l : nome) {
						
						if (l.startsWith(excluir)) {
							tadentro = true;
						} else {
							tadentro =false;
						}
						
						
						if (!tadentro) { 
							nome.add(linha);
						}
					}
				}
			}
			
		}
	}
	
*/
	
	
	public void moverMidia (String escolhaDaMidia, String ondeVaiSerColocadaPasta, String arquivoOndeVaiSerColocada)throws IOException{
		
			File arquivo = new File(escolhaDaMidia);
			
			
			File destino = new File(ondeVaiSerColocadaPasta);
			if (destino.exists()) {
				destino.mkdir();
			}
			
			File novoArquivo = new File(ondeVaiSerColocadaPasta,arquivoOndeVaiSerColocada);
			
			   List<String> linhasOriginais = new ArrayList<>();
		       List<String> midiaParaMover = new ArrayList<>();
		       boolean dentroDaMidia = false;
			
		       
		       try (BufferedReader br  = new BufferedReader( new FileReader(novoArquivo))) {
		    	   String linha;
		    	   
		    	   while ((linha = br.readLine()) != null) {
		    		   if (linha.startsWith(escolhaDaMidia)) {
		    			   midiaParaMover.add(linha);
		    			   dentroDaMidia = true;
		    		   } else {
		    			   dentroDaMidia = false;
		    		   }
		    	   }
		    	   
		    	   if (dentroDaMidia) {
		    		   midiaParaMover.add(linha);
		    	   } else {
		    		   linhasOriginais.add(linha);
		    	   }
		    	   
		       } catch (IOException e ) { 
		    	   e.printStackTrace();
		       }
		       
		       
		       try (PrintWriter pr  = new PrintWriter(new FileWriter (arquivoOndeVaiSerColocada) )) { 
		    	   for (String l : midiaParaMover) {
		    		   pr.print(l);
		    	   }
		       } catch (IOException e ) {
		    	   e.printStackTrace();
		       }
		       
		       try (PrintWriter pw =  new PrintWriter(new FileWriter(arquivo))) {
		    	   for (String l: linhasOriginais) {
		    		  pw.print(l);
		    	   }
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
	
	public void listarPorGenero (String arquivo, String genero) { 
		
	}
}

