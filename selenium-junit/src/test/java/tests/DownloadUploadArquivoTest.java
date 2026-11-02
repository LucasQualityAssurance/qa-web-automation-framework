package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.DownloadPage;
import pages.UploadResultPage;
import utils.ConfigReader;
import utils.TestLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Validates the file download + upload flow at https://the-internet.herokuapp.com/.
 * Starting from the home page, downloads the first available file (saving it into
 * {@link ConfigReader#getDownloadDirectory()}), returns to the home page, opens the
 * File Uploader example, uploads the file that was just downloaded and confirms the
 * "Arquivo carregado!" (translated) confirmation message.
 */
public class DownloadUploadArquivoTest extends BaseTest {

    @Test
    @DisplayName("Deve baixar um arquivo e depois realizar o upload dele com sucesso")
    public void deveBaixarArquivoEFazerUploadComSucesso() {
        TestLogger.step("Abrindo a home page (/)");
        homePage.open();

        TestLogger.step("Clicando no link 'File Download'");
        DownloadPage downloadPage = homePage.goToFileDownload();

        TestLogger.step("Baixando o primeiro arquivo da lista");
        String nomeArquivo = downloadPage.baixarPrimeiroArquivo();
        TestLogger.step("Arquivo baixado com sucesso: " + nomeArquivo);

        TestLogger.step("Retornando para a home page (/)");
        homePage.open();

        TestLogger.step("Clicando no link 'File Upload'");
        var uploadPage = homePage.goToFileUpload();

        String caminhoCompleto = ConfigReader.getDownloadDirectory() + "\\" + nomeArquivo;
        TestLogger.step("Escolhendo o arquivo baixado para upload: " + caminhoCompleto);
        uploadPage.escolherArquivo(caminhoCompleto);

        TestLogger.step("Clicando em 'Carregar'");
        UploadResultPage uploadResultPage = uploadPage.carregar();

        TestLogger.step("Validando mensagem de confirmacao (PT se a traducao do Chrome ocorrer a tempo, senao EN)");
        String mensagemConfirmacao = uploadResultPage.getTranslatedHeadingText();
        TestLogger.step("Mensagem capturada: " + mensagemConfirmacao);
        boolean mensagemValida = mensagemConfirmacao.toLowerCase().contains("arquivo")
                || mensagemConfirmacao.equals("File Uploaded!");
        assertTrue(mensagemValida);

        TestLogger.step("Validando que o nome do arquivo exibido confere com o que foi enviado");
        assertEquals(nomeArquivo, uploadResultPage.getUploadedFileName());

        TestLogger.step("Upload finalizado com sucesso");
    }
}
