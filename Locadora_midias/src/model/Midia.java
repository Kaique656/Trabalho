package model;

import java.io.IOException;

public abstract class Midia {
    protected String caminho;
    protected long tamanho;
    protected String titulo;
    protected int duracao;
    protected String categoria;

    public Midia(String caminho, long tamanho, String titulo, int duracao, String categoria) {
        setCaminho(caminho);
        setTamanho(tamanho);
        setTitulo(titulo);
        setDuracao(duracao);
        setCategoria(categoria);
    }

    public abstract TipoMidia getTipo();

    public abstract String atributosEspecificos();
    
    public abstract String dadoExtraCSV();

    public String paraCSV() {
        return getTipo() + ";" + caminho + ";" + tamanho + ";" + titulo + ";" +
               duracao + ";" + categoria + ";" + dadoExtraCSV();
    }

    public static Midia fromCSV(String linha) {
        try {
            String[] p = linha.split(";", -1);
            TipoMidia tipo = TipoMidia.valueOf(p[0]);
            String caminho = p[1];
            long tamanho = Long.parseLong(p[2]);
            String titulo = p[3];
            int duracao = Integer.parseInt(p[4]);
            String categoria = p[5];
            String extra = p.length > 6 ? p[6] : "";

            switch (tipo) {
                case FILME: return new Filme(caminho, tamanho, titulo, duracao, categoria, extra);
                case MUSICA: return new Musica(caminho, tamanho, titulo, duracao, categoria, extra);
                case LIVRO: return new Livro(caminho, tamanho, titulo, duracao, categoria, extra);
                default: return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getters e Setters
    public String getCaminho() {
        return caminho; 
    }
    public void setCaminho(String caminho) { 
        this.caminho = caminho; 
    }
    
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getTamanho() {
        return tamanho;
    }
    public void setTamanho(long tamanho) {
        this.tamanho = tamanho;
    }

    public int getDuracao() {
        return duracao;
    }
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}