package br.com.delivery.service;

import br.com.delivery.model.Cliente;
import br.com.delivery.dao.ClienteDAO;
import java.util.List;

/**
 * Serviço de lógica de negócio para operações com clientes.
 */
public class ClienteService {

    private ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    /**
     * Cria um novo cliente após validações.
     */
    public void criarCliente(String nome, String telefone, String endereco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser vazio.");
        }

        Cliente cliente = new Cliente(0, nome, telefone, endereco);
        clienteDAO.inserir(cliente);
        System.out.println("✓ Cliente " + nome + " cadastrado com sucesso.");
    }

    /**
     * Obtém um cliente pelo ID.
     */
    public Cliente obterCliente(int id) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        return cliente;
    }

    /**
     * Lista todos os clientes.
     */
    public List<Cliente> listarTodos() {
        return clienteDAO.listar();
    }

    /**
     * Atualiza dados de um cliente.
     */
    public void atualizarCliente(int id, String nome, String telefone, String endereco) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        Cliente clienteAtualizado = new Cliente(id, nome, telefone, endereco);
        clienteDAO.atualizar(clienteAtualizado);
        System.out.println("✓ Cliente atualizado com sucesso.");
    }

    /**
     * Deleta um cliente.
     */
    public void deletarCliente(int id) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        clienteDAO.deletar(id);
        System.out.println("✓ Cliente deletado com sucesso.");
    }
}
