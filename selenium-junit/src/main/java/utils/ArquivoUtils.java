package utils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Utilitários de sistema de arquivos usados pelos testes.
 */
public final class ArquivoUtils {

    private ArquivoUtils() {
    }

    /**
     * Cria o diretório quando ele não existe e apaga todos os arquivos dentro dele, para que
     * cada execução comece com a pasta vazia (sem sobras de execuções anteriores).
     *
     * @param diretorio diretório a limpar.
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
