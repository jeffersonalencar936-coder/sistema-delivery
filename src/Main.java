import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Restaurante restaurante = new Restaurante(1, "Sabor Caseiro", "Rua das Flores, 123", 4.7);
        Cliente cliente = new Cliente(1, "Mariana Silva", "(11) 99999-9999", "Av. Central, 56");
        Entregador entregador = new Entregador(1, "Lucas Pereira", "(11) 98888-8888", "disponível", "Rua Primavera, 45");

        ItemPedido item1 = new ItemPedido("Hambúrguer", 2, 28.50);
        ItemPedido item2 = new ItemPedido("Refrigerante", 1, 8.00);
        List<ItemPedido> itens = Arrays.asList(item1, item2);

        Pedido pedido = new Pedido(1, cliente, restaurante, itens, "em preparação");
        pedido.setEntregador(entregador);

        System.out.println(restaurante);
        System.out.println(cliente);
        System.out.println(entregador);
        System.out.println(pedido);
        System.out.println("Subtotal: R$ " + String.format("%.2f", pedido.calcularSubtotal()));
        System.out.println("Desconto: R$ " + String.format("%.2f", pedido.calcularDesconto()));
        System.out.println("Valor total: R$ " + String.format("%.2f", pedido.calcularValorTotal()));
    }
}
