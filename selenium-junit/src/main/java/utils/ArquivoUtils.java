package utils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * File system helpers used by the tests.
 */
public final class ArquivoUtils {

    private ArquivoUtils() {
    }

    /**
     * Creates the directory when it does not exist and deletes every file inside it,
     * so each run starts from an empty folder (no leftovers from previous runs).
     *
     * @param diretorio directory to clean.
     */
    public static void limparDiretorio(Path diretorio) {
        try {
            Files.createDirectories(diretorio);
            try (Stream<Path> arquivos = Files.list(diretorio)) {
                for (Path arquivo : arquivos.filter(Files::isRegularFile).toList()) {
                    Files.delete(arquivo);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Nao foi possivel limpar o diretorio " + diretorio, e);
        }
    }
}
