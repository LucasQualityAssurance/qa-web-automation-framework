package utils;

import io.qameta.allure.Allure;

/**
 * Pequeno utilitário para reportar o andamento do teste passo a passo, facilitando acompanhar
 * o que a automação está fazendo (não só o resultado final). Cada passo é impresso no
 * terminal e também registrado como um step do teste no relatório Allure.
 */
public final class TestLogger {

    private TestLogger() {
    }

    /**
     * Imprime a mensagem do passo no terminal, com o prefixo "[STEP]" para fácil identificação,
     * e a registra como um step no relatório Allure.
     *
     * @param mensagem descrição do passo que está sendo executado.
     */
    public static void step(String mensagem) {
        System.out.println("[STEP] " + mensagem);
        Allure.step(mensagem);
    }
}
