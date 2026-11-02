package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.DownloadPage;
import pages.UploadPage;
import pages.UploadResultPage;
import utils.ArquivoUtils;
import utils.ConfigReader;
import utils.TestLogger;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Validates the file download + upload flow at https://the-internet.herokuapp.com/.
 * Starting from the home page, downloads a file (saving it into the project's
 * {@code Files} folder, see {@link ConfigReader#getDiretorioDownload()}), returns to the
 * home page, opens the File Uploader example, uploads that same file from the project
 * folder and confirms the "File Uploaded!" confirmation heading.
 * <p>
 * The downloaded file is one of the files the site always ships (never removed nor
 * overwritten by other users' uploads), which keeps the scenario deterministic.
 */
public class DownloadUploadArquivoTest extends BaseTest {

    private static final String ARQUIVO_DO_SITE = "some-file.txt";

    @BeforeEach
    public void limparPastaDeDownload() {
        TestLogger.step("Limpando a pasta de download: " + ConfigReader.getDiretorioDownload());
        ArquivoUtils.limparDiretorio(ConfigReader.getDiretorioDownload());
    }

    @Test
    @DisplayName("Deve baixar um arquivo e depois realizar o upload dele com sucesso")
    public void deveBaixarArquivoEFazerUploadComSucesso() throws Exception {
        TestLogger.step("Abrindo a home page (/)");
        homePage.abrir();

        TestLogger.step("Clicando no link 'File Download'");
        DownloadPage downloadPage = homePage.acessarDownloadDeArquivos();

        TestLogger.step("Baixando o arquivo '" + ARQUIVO_DO_SITE + "'");
        Path arquivoBaixado = downloadPage.baixarArquivo(ARQUIVO_DO_SITE);
        TestLogger.step("Arquivo baixado com sucesso: " + arquivoBaixado);

        TestLogger.step("Validando que o arquivo baixado nao esta vazio");
        assertTrue(Files.size(arquivoBaixado) > 0, "O arquivo baixado esta vazio: " + arquivoBaixado);

        TestLogger.step("Retornando para a home page (/)");
        homePage.abrir();

        TestLogger.step("Clicando no link 'File Upload'");
        UploadPage uploadPage = homePage.acessarUploadDeArquivos();

        TestLogger.step("Escolhendo o arquivo baixado para upload: " + arquivoBaixado);
        uploadPage.escolherArquivo(arquivoBaixado);

        TestLogger.step("Clicando em 'Upload'");
        UploadResultPage uploadResultPage = uploadPage.carregar();

        TestLogger.step("Validando mensagem de confirmacao");
        assertEquals("File Uploaded!", uploadResultPage.obterTitulo());

        TestLogger.step("Validando que o nome do arquivo exibido confere com o que foi enviado");
        assertEquals(ARQUIVO_DO_SITE, uploadResultPage.obterNomeDoArquivoEnviado());

        TestLogger.step("Upload finalizado com sucesso");
    }
}
