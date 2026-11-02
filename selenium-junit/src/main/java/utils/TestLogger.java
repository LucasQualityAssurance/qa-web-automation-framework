package utils;

/**
 * Small helper to print step-by-step progress of a test to the terminal, making it
 * easy to follow what the automation is doing while it runs (not only the final result).
 */
public final class TestLogger {

    private TestLogger() {
    }

    /**
     * Prints a step message to the terminal, prefixed with "[STEP]" for easy identification.
     *
     * @param mensagem description of the step being executed.
     */
    public static void step(String mensagem) {
        System.out.println("[STEP] " + mensagem);
    }
}
