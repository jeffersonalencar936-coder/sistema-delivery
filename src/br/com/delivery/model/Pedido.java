package br.com.delivery.model;

import br.com.delivery.util.Auditavel;
import br.com.delivery.util.Calculavel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Auditavel, Calculavel {

    private int id;
    private int clienteId;
    private int restauranteId;
    private Integer entregadorId;

    private List<ItemPedido> itens;
    private String status;
    private List<String> historico;

    public Pedido(int id, int clienteId, int restauranteId, List<ItemPedido> itens, String status) {
        this.id = id;
        this.clienteId = clienteId;
        this.restauranteId = restauranteId;
        setItens(itens);
        setStatus(status);
        this.historico = new ArrayList<>();
        registrarLog("Pedido criado com status: " + status);
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

    @Override
    public void registrarLog(String acao) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String log = "[" + timestamp + "] " + acao;
        this.historico.add(log);
        System.out.println(log);
    }

    @Override
    public String obterHistorico() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== HISTORICO DE AUDITORIA DO PEDIDO #").append(id).append(" ==========\n");
        for (String log : historico) {
            sb.append(log).append("\n");
        }
        sb.append("=====================================\n");
        return sb.toString();
    }

    @Override
    public double calcularSubtotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    @Override
    public double calcularDesconto() {
        double subtotal = calcularSubtotal();

        if (subtotal > 300) return subtotal * 0.15;
        if (subtotal > 200) return subtotal * 0.10;
        if (subtotal > 100) return subtotal * 0.05;

        return 0;
    }

    @Override
    public double calcularValorTotal() {
        double subtotal = calcularSubtotal();
        double desconto = calcularDesconto();
        double taxa = calcularTaxaEntrega(subtotal, desconto);
        
        double valorTotal = subtotal - desconto + taxa;
        registrarLog("Cálculo de valor total: Subtotal=R$" + String.format("%.2f", subtotal) + 
                     " | Desconto=R$" + String.format("%.2f", desconto) + 
                     " | Taxa=R$" + String.format("%.2f", taxa) + 
                     " | Total=R$" + String.format("%.2f", valorTotal));
        
        return valorTotal;
    }

    
    private double calcularTaxaEntrega(double subtotal, double desconto) {
        double valorFinal = subtotal - desconto;
        
        if (valorFinal >= 150) {
            return 5.0;  
        } else if (valorFinal >= 100) {
            return 7.0;  
        } else {
            return 10.0; 
        }
    }
}
