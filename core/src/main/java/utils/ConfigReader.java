package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Centraliza as configurações do projeto, como a URL base sob teste, o diretório de
 * downloads e os tempos de espera padrão.
 */
public final class ConfigReader {

    private static final String BASE_URL = "https://the-internet.herokuapp.com";
    private static final String NOME_PASTA_DOWNLOAD = "Files";

    private static final Duration TEMPO_ESPERA_PADRAO = Duration.ofSeconds(10);
    private static final Duration TEMPO_ESPERA_DOWNLOAD = Duration.ofSeconds(20);
    private static final long PAUSA_ENTRE_PASSOS_PADRAO_SEGUNDOS = 2;

    private ConfigReader() {
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * Diretório onde o Chrome está configurado para salvar automaticamente os arquivos baixados:
     * a pasta {@code Files} na raiz do repositório.
     * <p>
     * Pode ser sobrescrito com {@code -DdiretorioDownload=<caminho>}.
     *
     * @return o caminho absoluto do diretório de downloads.
     */
    public static Path getDiretorioDownload() {
        String sobrescrito = System.getProperty("diretorioDownload");
        if (sobrescrito != null && !sobrescrito.isBlank()) {
            return Path.of(sobrescrito).toAbsolutePath();
        }
        return getRaizDoRepositorio().resolve(NOME_PASTA_DOWNLOAD);
    }

    /**
     * Indica se o Chrome deve rodar sem abrir janela. Ativado com {@code -Dheadless=true}.
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }

    /**
     * Pausa feita depois de cada passo do teste, para que dê tempo de ver a ação no navegador.
     * O padrão é 2 segundos com a janela visível e nenhuma pausa no modo headless (em que não
     * há nada para ver). Pode ser alterada com {@code -DpausaEntrePassos=<segundos>}; use
     * {@code -DpausaEntrePassos=0} para rodar na velocidade normal.
     *
     * @return a duração da pausa entre os passos.
     */
    public static Duration getPausaEntrePassos() {
        long padrao = isHeadless() ? 0 : PAUSA_ENTRE_PASSOS_PADRAO_SEGUNDOS;
        return Duration.ofSeconds(Long.getLong("pausaEntrePassos", padrao));
    }

    public static Duration getTempoEsperaPadrao() {
        return TEMPO_ESPERA_PADRAO;
    }

    public static Duration getTempoEsperaDownload() {
        return TEMPO_ESPERA_DOWNLOAD;
    }

    /**
     * Resolve a raiz do repositório subindo a partir do diretório de trabalho até encontrar a
     * pasta que contém o módulo {@code core}. Funciona tanto rodando o Maven na raiz quanto
     * dentro da pasta de um módulo, e também pelo IntelliJ.
     */
    private static Path getRaizDoRepositorio() {
        Path atual = Path.of(System.getProperty("user.dir")).toAbsolutePath();
        for (Path p = atual; p != null; p = p.getParent()) {
            if (Files.isRegularFile(p.resolve("core").resolve("pom.xml"))) {
                return p;
            }
        }
        return atual;
    }
}
