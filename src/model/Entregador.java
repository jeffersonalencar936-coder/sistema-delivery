package model;

public class Entregador extends Usuario {

    private String status;
    private String localizacao;

    public Entregador(int id, String nome, String telefone, String status, String localizacao) {
        super(id, nome, telefone);
        this.status = status;
        this.localizacao = localizacao;
    }

    public String getStatus() { return status; }
    public String getLocalizacao() { return localizacao; }

    @Override
    public void exibirBoasVindas() {
        System.out.println("Bem-vindo entregador: " + nome);
    }
}
