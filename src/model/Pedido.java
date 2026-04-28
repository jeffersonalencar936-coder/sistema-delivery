package model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int id;
    private int clienteId;
    private int restauranteId;
    private Integer entregadorId;

    private List<ItemPedido> itens;
    private String status;

    public Pedido(int id, int clienteId, int restauranteId, List<ItemPedido> itens, String status) {
        this.id = id;
        this.clienteId = clienteId;
        this.restauranteId = restauranteId;
        setItens(itens);
        setStatus(status);
    }

    public int getId() { return id; }

    public int getClienteId() { return clienteId; }

    public int getRestauranteId() { return restauranteId; }

    public Integer getEntregadorId() { return entregadorId; }

    public void setEntregadorId(Integer entregadorId) {
        this.entregadorId = entregadorId;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }

    public void setItens(List<ItemPedido> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Pedido deve conter itens.");
        }
        this.itens = new ArrayList<>(itens);
    }

    public String getStatus() { return status; }

    public void setStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status inválido.");
        }
        this.status = status;
    }

    public double calcularSubtotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public double calcularDesconto() {
        double subtotal = calcularSubtotal();

        if (subtotal > 300) return subtotal * 0.15;
        if (subtotal > 200) return subtotal * 0.10;
        if (subtotal > 100) return subtotal * 0.05;

        return 0;
    }

    public double calcularValorTotal() {
        return calcularSubtotal() - calcularDesconto() + 8.0;
    }
}