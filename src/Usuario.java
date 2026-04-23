public class Usuario {
    protected int id;
    protected String nome;
    protected String telefone;

    public Usuario(int id, String nome, String telefone) {
        setId(id);
        setNome(nome);
        setTelefone(telefone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID deve ser maior que zero.");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }
        this.telefone = telefone.trim();
    }

    public String getInformacoes() {
        return "ID: " + id + ", Nome: " + nome + ", Telefone: " + telefone;
    }

    public void exibirBoasVindas() {
        System.out.println("Bem-vindo ao Sistema de Delivery, " + nome + "!");
    }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nome='" + nome + '\'' + ", telefone='" + telefone + '\'' + '}';
    }
}