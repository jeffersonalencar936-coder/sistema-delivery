import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaMain {

    private static Scanner scanner = new Scanner(System.in);

    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Restaurante> restaurantes = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static List<Entregador> entregadores = new ArrayList<>();

    public static void main(String[] args) {

        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    cadastrarRestaurante();
                    break;
                case 3:
                    cadastrarEntregador();
                    break;
                case 4:
                    criarPedido();
                    break;
                case 5:
                    listarPedidos();
                    break;
                case 6:
                    listarEntregadores();
                    break;
                case 0:
                    System.out.println("Encerrando sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===== SISTEMA DELIVERY =====");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Cadastrar Restaurante");
        System.out.println("3. Cadastrar Entregador");
        System.out.println("4. Criar Pedido");
        System.out.println("5. Listar Pedidos");
        System.out.println("6. Listar Entregadores");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarCliente() {
        System.out.println("\n--- CADASTRAR CLIENTE ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        Cliente cliente = new Cliente(clientes.size() + 1, nome, telefone, endereco);
        clientes.add(cliente);

        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void cadastrarRestaurante() {
        System.out.println("\n--- CADASTRAR RESTAURANTE ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        System.out.print("Avaliação: ");
        double avaliacao = scanner.nextDouble();
        scanner.nextLine();

        Restaurante restaurante = new Restaurante(
                restaurantes.size() + 1,
                nome,
                endereco,
                avaliacao
        );

        restaurantes.add(restaurante);

        System.out.println("Restaurante cadastrado com sucesso!");
    }

    private static void cadastrarEntregador() {
        System.out.println("\n--- CADASTRAR ENTREGADOR ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Status (disponível / em entrega / indisponível): ");
        String status = scanner.nextLine();

        System.out.print("Localização atual: ");
        String localizacao = scanner.nextLine();

        Entregador entregador = new Entregador(
                entregadores.size() + 1,
                nome,
                telefone,
                status,
                localizacao
        );

        entregadores.add(entregador);

        System.out.println("Entregador cadastrado com sucesso!");
    }

    private static void listarEntregadores() {
        System.out.println("\n--- LISTA DE ENTREGADORES ---");

        if (entregadores.isEmpty()) {
            System.out.println("Nenhum entregador cadastrado.");
        } else {
            for (Entregador e : entregadores) {
                System.out.println(e);
            }
        }
    }

    private static void criarPedido() {
        System.out.println("\n--- CRIAR PEDIDO ---");

        if (clientes.isEmpty() || restaurantes.isEmpty()) {
            System.out.println("Cadastre cliente e restaurante primeiro!");
            return;
        }

        Cliente cliente = clientes.get(0); // simplificado
        Restaurante restaurante = restaurantes.get(0); // simplificado

        List<ItemPedido> itens = new ArrayList<>();

        String opcao;
        do {
            System.out.print("Nome do item: ");
            String nome = scanner.nextLine();

            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();

            System.out.print("Preço: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();

            itens.add(new ItemPedido(nome, quantidade, preco));

            System.out.print("Adicionar mais itens? (s/n): ");
            opcao = scanner.nextLine();

        } while (opcao.equalsIgnoreCase("s"));

        Pedido pedido = new Pedido(
                pedidos.size() + 1,
                cliente,
                restaurante,
                itens,
                "em preparação"
        );

        // 🔥 VINCULAR ENTREGADOR
        if (!entregadores.isEmpty()) {
            for (Entregador e : entregadores) {
                if (e.getStatusDisponibilidade().equalsIgnoreCase("disponível")) {
                    pedido.setEntregador(e);
                    e.setStatusDisponibilidade("em entrega");
                    System.out.println("Entregador atribuído: " + e.getNome());
                    break;
                }
            }
        } else {
            System.out.println("Nenhum entregador disponível.");
        }

        pedidos.add(pedido);

        System.out.println("Pedido criado com sucesso!");
        System.out.println("Total: R$ " + String.format("%.2f", pedido.calcularValorTotal()));
    }

    private static void listarPedidos() {
        System.out.println("\n--- LISTA DE PEDIDOS ---");

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
        } else {
            for (Pedido p : pedidos) {
                System.out.println(p);
            }
        }
    }
}
