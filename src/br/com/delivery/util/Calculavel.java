package br.com.delivery.util;

/**
 * Interface que define comportamento de cálculo.
 * Classes que implementam esta interface podem calcular valores.
 */
public interface Calculavel {
    
    /**
     * Calcula o subtotal do objeto.
     * @return o valor do subtotal
     */
    double calcularSubtotal();
    
    /**
     * Calcula o desconto aplicável.
     * @return o valor do desconto
     */
    double calcularDesconto();
    
    /**
     * Calcula o valor total com todos os cálculos aplicados.
     * @return o valor total final
     */
    double calcularValorTotal();
}
