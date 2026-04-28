package model;

public class Restaurante {

    private int id;
    private String nome;
    private String endereco;
    private double avaliacao;

    public Restaurante(int id, String nome, String endereco, double avaliacao) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.avaliacao = avaliacao;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public double getAvaliacao() { return avaliacao; }
}