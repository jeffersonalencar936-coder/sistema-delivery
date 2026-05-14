package br.com.delivery.model;

import br.com.delivery.dao.*;
import br.com.delivery.service.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaMain {
    static Scanner sc = new Scanner(System.in);

    
    static ClienteDAO clienteDAO = new ClienteDAO();
    static RestauranteDAO restauranteDAO = new RestauranteDAO();
    static EntregadorDAO entregadorDAO = new EntregadorDAO();
    static PedidoDAO pedidoDAO = new PedidoDAO();
    static br.com.delivery.dao.ProdutoDAO produtoDAO = new br.com.delivery.dao.ProdutoDAO();

    
    static ClienteService clienteService = new ClienteService();
    static PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {

        int op;

        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("     SISTEMA DE DELIVERY - MENU PRINCIPAL ");
            System.out.println("=".repeat(50));
            System.out.println("\n  CLIENTES");
            System.out.println("1  - Cadastrar Cliente");
            System.out.println("2  - Listar Clientes");
            System.out.println("3  - Buscar Cliente por ID");
            System.out.println("4  - Atualizar Cliente");
            System.out.println("5  - Deletar Cliente");

            System.out.println("\n  RESTAURANTES");
            System.out.println("6  - Cadastrar Restaurante");
            System.out.println("7  - Listar Restaurantes");
            System.out.println("8  - Atualizar Restaurante");
            System.out.println("9  - Deletar Restaurante");
            System.out.println("10 - Gerar Relatório de Restaurante");

            System.out.println("\n  ENTREGADORES");
            System.out.println("11 - Cadastrar Entregador");
            System.out.println("12 - Listar Entregadores");
            System.out.println("13 - Atualizar Entregador");
            System.out.println("14 - Deletar Entregador");

            System.out.println("\n  PRODUTOS");
            System.out.println("15 - Listar Cardápio");

            System.out.println("\n  PEDIDOS");
            System.out.println("16 - Criar Pedido");
            System.out.println("17 - Listar Todos os Pedidos");
            System.out.println("18 - Listar Pedidos por Cliente");
            System.out.println("19 - Listar Pedidos por Status");
            System.out.println("20 - Ver Detalhes do Pedido");
            System.out.println("21 - Calcular Valor Final (Desconto + Taxa)");
            System.out.println("22 - Atualizar Status do Pedido");
            System.out.println("23 - Atribuir Entregador");
            System.out.println("24 - Deletar Pedido");

            System.out.println("\n0  - Sair");
            System.out.print("\nEscolha uma opção: ");

            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> buscarCliente();
                case 4 -> atualizarCliente();
                case 5 -> deletarCliente();

                
                case 6 -> cadastrarRestaurante();
                case 7 -> listarRestaurantes();
                case 8 -> atualizarRestaurante();
                case 9 -> deletarRestaurante();
                case 10 -> gerarRelatorioRestaurante();

                
                case 11 -> cadastrarEntregador();
                case 12 -> listarEntregadores();
                case 13 -> atualizarEntregador();
                case 14 -> deletarEntregador();

                
                case 15 -> listarCardapio();

                
                case 16 -> criarPedido();
                case 17 -> listarPedidos();
                case 18 -> listarPedidosPorCliente();
                case 19 -> listarPedidosPorStatus();
                case 20 -> verDetalhesPedido();
                case 21 -> calcularValorFinal();
                case 22 -> atualizarStatusPedido();
                case 23 -> atribuirEntregador();
                case 24 -> deletarPedido();

                case 0 -> {
                    System.out.println("\n✓ Encerrando sistema... Até logo! 👋");
                    System.exit(0);
                }
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }

        } while (true);
    }

    

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

    static void buscarCliente() {
        try {
            System.out.print("ID do Cliente: ");
            int id = sc.nextInt();
            sc.nextLine();

            Cliente c = clienteService.obterCliente(id);
            System.out.println("\n" + "=".repeat(60));
            System.out.println("  DADOS DO CLIENTE");
            System.out.println("=".repeat(60));
            System.out.printf("ID:       %d%n", c.getId());
            System.out.printf("Nome:     %s%n", c.getNome());
            System.out.printf("Telefone: %s%n", c.getTelefone());
            System.out.printf("Endereço: %s%n", c.getEndereco());
            System.out.println("=".repeat(60));
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    

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

    

    static void listarCardapio() {
        java.util.List<br.com.delivery.model.Produto> cardapio = produtoDAO.listar();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           🍽️  CARDÁPIO DISPONÍVEL");
        System.out.println("=".repeat(50));
        System.out.printf("%-5s %-30s %s%n", "Nº", "PRODUTO", "PREÇO");
        System.out.println("-".repeat(50));
        for (br.com.delivery.model.Produto p : cardapio) {
            System.out.printf("%-5d %-30s R$ %.2f%n", p.getId(), p.getNome(), p.getPreco());
        }
        System.out.println("=".repeat(50));
    }

    

    static void criarPedido() {
        try {
            System.out.print("ID do Cliente: ");
            int clienteId = sc.nextInt();
            sc.nextLine();

            System.out.print("ID do Restaurante: ");
            int restauranteId = sc.nextInt();
            sc.nextLine();

            
            if (clienteDAO.buscarPorId(clienteId) == null) {
                System.out.println("❌ Cliente não encontrado!");
                return;
            }
            if (restauranteDAO.buscarPorId(restauranteId) == null) {
                System.out.println("❌ Restaurante não encontrado!");
                return;
            }

            List<ItemPedido> itens = new ArrayList<>();

            
            java.util.List<br.com.delivery.model.Produto> cardapio = produtoDAO.listar();
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           🍽️  CARDÁPIO DISPONÍVEL");
            System.out.println("=".repeat(50));
            System.out.printf("%-5s %-30s %s%n", "Nº", "PRODUTO", "PREÇO");
            System.out.println("-".repeat(50));
            for (br.com.delivery.model.Produto p : cardapio) {
                System.out.printf("%-5d %-30s R$ %.2f%n", p.getId(), p.getNome(), p.getPreco());
            }
            System.out.println("=".repeat(50));

            boolean adicionarMais = true;
            while (adicionarMais) {
                System.out.print("\nDigite o número do produto (ou 0 para finalizar): ");
                int produtoId = sc.nextInt();
                sc.nextLine();

                if (produtoId == 0) {
                    if (itens.isEmpty()) {
                        System.out.println("❌ O pedido deve ter pelo menos 1 item!");
                        continue;
                    }
                    adicionarMais = false;
                    continue;
                }

                br.com.delivery.model.Produto produtoSelecionado = produtoDAO.buscarPorId(produtoId);
                if (produtoSelecionado == null) {
                    System.out.println("❌ Produto inválido! Escolha um número do cardápio.");
                    continue;
                }

                System.out.print("Quantidade de \"" + produtoSelecionado.getNome() + "\": ");
                int qtd = sc.nextInt();
                sc.nextLine();

                if (qtd <= 0) {
                    System.out.println("❌ Quantidade deve ser maior que zero.");
                    continue;
                }

                itens.add(new ItemPedido(produtoSelecionado.getNome(), qtd, produtoSelecionado.getPreco()));
                System.out.printf("✓ Adicionado: %dx %s (R$ %.2f cada)%n",
                        qtd, produtoSelecionado.getNome(), produtoSelecionado.getPreco());
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

    static void listarPedidosPorCliente() {
        try {
            System.out.print("ID do Cliente: ");
            int clienteId = sc.nextInt();
            sc.nextLine();

            List<Pedido> pedidos = pedidoService.listarPedidosCliente(clienteId);
            if (pedidos.isEmpty()) {
                System.out.println("❌ Nenhum pedido encontrado para este cliente.");
            } else {
                System.out.println("\n" + "=".repeat(80));
                System.out.printf("%-6s %-10s %-12s %-12s %-12s%n", "ID", "RESTAURANTE", "ENTREGADOR", "STATUS", "VALOR");
                System.out.println("=".repeat(80));
                for (Pedido p : pedidos) {
                    String entregador = p.getEntregadorId() != null ? String.valueOf(p.getEntregadorId()) : "-";
                    System.out.printf("%-6d %-10d %-12s %-12s R$%.2f%n",
                            p.getId(), p.getRestauranteId(), entregador, p.getStatus(), p.calcularValorTotal());
                }
                System.out.println("=".repeat(80));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void listarPedidosPorStatus() {
        System.out.println("\nStatus disponíveis: PENDENTE, CONFIRMADO, PREPARANDO, PRONTO_ENTREGA, ENTREGUE, CANCELADO");
        System.out.print("Status: ");
        String status = sc.nextLine().toUpperCase();

        List<Pedido> pedidos = pedidoService.listarPedidosPorStatus(status);
        if (pedidos.isEmpty()) {
            System.out.println("❌ Nenhum pedido com status \"" + status + "\" encontrado.");
        } else {
            System.out.println("\n" + "=".repeat(80));
            System.out.printf("%-6s %-8s %-10s %-12s %-12s%n", "ID", "CLIENTE", "RESTAURANTE", "ENTREGADOR", "VALOR");
            System.out.println("=".repeat(80));
            for (Pedido p : pedidos) {
                String entregador = p.getEntregadorId() != null ? String.valueOf(p.getEntregadorId()) : "-";
                System.out.printf("%-6d %-8d %-10d %-12s R$%.2f%n",
                        p.getId(), p.getClienteId(), p.getRestauranteId(), entregador, p.calcularValorTotal());
            }
            System.out.println("=".repeat(80));
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