package dao;

import model.Entregador;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregadorDAO {

    public void inserir(Entregador e) {
        String sql = "INSERT INTO entregador (nome, telefone, status, localizacao) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getTelefone());
            stmt.setString(3, e.getStatus());
            stmt.setString(4, e.getLocalizacao());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Entregador> listar() {
        List<Entregador> lista = new ArrayList<>();
        String sql = "SELECT * FROM entregador";

        try (Connection conn = ConexaoBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Entregador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("status"),
                        rs.getString("localizacao")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    public void atualizar(Entregador e) {
        String sql = "UPDATE entregador SET nome=?, telefone=?, status=?, localizacao=? WHERE id=?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getTelefone());
            stmt.setString(3, e.getStatus());
            stmt.setString(4, e.getLocalizacao());
            stmt.setInt(5, e.getId());

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Entregador atualizado!");
            } else {
                System.out.println("Entregador não encontrado.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM entregador WHERE id=?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Entregador removido!");
            } else {
                System.out.println("Entregador não encontrado.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}