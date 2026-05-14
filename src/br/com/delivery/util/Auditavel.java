package br.com.delivery.util;


public interface Auditavel {
    
    
    void registrarLog(String acao);
    
    
    String obterHistorico();
}
