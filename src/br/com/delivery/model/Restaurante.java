package br.com.delivery.model;

import br.com.delivery.util.Relatorio;

public class Restaurante implements Relatorio {

    private int id;
    private String nome;
    private String endereco;
    private double avaliacao;

    public Restaurante(int id, String nome, String endereco, double avaliacao) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.avaliacao = avaliacao;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public double getAvaliacao() { return avaliacao; }

    @Override
    public String gerarRelatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== RELATÓRIO DO RESTAURANTE ==========\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Endereço: ").append(endereco).append("\n");
        sb.append("Avaliação: ").append(String.format("%.1f", avaliacao)).append(" ⭐\n");
        sb.append("=============================================\n");
        return sb.toString();
    }

    @Override
    public String exportarDados(String formato) {
        if (formato == null || formato.isEmpty()) {
            throw new IllegalArgumentException("Formato inválido.");
        }

        switch (formato.toLowerCase()) {
            case "csv":
                return exportarCSV();
            case "json":
                return exportarJSON();
            case "xml":
                return exportarXML();
            default:
                throw new IllegalArgumentException("Formato não suportado: " + formato);
        }
    }

    private String exportarCSV() {
        return "ID,Nome,Endereço,Avaliação\n" +
               id + "," + nome + "," + endereco + "," + avaliacao;
    }

    private String exportarJSON() {
        return "{\n" +
               "  \"id\": " + id + ",\n" +
               "  \"nome\": \"" + nome + "\",\n" +
               "  \"endereco\": \"" + endereco + "\",\n" +
               "  \"avaliacao\": " + avaliacao + "\n" +
               "}";
    }

    private String exportarXML() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
               "<restaurante>\n" +
               "  <id>" + id + "</id>\n" +
               "  <nome>" + nome + "</nome>\n" +
               "  <endereco>" + endereco + "</endereco>\n" +
               "  <avaliacao>" + avaliacao + "</avaliacao>\n" +
               "</restaurante>";
    }
}
