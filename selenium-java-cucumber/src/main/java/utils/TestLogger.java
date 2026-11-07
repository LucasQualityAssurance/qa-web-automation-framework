package utils;

import io.qameta.allure.Allure;

import java.time.Duration;

/**
 * Pequeno utilitário para reportar o andamento do teste passo a passo, facilitando acompanhar
 * o que a automação está fazendo (não só o resultado final). Cada passo é impresso no
 * terminal e também registrado como um step do teste no relatório Allure.
 * <p>
 * Para que dê tempo de ver cada ação no navegador, {@link #step(String)} faz uma pausa depois
 * de registrar o passo (veja {@link ConfigReader#getPausaEntrePassos()}). Essa pausa serve só
 * para visualização: a sincronização dos testes continua sendo feita com esperas explícitas.
 */
public final class TestLogger {

    private TestLogger() {
    }

    /**
     * Imprime a mensagem do passo no terminal, com o prefixo "[STEP]" para fácil identificação,
     * a registra como um step no relatório Allure e faz a pausa de visualização configurada.
     *
     * @param mensagem descrição do passo que está sendo executado.
     */
    public static void step(String mensagem) {
        info(mensagem);
        pausar(ConfigReader.getPausaEntrePassos());
    }

    /**
     * Registra a mensagem como {@link #step(String)}, mas sem a pausa de visualização. Usado
     * nos passos que não mostram nenhuma ação na tela (início e fim do teste, nome do cenário,
     * preparação de dados).
     *
     * @param mensagem descrição do passo que está sendo executado.
     */
    public static void info(String mensagem) {
        System.out.println("[STEP] " + mensagem);
        Allure.step(mensagem);
    }

    /**
     * Faz só a pausa de visualização configurada, sem registrar nenhum passo. Usado pelo hook
     * {@code @AfterStep} do Cucumber, em que cada passo do Gherkin já é registrado pelo próprio
     * Cucumber e pelo plugin do Allure.
     */
    public static void pausarEntrePassos() {
        pausar(ConfigReader.getPausaEntrePassos());
    }

    private static void pausar(Duration pausa) {
        if (pausa.isZero() || pausa.isNegative()) {
            return;
        }
        try {
            Thread.sleep(pausa.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
