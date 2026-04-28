package model;

public class Cliente extends Usuario {

    private String endereco;

    public Cliente(int id, String nome, String telefone, String endereco) {
        super(id, nome, telefone);
        this.endereco = endereco;
    }

    public String getEndereco() { return endereco; }

    @Override
    public void exibirBoasVindas() {
        System.out.println("Bem-vindo cliente: " + nome);
    }
}