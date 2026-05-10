package br.com.delivery.util;

/**
 * Interface que define comportamento de geração de relatórios.
 * Classes que implementam esta interface podem gerar dados para relatórios.
 */
public interface Relatorio {
    
    /**
     * Gera um relatório em formato de string.
     * @return string contendo o relatório formatado
     */
    String gerarRelatorio();
    
    /**
     * Exporta dados para um formato específico.
     * @param formato o formato desejado (ex: CSV, JSON, PDF)
     * @return string contendo os dados no formato especificado
     */
    String exportarDados(String formato);
}
