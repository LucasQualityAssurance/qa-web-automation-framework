package utils;

import io.qameta.allure.Allure;

/**
 * Small helper to report step-by-step progress of a test, making it easy to follow what
 * the automation is doing (not only the final result). Each step is printed to the
 * terminal and also recorded as a step of the test in the Allure report.
 */
public final class TestLogger {

    private TestLogger() {
    }

    /**
     * Prints a step message to the terminal, prefixed with "[STEP]" for easy identification,
     * and records it as a step in the Allure report.
     *
     * @param mensagem description of the step being executed.
     */
    public static void step(String mensagem) {
        System.out.println("[STEP] " + mensagem);
        Allure.step(mensagem);
    }
}
