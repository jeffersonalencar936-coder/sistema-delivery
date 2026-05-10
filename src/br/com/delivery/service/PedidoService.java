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

/**
 * Serviço de lógica de negócio para operações com pedidos.
 * Implementa as regras de negócio complexas do sistema de delivery.
 */
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

    /**
     * Cria um novo pedido após validações.
     * Regra de negócio: Valida cliente, restaurante e itens.
     */
    public int criarPedido(int clienteId, int restauranteId, List<ItemPedido> itens) {
        // Validação: Cliente existe?
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente com ID " + clienteId + " não encontrado.");
        }

        // Validação: Restaurante existe?
        Restaurante restaurante = restauranteDAO.buscarPorId(restauranteId);
        if (restaurante == null) {
            throw new IllegalArgumentException("Restaurante com ID " + restauranteId + " não encontrado.");
        }

        // Validação: Itens válidos?
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Pedido deve conter pelo menos um item.");
        }

        // Cria o pedido
        Pedido pedido = new Pedido(0, clienteId, restauranteId, itens, "PENDENTE");
        int pedidoId = pedidoDAO.inserir(pedido);

        if (pedidoId > 0) {
            System.out.println("✓ Pedido #" + pedidoId + " criado com sucesso para " + cliente.getNome());
        }

        return pedidoId;
    }

    /**
     * Calcula o valor total do pedido com a regra de negócio complexa.
     * Regra: Desconto progressivo + Taxa de entrega variável
     */
    public double calcularValorFinal(int pedidoId) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }

        double valorTotal = pedido.calcularValorTotal();
        System.out.println("Valor final do pedido #" + pedidoId + ": R$ " + String.format("%.2f", valorTotal));
        return valorTotal;
    }

    /**
     * Obtém um relatório detalhado do pedido com cálculos e histórico.
     */
    public String obterRelatorioPedido(int pedidoId) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("\n========== RELATÓRIO DETALHADO DO PEDIDO #").append(pedidoId).append(" ==========\n");
        relatorio.append("Status: ").append(pedido.getStatus()).append("\n");
        relatorio.append("Cliente ID: ").append(pedido.getClienteId()).append("\n");
        relatorio.append("Restaurante ID: ").append(pedido.getRestauranteId()).append("\n");

        if (pedido.getEntregadorId() != null) {
            relatorio.append("Entregador ID: ").append(pedido.getEntregadorId()).append("\n");
        }

        relatorio.append("\n--- Itens do Pedido ---\n");
        List<ItemPedido> itens = pedido.getItens();
        for (ItemPedido item : itens) {
            relatorio.append("  • ").append(item.getNome())
                    .append(" x").append(item.getQuantidade())
                    .append(" = R$ ").append(String.format("%.2f", item.calcularSubtotal()))
                    .append("\n");
        }

        relatorio.append("\n--- Cálculo de Valores ---\n");
        relatorio.append("Subtotal: R$ ").append(String.format("%.2f", pedido.calcularSubtotal())).append("\n");
        relatorio.append("Desconto: R$ ").append(String.format("%.2f", pedido.calcularDesconto())).append("\n");
        relatorio.append("Valor com desconto: R$ ").append(String.format("%.2f", 
                pedido.calcularSubtotal() - pedido.calcularDesconto())).append("\n");
        relatorio.append("Taxa de Entrega: R$ ").append(String.format("%.2f", 
                pedido.calcularValorTotal() - (pedido.calcularSubtotal() - pedido.calcularDesconto()))).append("\n");
        relatorio.append("VALOR TOTAL: R$ ").append(String.format("%.2f", pedido.calcularValorTotal())).append("\n");

        relatorio.append("\n--- Histórico de Auditoria ---\n");
        relatorio.append(pedido.obterHistorico());

        relatorio.append("====================================\n\n");
        return relatorio.toString();
    }

    /**
     * Atualiza o status do pedido com validações.
     */
    public void atualizarStatus(int pedidoId, String novoStatus) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }

        // Validação de transição de status
        String statusAtual = pedido.getStatus();
        if (!isTransicaoValida(statusAtual, novoStatus)) {
            throw new IllegalStateException("Transição inválida: " + statusAtual + " → " + novoStatus);
        }

        pedidoDAO.atualizarStatus(pedidoId, novoStatus);
        pedido.registrarLog("Status alterado para: " + novoStatus);
    }

    /**
     * Atribui um entregador a um pedido.
     */
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
        pedido.registrarLog("Entregador " + entregador.getNome() + " atribuído ao pedido.");
    }

    /**
     * Lista todos os pedidos de um cliente.
     */
    public List<Pedido> listarPedidosCliente(int clienteId) {
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        return pedidoDAO.buscarPorCliente(clienteId);
    }

    /**
     * Lista todos os pedidos com um status específico.
     */
    public List<Pedido> listarPedidosPorStatus(String status) {
        List<Pedido> todosPedidos = pedidoDAO.listar();
        return todosPedidos.stream()
                .filter(p -> p.getStatus().equals(status))
                .toList();
    }

    /**
     * Valida se a transição de status é permitida.
     */
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
                return false; // Estados finais
            default:
                return false;
        }
    }

    /**
     * Deleta um pedido.
     */
    public void deletarPedido(int pedidoId) {
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }

        pedidoDAO.deletar(pedidoId);
        System.out.println("✓ Pedido #" + pedidoId + " deletado com sucesso.");
    }

    /**
     * Obtém um pedido pelo ID.
     */
    public Pedido obterPedido(int pedidoId) {
        return pedidoDAO.buscarPorId(pedidoId);
    }

    /**
     * Lista todos os pedidos.
     */
    public List<Pedido> listarTodosPedidos() {
        return pedidoDAO.listar();
    }
}
