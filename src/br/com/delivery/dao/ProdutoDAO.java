package br.com.delivery.dao;

import br.com.delivery.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private static List<Produto> produtos = new ArrayList<>();

    static {
        
        produtos.add(new Produto(1, "Pizza", 25.98));
        produtos.add(new Produto(2, "Hamburger", 15.50));
        produtos.add(new Produto(3, "Batata Frita", 8.00));
        produtos.add(new Produto(4, "Refrigerante 350ml", 5.00));
        produtos.add(new Produto(5, "Sorvete", 7.50));
    }

    public List<Produto> listar() {
        return new ArrayList<>(produtos);
    }

    public Produto buscarPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}