package utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Grava o arquivo {@code environment.properties} lido pelo Allure, para que a visão geral do
 * relatório mostre onde e como os testes rodaram (site sob teste, navegador, modo headless, versão do Java).
 */
public final class AllureAmbiente {

    private AllureAmbiente() {
    }

    public static void registrar() {
        Path diretorioResultados = Path.of(System.getProperty("allure.results.directory", "target/allure-results"));

        Properties ambiente = new Properties();
        ambiente.setProperty("URL", ConfigReader.getBaseUrl());
        ambiente.setProperty("Navegador", "Chrome");
        ambiente.setProperty("Headless", String.valueOf(ConfigReader.isHeadless()));
        ambiente.setProperty("Java", System.getProperty("java.version"));
        ambiente.setProperty("Sistema", System.getProperty("os.name"));

        try {
            Files.createDirectories(diretorioResultados);
            try (OutputStream saida = Files.newOutputStream(diretorioResultados.resolve("environment.properties"))) {
                ambiente.store(saida, null);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Nao foi possivel gravar o ambiente do Allure.", e);
        }
    }
}
