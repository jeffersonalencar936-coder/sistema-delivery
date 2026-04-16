public class ItemPedido {
    private String nome;
    private int quantidade;
    private double precoUnitario;

    public ItemPedido(String nome, int quantidade, double precoUnitario) {
        setNome(nome);
        setQuantidade(quantidade);
        setPrecoUnitario(precoUnitario);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do item de pedido não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        if (precoUnitario < 0.0) {
            throw new IllegalArgumentException("Preço unitário não pode ser negativo.");
        }
        this.precoUnitario = precoUnitario;
    }

    public double calcularSubtotal() {
        return quantidade * precoUnitario;
    }

    @Override
    public String toString() {
        return "ItemPedido{nome='" + nome + '\'' + ", quantidade=" + quantidade + ", precoUnitario=" + precoUnitario + '}';
    }
}
