package dao;

import model.Restaurante;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestauranteDAO {

    public void inserir(Restaurante r) {
        String sql = "INSERT INTO restaurante (nome, endereco, avaliacao) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getNome());
            stmt.setString(2, r.getEndereco());
            stmt.setDouble(3, r.getAvaliacao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Restaurante> listar() {
        List<Restaurante> lista = new ArrayList<>();
        String sql = "SELECT * FROM restaurante";

        try (Connection conn = ConexaoBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Restaurante(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getDouble("avaliacao")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void atualizar(Restaurante r) {
        String sql = "UPDATE restaurante SET nome=?, endereco=?, avaliacao=? WHERE id=?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getNome());
            stmt.setString(2, r.getEndereco());
            stmt.setDouble(3, r.getAvaliacao());
            stmt.setInt(4, r.getId());

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Restaurante atualizado!");
            } else {
                System.out.println("Restaurante não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM restaurante WHERE id=?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Restaurante removido!");
            } else {
                System.out.println("Restaurante não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}