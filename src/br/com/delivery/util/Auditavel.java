package br.com.delivery.util;

/**
 * Interface que define comportamento de auditoria.
 * Classes que implementam esta interface podem registrar logs de ações.
 */
public interface Auditavel {
    
    /**
     * Registra uma ação no histórico de auditoria.
     * @param acao a descrição da ação a ser registrada
     */
    void registrarLog(String acao);
    
    /**
     * Obtém o histórico completo de auditoria.
     * @return string contendo o histórico de auditoria
     */
    String obterHistorico();
}
