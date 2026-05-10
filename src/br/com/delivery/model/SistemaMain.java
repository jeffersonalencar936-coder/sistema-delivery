package br.com.delivery.model;

import br.com.delivery.dao.*;
import br.com.delivery.service.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Sistema de Delivery - Classe Principal
 * Implementa um menu interativo para gerenciar:
 * - Clientes
 * - Restaurantes
 * - Entregadores
 * - Pedidos (com cálculo de valor com desconto progressivo e taxa variável)
 */
public class SistemaMain {

    static Scanner sc = new Scanner(System.in);

    // DAOs
    static ClienteDAO clienteDAO = new ClienteDAO();
    static RestauranteDAO restauranteDAO = new RestauranteDAO();
    static EntregadorDAO entregadorDAO = new EntregadorDAO();
    static PedidoDAO pedidoDAO = new PedidoDAO();

    // Services
    static ClienteService clienteService = new ClienteService();
    static PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {

        int op;

        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("      🚚 SISTEMA DE DELIVERY - MENU PRINCIPAL 🚚");
            System.out.println("=".repeat(50));
            System.out.println("\n📋 CLIENTES");
            System.out.println("1  - Cadastrar Cliente");
            System.out.println("2  - Listar Clientes");
            System.out.println("3  - Atualizar Cliente");
            System.out.println("4  - Deletar Cliente");

            System.out.println("\n🏪 RESTAURANTES");
            System.out.println("5  - Cadastrar Restaurante");
            System.out.println("6  - Listar Restaurantes");
            System.out.println("7  - Atualizar Restaurante");
            System.out.println("8  - Deletar Restaurante");
            System.out.println("9  - Gerar Relatório de Restaurante");

            System.out.println("\n🚴 ENTREGADORES");
            System.out.println("10 - Cadastrar Entregador");
            System.out.println("11 - Listar Entregadores");
            System.out.println("12 - Atualizar Entregador");
            System.out.println("13 - Deletar Entregador");

            System.out.println("\n📦 PEDIDOS");
            System.out.println("14 - Criar Pedido");
            System.out.println("15 - Listar Pedidos");
            System.out.println("16 - Ver Detalhes do Pedido");
            System.out.println("17 - Calcular Valor Final (Desconto + Taxa)");
            System.out.println("18 - Atualizar Status do Pedido");
            System.out.println("19 - Atribuir Entregador");
            System.out.println("20 - Deletar Pedido");

            System.out.println("\n0  - Sair");
            System.out.print("\nEscolha uma opção: ");

            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                // Clientes
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> atualizarCliente();
                case 4 -> deletarCliente();

                // Restaurantes
                case 5 -> cadastrarRestaurante();
                case 6 -> listarRestaurantes();
                case 7 -> atualizarRestaurante();
                case 8 -> deletarRestaurante();
                case 9 -> gerarRelatorioRestaurante();

                // Entregadores
                case 10 -> cadastrarEntregador();
                case 11 -> listarEntregadores();
                case 12 -> atualizarEntregador();
                case 13 -> deletarEntregador();

                // Pedidos
                case 14 -> criarPedido();
                case 15 -> listarPedidos();
                case 16 -> verDetalhesPedido();
                case 17 -> calcularValorFinal();
                case 18 -> atualizarStatusPedido();
                case 19 -> atribuirEntregador();
                case 20 -> deletarPedido();

                case 0 -> {
                    System.out.println("\n✓ Encerrando sistema... Até logo! 👋");
                    System.exit(0);
                }
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }

        } while (true);
    }

    // ============= CLIENTES =============

    static void cadastrarCliente() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Telefone: ");
            String tel = sc.nextLine();

            System.out.print("Endereço: ");
            String end = sc.nextLine();

            clienteService.criarCliente(nome, tel, end);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void listarClientes() {
        List<Cliente> lista = clienteDAO.listar();

        if (lista.isEmpty()) {
            System.out.println("❌ Nenhum cliente encontrado.");
        } else {
            System.out.println("\n" + "=".repeat(80));
            System.out.printf("%-5s %-30s %-20s %-25s%n", "ID", "NOME", "TELEFONE", "ENDEREÇO");
            System.out.println("=".repeat(80));
            for (Cliente c : lista) {
                System.out.printf("%-5d %-30s %-20s %-25s%n", c.getId(), c.getNome(), c.getTelefone(), c.getEndereco());
            }
            System.out.println("=".repeat(80));
        }
    }

    static void atualizarCliente() {
        try {
            System.out.print("ID do Cliente: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Novo Nome: ");
            String nome = sc.nextLine();

            System.out.print("Novo Telefone: ");
            String tel = sc.nextLine();

            System.out.print("Novo Endereço: ");
            String end = sc.nextLine();

            clienteService.atualizarCliente(id, nome, tel, end);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void deletarCliente() {
        try {
            System.out.print("ID do Cliente: ");
            int id = sc.nextInt();
            sc.nextLine();

            clienteService.deletarCliente(id);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    // ============= RESTAURANTES =============

    static void cadastrarRestaurante() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Endereço: ");
        String end = sc.nextLine();

        System.out.print("Avaliação (0-5): ");
        double av = sc.nextDouble();
        sc.nextLine();

        restauranteDAO.inserir(new Restaurante(0, nome, end, av));
        System.out.println("✓ Restaurante cadastrado com sucesso!");
    }

    static void listarRestaurantes() {
        List<Restaurante> lista = restauranteDAO.listar();

        if (lista.isEmpty()) {
            System.out.println("❌ Nenhum restaurante encontrado.");
        } else {
            System.out.println("\n" + "=".repeat(80));
            System.out.printf("%-5s %-30s %-30s %-10s%n", "ID", "NOME", "ENDEREÇO", "AVALIAÇÃO");
            System.out.println("=".repeat(80));
            for (Restaurante r : lista) {
                System.out.printf("%-5d %-30s %-30s %-10.1f⭐%n", r.getId(), r.getNome(), r.getEndereco(), r.getAvaliacao());
            }
            System.out.println("=".repeat(80));
        }
    }

    static void atualizarRestaurante() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Endereço: ");
        String end = sc.nextLine();

        System.out.print("Avaliação: ");
        double av = sc.nextDouble();
        sc.nextLine();

        restauranteDAO.atualizar(new Restaurante(id, nome, end, av));
        System.out.println("✓ Restaurante atualizado!");
    }

    static void deletarRestaurante() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        restauranteDAO.deletar(id);
    }

    static void gerarRelatorioRestaurante() {
        System.out.print("ID do Restaurante: ");
        int id = sc.nextInt();
        sc.nextLine();

        Restaurante r = restauranteDAO.buscarPorId(id);
        if (r == null) {
            System.out.println("❌ Restaurante não encontrado.");
        } else {
            System.out.println(r.gerarRelatorio());
            
            System.out.print("Deseja exportar os dados? (CSV/JSON/XML): ");
            String formato = sc.nextLine();
            try {
                System.out.println("\n" + r.exportarDados(formato));
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    // ============= ENTREGADORES =============

    static void cadastrarEntregador() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Telefone: ");
        String tel = sc.nextLine();

        System.out.print("Status: ");
        String status = sc.nextLine();

        System.out.print("Localização: ");
        String loc = sc.nextLine();

        entregadorDAO.inserir(new Entregador(0, nome, tel, status, loc));
        System.out.println("✓ Entregador cadastrado!");
    }

    static void listarEntregadores() {
        List<Entregador> lista = entregadorDAO.listar();

        if (lista.isEmpty()) {
            System.out.println("❌ Nenhum entregador encontrado.");
        } else {
            System.out.println("\n" + "=".repeat(90));
            System.out.printf("%-5s %-25s %-15s %-15s %-25s%n", "ID", "NOME", "TELEFONE", "STATUS", "LOCALIZAÇÃO");
            System.out.println("=".repeat(90));
            for (Entregador e : lista) {
                System.out.printf("%-5d %-25s %-15s %-15s %-25s%n", e.getId(), e.getNome(), e.getTelefone(), e.getStatus(), e.getLocalizacao());
            }
            System.out.println("=".repeat(90));
        }
    }

    static void atualizarEntregador() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Telefone: ");
        String tel = sc.nextLine();

        System.out.print("Status: ");
        String status = sc.nextLine();

        System.out.print("Localização: ");
        String loc = sc.nextLine();

        entregadorDAO.atualizar(new Entregador(id, nome, tel, status, loc));
        System.out.println("✓ Entregador atualizado!");
    }

    static void deletarEntregador() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        entregadorDAO.deletar(id);
    }

    // ============= PEDIDOS =============

    static void criarPedido() {
        try {
            System.out.print("ID do Cliente: ");
            int clienteId = sc.nextInt();
            sc.nextLine();

            System.out.print("ID do Restaurante: ");
            int restauranteId = sc.nextInt();
            sc.nextLine();

            // Validação
            if (clienteDAO.buscarPorId(clienteId) == null) {
                System.out.println("❌ Cliente não encontrado!");
                return;
            }
            if (restauranteDAO.buscarPorId(restauranteId) == null) {
                System.out.println("❌ Restaurante não encontrado!");
                return;
            }

            List<ItemPedido> itens = new ArrayList<>();

            System.out.print("Quantos itens terá o pedido? ");
            int qtdItens = sc.nextInt();
            sc.nextLine();

            for (int i = 1; i <= qtdItens; i++) {
                System.out.print("\nItem " + i + " - Nome: ");
                String nome = sc.nextLine();

                System.out.print("Quantidade: ");
                int qtd = sc.nextInt();

                System.out.print("Preço Unitário: ");
                double preco = sc.nextDouble();
                sc.nextLine();

                itens.add(new ItemPedido(nome, qtd, preco));
            }

            int pedidoId = pedidoService.criarPedido(clienteId, restauranteId, itens);
            System.out.println("\n✓ Pedido #" + pedidoId + " criado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarTodosPedidos();

        if (pedidos.isEmpty()) {
            System.out.println("❌ Nenhum pedido encontrado.");
        } else {
            System.out.println("\n" + "=".repeat(100));
            System.out.printf("%-6s %-8s %-10s %-12s %-12s %-12s %-20s%n", "ID", "CLIENTE", "RESTAURANTE", "ENTREGADOR", "STATUS", "VALOR", "DATA CRIAÇÃO");
            System.out.println("=".repeat(100));
            for (Pedido p : pedidos) {
                String entregador = p.getEntregadorId() != null ? String.valueOf(p.getEntregadorId()) : "-";
                System.out.printf("%-6d %-8d %-10d %-12s %-12s R$%-10.2f %s%n", 
                    p.getId(), p.getClienteId(), p.getRestauranteId(), entregador, p.getStatus(), p.calcularValorTotal(), "");
            }
            System.out.println("=".repeat(100));
        }
    }

    static void verDetalhesPedido() {
        try {
            System.out.print("ID do Pedido: ");
            int pedidoId = sc.nextInt();
            sc.nextLine();

            System.out.println(pedidoService.obterRelatorioPedido(pedidoId));

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void calcularValorFinal() {
        try {
            System.out.print("ID do Pedido: ");
            int pedidoId = sc.nextInt();
            sc.nextLine();

            double valor = pedidoService.calcularValorFinal(pedidoId);
            System.out.println("\n✓ Valor final calculado: R$ " + String.format("%.2f", valor));

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void atualizarStatusPedido() {
        try {
            System.out.print("ID do Pedido: ");
            int pedidoId = sc.nextInt();
            sc.nextLine();

            System.out.println("\nStatus disponíveis: PENDENTE, CONFIRMADO, PREPARANDO, PRONTO_ENTREGA, ENTREGUE, CANCELADO");
            System.out.print("Novo Status: ");
            String novoStatus = sc.nextLine().toUpperCase();

            pedidoService.atualizarStatus(pedidoId, novoStatus);
            System.out.println("✓ Status atualizado!");

        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void atribuirEntregador() {
        try {
            System.out.print("ID do Pedido: ");
            int pedidoId = sc.nextInt();
            sc.nextLine();

            System.out.print("ID do Entregador: ");
            int entregadorId = sc.nextInt();
            sc.nextLine();

            pedidoService.atribuirEntregador(pedidoId, entregadorId);
            System.out.println("✓ Entregador atribuído!");

        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void deletarPedido() {
        try {
            System.out.print("ID do Pedido: ");
            int pedidoId = sc.nextInt();
            sc.nextLine();

            pedidoService.deletarPedido(pedidoId);

        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
}
