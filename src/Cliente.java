public class Cliente {
    private int id;
    private String nome;
    private String telefone;
    private String endereco;

    public Cliente(int id, String nome, String telefone, String endereco) {
        setId(id);
        setNome(nome);
        setTelefone(telefone);
        setEndereco(endereco);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do cliente deve ser maior que zero.");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do cliente não pode ser vazio.");
        }
        this.telefone = telefone.trim();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço do cliente não pode ser vazio.");
        }
        this.endereco = endereco.trim();
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nome='" + nome + '\'' + ", telefone='" + telefone + '\'' + ", endereco='" + endereco + '\'' + '}';
    }
}
