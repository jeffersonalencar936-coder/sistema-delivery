package br.com.delivery.service;

import br.com.delivery.model.Pedido;
import br.com.delivery.model.ItemPedido;
import br.com.delivery.dao.PedidoDAO;
import br.com.delivery.dao.ClienteDAO;
import br.com.delivery.dao.RestauranteDAO;
import br.com.delivery.dao.EntregadorDAO;
import br.com.delivery.model.Cliente;
import br.com.delivery.model.Restaurante;
import br.com.delivery.model.Entregador;
import java.util.List;


public class PedidoService {

    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;
    private RestauranteDAO restauranteDAO;
    private EntregadorDAO entregadorDAO;

    public PedidoService() {
        this.pedidoDAO = new PedidoDAO();
        this.clienteDAO = new ClienteDAO();
        this.restauranteDAO = new RestauranteDAO();
        this.entregadorDAO = new EntregadorDAO();
    }

    
    public int criarPedido(int clienteId, int restauranteId, List<ItemPedido> itens) {
        
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente com ID " + clienteId + " não encontrado.");
        }

        
        Restaurante restaurante = restauranteDAO.buscarPorId(restauranteId);
        if (restaurante == null) {
            throw new IllegalArgumentException("Restaurante com ID " + restauranteId + " não encontrado.");
        }

        
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Pedido deve conter pelo menos um item.");
        }

        
        Pedido pedido = new Pedido(0, clienteId, restauranteId, itens, "PENDENTE");
        int pedidoId = pedidoDAO.inserir(pedido);

        if (pedidoId > 0) {
            System.out.println(" Pedido #" + pedidoId + " criado com sucesso para " + cliente.getNome());
        }

        return pedidoId;
    }

    
    public double calcularValorFinal(int pedidoId) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }

        double valorTotal = pedido.calcularValorTotal();
        System.out.println("Valor final do pedido #" + pedidoId + ": R$ " + String.format("%.2f", valorTotal));
        return valorTotal;
    }

    
    public String obterRelatorioPedido(int pedidoId) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("\n========== RELAT�RIO DETALHADO DO PEDIDO #").append(pedidoId).append(" ==========\n");
        relatorio.append("Status: ").append(pedido.getStatus()).append("\n");
        relatorio.append("Cliente ID: ").append(pedido.getClienteId()).append("\n");
        relatorio.append("Restaurante ID: ").append(pedido.getRestauranteId()).append("\n");

        if (pedido.getEntregadorId() != null) {
            relatorio.append("Entregador ID: ").append(pedido.getEntregadorId()).append("\n");
        }

        relatorio.append("\n--- Itens do Pedido ---\n");
        List<ItemPedido> itens = pedido.getItens();
        for (ItemPedido item : itens) {
            relatorio.append("  - ").append(item.getNome())
                    .append(" x").append(item.getQuantidade())
                    .append(" = R$ ").append(String.format("%.2f", item.calcularSubtotal()))
                    .append("\n");
        }

        relatorio.append("\n--- Cálculo de Valores ---\n");
        double subtotal = pedido.calcularSubtotal();
        double desconto = pedido.calcularDesconto();
        double valorComDesconto = subtotal - desconto;
        double taxa = calcularTaxaEntrega(valorComDesconto);
        double valorTotal = valorComDesconto + taxa;
        relatorio.append("Subtotal: R$ ").append(String.format("%.2f", subtotal)).append("\n");
        relatorio.append("Desconto: R$ ").append(String.format("%.2f", desconto)).append("\n");
        relatorio.append("Valor com desconto: R$ ").append(String.format("%.2f", valorComDesconto)).append("\n");
        relatorio.append("Taxa de Entrega: R$ ").append(String.format("%.2f", taxa)).append("\n");
        relatorio.append("VALOR TOTAL: R$ ").append(String.format("%.2f", valorTotal)).append("\n");

        relatorio.append("\n--- Histórico de Auditoria ---\n");
        relatorio.append(pedido.obterHistorico());

        relatorio.append("====================================\n\n");
        return relatorio.toString();
    }

    
    public void atualizarStatus(int pedidoId, String novoStatus) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }

        
        String statusAtual = pedido.getStatus();
        if (!isTransicaoValida(statusAtual, novoStatus)) {
            throw new IllegalStateException("Transição inválida: " + statusAtual + " -> " + novoStatus);
        }

        pedidoDAO.atualizarStatus(pedidoId, novoStatus);
        pedido.setStatus(novoStatus);
        pedido.registrarLog("Status alterado para: " + novoStatus);
    }

    
    public void atribuirEntregador(int pedidoId, int entregadorId) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }

        Entregador entregador = entregadorDAO.buscarPorId(entregadorId);
        if (entregador == null) {
            throw new IllegalArgumentException("Entregador não encontrado.");
        }

        pedidoDAO.atribuirEntregador(pedidoId, entregadorId);
        pedido.registrarLog("Entregador " + entregador.getNome() + " atribuido ao pedido.");
    }

    
    public List<Pedido> listarPedidosCliente(int clienteId) {
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        return pedidoDAO.buscarPorCliente(clienteId);
    }

    
    public List<Pedido> listarPedidosPorStatus(String status) {
        List<Pedido> todosPedidos = pedidoDAO.listar();
        return todosPedidos.stream()
                .filter(p -> p.getStatus().equals(status))
                .toList();
    }

    
    private boolean isTransicaoValida(String statusAtual, String novoStatus) {
        switch (statusAtual.toUpperCase()) {
            case "PENDENTE":
                return novoStatus.equals("CONFIRMADO") || novoStatus.equals("CANCELADO");
            case "CONFIRMADO":
                return novoStatus.equals("PREPARANDO") || novoStatus.equals("CANCELADO");
            case "PREPARANDO":
                return novoStatus.equals("PRONTO_ENTREGA") || novoStatus.equals("CANCELADO");
            case "PRONTO_ENTREGA":
                return novoStatus.equals("ENTREGUE") || novoStatus.equals("CANCELADO");
            case "ENTREGUE":
            case "CANCELADO":
                return false; 
            default:
                return false;
        }
    }

    
    public void deletarPedido(int pedidoId) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }

        pedidoDAO.deletar(pedidoId);
        System.out.println(" Pedido #" + pedidoId + " deletado com sucesso.");
    }

    
    public Pedido obterPedido(int pedidoId) {
        return pedidoDAO.buscarPorId(pedidoId);
    }

    
    public List<Pedido> listarTodosPedidos() {
        return pedidoDAO.listar();
    }

    private double calcularTaxaEntrega(double valorFinal) {
        if (valorFinal >= 150) {
            return 5.0;
        } else if (valorFinal >= 100) {
            return 7.0;
        } else {
            return 10.0;
        }
    }
}
