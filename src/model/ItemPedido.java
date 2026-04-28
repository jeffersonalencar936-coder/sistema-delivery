package model;
public class ItemPedido {
    private String nome;
    private int quantidade;
    private double precoUnitario;

    public ItemPedido(String nome, int quantidade, double precoUnitario) {
        setNome(nome);
        setQuantidade(quantidade);
        setPrecoUnitario(precoUnitario);
    }

    public ItemPedido(String nome, double precoUnitario) {
        this(nome, 1, precoUnitario);
    }

    public ItemPedido(String nome, int quantidade, double precoUnitario, String observacao) {
        this(nome, quantidade, precoUnitario);
        System.out.println("Observação adicionada: " + observacao);
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

    public double calcularSubtotal(double percentualDesconto) {
        double subtotal = quantidade * precoUnitario;
        return subtotal - (subtotal * percentualDesconto / 100);
    }

    public void exibirInformacoes() {
        System.out.println("Item: " + nome);
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Preço Unitário: R$ " + precoUnitario);
        System.out.println("Subtotal: R$ " + calcularSubtotal());
    }

    public void exibirInformacoes(boolean mostrarDetalhes) {
        System.out.println("=== INFORMAÇÕES DO ITEM ===");
        System.out.println("Item: " + nome);
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Preço Unitário: R$ " + precoUnitario);
        System.out.println("Subtotal: R$ " + calcularSubtotal());
        
        if (mostrarDetalhes) {
            System.out.println("--- Detalhes Extras ---");
            System.out.println("Valor com 10% desconto: R$ " + calcularSubtotal(10));
            System.out.println("Valor com 20% desconto: R$ " + calcularSubtotal(20));
        }
    }

    @Override
    public String toString() {
        return "ItemPedido{nome='" + nome + '\'' + ", quantidade=" + quantidade + ", precoUnitario=" + precoUnitario + '}';
    }
}
