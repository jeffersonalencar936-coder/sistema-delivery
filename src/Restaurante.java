public class Restaurante {
    private int id;
    private String nome;
    private String endereco;
    private double avaliacao;

    public Restaurante(int id, String nome, String endereco, double avaliacao) {
        setId(id);
        setNome(nome);
        setEndereco(endereco);
        setAvaliacao(avaliacao);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do restaurante deve ser maior que zero.");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do restaurante não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço do restaurante não pode ser vazio.");
        }
        this.endereco = endereco.trim();
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        if (avaliacao < 0.0 || avaliacao > 5.0) {
            throw new IllegalArgumentException("Avaliação deve estar entre 0 e 5.");
        }
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return "Restaurante{id=" + id + ", nome='" + nome + '\'' + ", endereco='" + endereco + '\'' + ", avaliacao=" + avaliacao + '}';
    }
}
