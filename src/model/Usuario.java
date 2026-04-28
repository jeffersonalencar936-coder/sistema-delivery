package model;

public abstract class Usuario {
    protected int id;
    protected String nome;
    protected String telefone;

    public Usuario(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getInformacoes() {
        return "ID: " + id + ", Nome: " + nome + ", Telefone: " + telefone;
    }

    public abstract void exibirBoasVindas();

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
}