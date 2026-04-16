import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private Restaurante restaurante;
    private List<ItemPedido> itens;
    private double valorTotal;
    private String status;
    private Entregador entregador;

    public Pedido(int id, Cliente cliente, Restaurante restaurante, List<ItemPedido> itens, String status) {
        setId(id);
        setCliente(cliente);
        setRestaurante(restaurante);
        setItens(itens);
        setStatus(status);
        this.entregador = null;
        this.valorTotal = calcularValorTotal();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do pedido deve ser maior que zero.");
        }
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        this.cliente = cliente;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        if (restaurante == null) {
            throw new IllegalArgumentException("Restaurante não pode ser nulo.");
        }
        this.restaurante = restaurante;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }

    public void setItens(List<ItemPedido> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Pedido deve conter pelo menos um item.");
        }
        this.itens = new ArrayList<>(itens);
        this.valorTotal = calcularValorTotal();
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        if (valorTotal < 0.0) {
            throw new IllegalArgumentException("Valor total não pode ser negativo.");
        }
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status do pedido não pode ser vazio.");
        }
        this.status = status.trim();
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public void setEntregador(Entregador entregador) {
        if (entregador == null) {
            this.entregador = null;
            return;
        }
        if (!"disponível".equalsIgnoreCase(entregador.getStatusDisponibilidade())) {
            throw new IllegalArgumentException("Entregador deve estar disponível para ser atribuído ao pedido.");
        }
        this.entregador = entregador;
        entregador.setStatusDisponibilidade("em entrega");
    }

    public double calcularSubtotal() {
        double subtotal = 0.0;
        for (ItemPedido item : itens) {
            subtotal += item.calcularSubtotal();
        }
        return subtotal;
    }

    public double calcularDesconto() {
        double subtotal = calcularSubtotal();
        if (subtotal > 300.0) {
            return subtotal * 0.15;
        }
        if (subtotal > 200.0) {
            return subtotal * 0.10;
        }
        if (subtotal > 100.0) {
            return subtotal * 0.05;
        }
        return 0.0;
    }

    public double calcularValorTotal() {
        double subtotal = calcularSubtotal();
        double desconto = calcularDesconto();
        double taxaEntrega = 8.0;
        return subtotal - desconto + taxaEntrega;
    }

    @Override
    public String toString() {
        return "Pedido{id=" + id + ", cliente=" + cliente + ", restaurante=" + restaurante + ", itens=" + itens + ", valorTotal=" + valorTotal + ", status='" + status + '\'' + ", entregador=" + entregador + '}';
    }
}
