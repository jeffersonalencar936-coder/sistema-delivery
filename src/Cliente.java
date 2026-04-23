public class Cliente extends Usuario {
    private String endereco;

    public Cliente(int id, String nome, String telefone, String endereco) {
        super(id, nome, telefone);
        setEndereco(endereco);
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço do cliente não pode ser vazio.");
        }
        this.endereco = endereco.trim();
    }

    @Override
    public String getInformacoes() {
        return super.getInformacoes() + ", Endereço: " + endereco;
    }

    @Override
    public void exibirBoasVindas() {
        System.out.println("Bem-vindo ao Sistema de Delivery, cliente " + nome + "!");
        System.out.println("Seu endereço de entrega: " + endereco);
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nome='" + nome + '\'' + ", telefone='" + telefone + '\'' + ", endereco='" + endereco + '\'' + '}';
    }
}
