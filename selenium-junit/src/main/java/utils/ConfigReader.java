package utils;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Centralizes project-wide configuration values, such as the base URL under test,
 * the downloads directory and the default wait timeouts.
 */
public final class ConfigReader {

    private static final String BASE_URL = "https://the-internet.herokuapp.com";
    private static final String NOME_PASTA_DOWNLOAD = "Files";

    private static final Duration TEMPO_ESPERA_PADRAO = Duration.ofSeconds(10);
    private static final Duration TEMPO_ESPERA_DOWNLOAD = Duration.ofSeconds(20);

    private ConfigReader() {
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * Directory where Chrome is configured to automatically save downloaded files: the
     * {@code Files} folder at the repository root. It is resolved from the location of the
     * compiled classes, so it works on any machine and regardless of the working directory
     * the tests are started from (IntelliJ or Maven).
     * <p>
     * Can be overridden with {@code -DdiretorioDownload=<path>}.
     *
     * @return the absolute path of the downloads directory.
     */
    public static Path getDiretorioDownload() {
        String sobrescrito = System.getProperty("diretorioDownload");
        if (sobrescrito != null && !sobrescrito.isBlank()) {
            return Path.of(sobrescrito).toAbsolutePath();
        }
        return getRaizDoRepositorio().resolve(NOME_PASTA_DOWNLOAD);
    }

    /**
     * Whether Chrome should run without opening a window. Enabled with {@code -Dheadless=true}.
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }

    public static Duration getTempoEsperaPadrao() {
        return TEMPO_ESPERA_PADRAO;
    }

    public static Duration getTempoEsperaDownload() {
        return TEMPO_ESPERA_DOWNLOAD;
    }

    /**
     * Resolves the repository root from the compiled classes location
     * ({@code <repo>/selenium-junit/target/classes}).
     */
    private static Path getRaizDoRepositorio() {
        try {
            Path pastaClasses = Path.of(ConfigReader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return pastaClasses.getParent().getParent().getParent();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Nao foi possivel localizar a raiz do repositorio.", e);
        }
    }
}
