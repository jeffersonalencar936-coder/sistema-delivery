package br.com.delivery.dao;

import br.com.delivery.model.Pedido;
import br.com.delivery.model.ItemPedido;
import br.com.delivery.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    /**
     * Insere um novo pedido no banco de dados com seus itens associados.
     */
    public int inserir(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedido (cliente_id, restaurante_id, entregador_id, status) " +
                           "VALUES (?, ?, ?, ?) RETURNING id";
        int pedidoId = -1;

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sqlPedido)) {

            stmt.setInt(1, pedido.getClienteId());
            stmt.setInt(2, pedido.getRestauranteId());
            if (pedido.getEntregadorId() != null) {
                stmt.setInt(3, pedido.getEntregadorId());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            stmt.setString(4, pedido.getStatus());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pedidoId = rs.getInt("id");
                inserirItensPedido(pedidoId, pedido.getItens());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidoId;
    }

    /**
     * Insere os itens associados a um pedido.
     */
    private void inserirItensPedido(int pedidoId, List<ItemPedido> itens) {
        String sql = "INSERT INTO item_pedido (pedido_id, nome, quantidade, preco_unitario) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (ItemPedido item : itens) {
                stmt.setInt(1, pedidoId);
                stmt.setString(2, item.getNome());
                stmt.setInt(3, item.getQuantidade());
                stmt.setDouble(4, item.getPrecoUnitario());
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca um pedido pelo ID, incluindo seus itens.
     */
    public Pedido buscarPorId(int id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        Pedido pedido = null;

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("restaurante_id"),
                        buscarItensPedido(rs.getInt("id")),
                        rs.getString("status")
                );
                
                if (rs.getObject("entregador_id") != null) {
                    pedido.setEntregadorId(rs.getInt("entregador_id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedido;
    }

    /**
     * Busca os itens de um pedido específico.
     */
    private List<ItemPedido> buscarItensPedido(int pedidoId) {
        List<ItemPedido> itens = new ArrayList<>();
        String sql = "SELECT * FROM item_pedido WHERE pedido_id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedidoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemPedido item = new ItemPedido(
                        rs.getString("nome"),
                        rs.getInt("quantidade"),
                        rs.getDouble("preco_unitario")
                );
                itens.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itens;
    }

    /**
     * Lista todos os pedidos do banco de dados.
     */
    public List<Pedido> listar() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido ORDER BY id DESC";

        try (Connection conn = ConexaoBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("restaurante_id"),
                        buscarItensPedido(rs.getInt("id")),
                        rs.getString("status")
                );
                
                if (rs.getObject("entregador_id") != null) {
                    pedido.setEntregadorId(rs.getInt("entregador_id"));
                }
                
                lista.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Atualiza o status de um pedido.
     */
    public void atualizarStatus(int pedidoId, String novoStatus) {
        String sql = "UPDATE pedido SET status = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, pedidoId);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Pedido #" + pedidoId + " atualizado para: " + novoStatus);
            } else {
                System.out.println("Pedido não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza o entregador de um pedido.
     */
    public void atribuirEntregador(int pedidoId, int entregadorId) {
        String sql = "UPDATE pedido SET entregador_id = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entregadorId);
            stmt.setInt(2, pedidoId);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Entregador atribuído ao pedido #" + pedidoId);
            } else {
                System.out.println("Pedido não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deleta um pedido e seus itens associados.
     */
    public void deletar(int id) {
        String sql = "DELETE FROM pedido WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Pedido #" + id + " removido com sucesso!");
            } else {
                System.out.println("Pedido não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista pedidos de um cliente específico.
     */
    public List<Pedido> buscarPorCliente(int clienteId) {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE cliente_id = ? ORDER BY id DESC";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("restaurante_id"),
                        buscarItensPedido(rs.getInt("id")),
                        rs.getString("status")
                );
                
                if (rs.getObject("entregador_id") != null) {
                    pedido.setEntregadorId(rs.getInt("entregador_id"));
                }
                
                lista.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
