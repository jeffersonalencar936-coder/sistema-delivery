public class Entregador extends Usuario {
    private String statusDisponibilidade;
    private String localizacaoAtual;

    public Entregador(int id, String nome, String telefone, String statusDisponibilidade, String localizacaoAtual) {
        super(id, nome, telefone);
        setStatusDisponibilidade(statusDisponibilidade);
        setLocalizacaoAtual(localizacaoAtual);
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
    public String getInformacoes() {
        return super.getInformacoes() + ", Status: " + statusDisponibilidade + ", Localização: " + localizacaoAtual;
    }

    @Override
    public void exibirBoasVindas() {
        System.out.println("Bem-vindo(a), entregador(a) " + nome + "!");
        System.out.println("Seu status atual: " + statusDisponibilidade);
        System.out.println("Localização: " + localizacaoAtual);
    }

    @Override
    public String toString() {
        return "Entregador{id=" + id + ", nome='" + nome + '\'' + ", telefone='" + telefone + '\'' + ", statusDisponibilidade='" + statusDisponibilidade + '\'' + ", localizacaoAtual='" + localizacaoAtual + '\'' + '}';
    }
}
