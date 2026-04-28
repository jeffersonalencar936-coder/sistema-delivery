package model;

import dao.*;
import java.util.List;
import java.util.Scanner;

public class SistemaMain {

    static Scanner sc = new Scanner(System.in);

    static ClienteDAO clienteDAO = new ClienteDAO();
    static RestauranteDAO restauranteDAO = new RestauranteDAO();
    static EntregadorDAO entregadorDAO = new EntregadorDAO();

    public static void main(String[] args) {

        int op;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1- Cadastrar Cliente");
            System.out.println("2- Listar Clientes");
            System.out.println("3- Atualizar Cliente");
            System.out.println("4- Deletar Cliente");

            System.out.println("5- Cadastrar Restaurante");
            System.out.println("6- Listar Restaurantes");
            System.out.println("7- Atualizar Restaurante");
            System.out.println("8- Deletar Restaurante");

            System.out.println("9- Cadastrar Entregador");
            System.out.println("10- Listar Entregadores");
            System.out.println("11- Atualizar Entregador");
            System.out.println("12- Deletar Entregador");

            System.out.println("0- Sair");

            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> atualizarCliente();
                case 4 -> deletarCliente();

                case 5 -> cadastrarRestaurante();
                case 6 -> listarRestaurantes();
                case 7 -> atualizarRestaurante();
                case 8 -> deletarRestaurante();

                case 9 -> cadastrarEntregador();
                case 10 -> listarEntregadores();
                case 11 -> atualizarEntregador();
                case 12 -> deletarEntregador();

                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }

        } while (op != 0);
    }

    static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Telefone: ");
        String tel = sc.nextLine();

        System.out.print("Endereço: ");
        String end = sc.nextLine();

        clienteDAO.inserir(new Cliente(0, nome, tel, end));
        System.out.println("Cliente cadastrado!");
    }

    static void listarClientes() {
        List<Cliente> lista = clienteDAO.listar();

        if (lista.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
        } else {
            lista.forEach(c -> System.out.println(c.getInformacoes()));
        }
    }

    static void atualizarCliente() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Telefone: ");
        String tel = sc.nextLine();

        System.out.print("Endereço: ");
        String end = sc.nextLine();

        clienteDAO.atualizar(new Cliente(id, nome, tel, end));
    }

    static void deletarCliente() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        clienteDAO.deletar(id);
    }

    static void cadastrarRestaurante() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Endereço: ");
        String end = sc.nextLine();

        System.out.print("Avaliação: ");
        double av = sc.nextDouble();
        sc.nextLine();

        restauranteDAO.inserir(new Restaurante(0, nome, end, av));
        System.out.println("Restaurante cadastrado!");
    }

    static void listarRestaurantes() {
        restauranteDAO.listar().forEach(r -> System.out.println(r.getNome()));
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
    }

    static void deletarRestaurante() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        restauranteDAO.deletar(id);
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
        System.out.println("Entregador cadastrado!");
    }

    static void listarEntregadores() {
        entregadorDAO.listar().forEach(e -> System.out.println(e.getNome()));
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
    }

    static void deletarEntregador() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        entregadorDAO.deletar(id);
    }
}