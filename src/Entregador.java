public class Entregador {
    private int id;
    private String nome;
    private String telefone;
    private String statusDisponibilidade;
    private String localizacaoAtual;

    public Entregador(int id, String nome, String telefone, String statusDisponibilidade, String localizacaoAtual) {
        setId(id);
        setNome(nome);
        setTelefone(telefone);
        setStatusDisponibilidade(statusDisponibilidade);
        setLocalizacaoAtual(localizacaoAtual);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do entregador deve ser maior que zero.");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do entregador não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do entregador não pode ser vazio.");
        }
        this.telefone = telefone.trim();
    }

    public String getStatusDisponibilidade() {
        return statusDisponibilidade;
    }

    public void setStatusDisponibilidade(String statusDisponibilidade) {
        if (statusDisponibilidade == null || statusDisponibilidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Status de disponibilidade não pode ser vazio.");
        }
        String status = statusDisponibilidade.trim().toLowerCase();
        if (!status.equals("disponível") && !status.equals("em entrega") && !status.equals("indisponível")) {
            throw new IllegalArgumentException("Status de disponibilidade inválido. Use 'disponível', 'em entrega' ou 'indisponível'.");
        }
        this.statusDisponibilidade = statusDisponibilidade.trim();
    }

    public String getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public void setLocalizacaoAtual(String localizacaoAtual) {
        if (localizacaoAtual == null || localizacaoAtual.trim().isEmpty()) {
            throw new IllegalArgumentException("Localização atual não pode ser vazia.");
        }
        this.localizacaoAtual = localizacaoAtual.trim();
    }

    @Override
    public String toString() {
        return "Entregador{id=" + id + ", nome='" + nome + '\'' + ", telefone='" + telefone + '\'' + ", statusDisponibilidade='" + statusDisponibilidade + '\'' + ", localizacaoAtual='" + localizacaoAtual + '\'' + '}';
    }
}
