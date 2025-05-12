package model;

public class Livro {

    private String nome;
    private String autor;
    private int numExemplares;
    private int anoPublicacao;
    private String categoria;

    public Livro(String nome, String autor, int anoPublicacao, int numExemplares, String categoria) {
        this.nome = nome;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.numExemplares = numExemplares;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumExemplares() {
        return numExemplares;
    }

    public void setNumExemplares(int numExemplares) {
        this.numExemplares = numExemplares;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return nome;
    }

    public void emprestarLivro() {
        if (numExemplares > 0) {
            numExemplares--;
            System.out.println("Livro emprestado com sucesso!");
        } else {
            System.out.println("Não há exemplares disponíveis para empréstimo.");
        }
    }

    public void devolverLivro() {
        numExemplares++;
        System.out.println("Livro devolvido com sucesso!");
    }

    @Override
    public String toString() {
        return "Livro: " + nome + ", Autor: " + autor + ", Ano: " + anoPublicacao + ", Categoria: " + categoria
                + ", Exemplares: " + numExemplares;
    }
}
