package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

/**
 * Representa a página File Uploader (https://the-internet.herokuapp.com/upload).
 */
public class UploadPage extends BasePage {

    private final By campoArquivo = By.id("file-upload");
    private final By botaoCarregar = By.id("file-submit");

    public UploadPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega para a página File Uploader, aguardando até que o campo de arquivo esteja visível.
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public UploadPage abrir() {
        navegarPara("/upload", campoArquivo);
        return this;
    }

    /**
     * Escolhe o arquivo a enviar mandando seu caminho absoluto direto para o campo de arquivo
     * nativo, já que o Selenium não consegue interagir com as janelas de arquivo do sistema.
     * O navegador então lê o arquivo desta máquina (neste projeto, da pasta de downloads).
     *
     * @param arquivo caminho do arquivo a enviar.
     * @return esta página, para permitir encadeamento fluente.
     */
    public UploadPage escolherArquivo(Path arquivo) {
        escrever(campoArquivo, arquivo.toAbsolutePath().toString());
        return this;
    }

    /**
     * Clica no botão "Upload", enviando o arquivo escolhido.
     *
     * @return a {@link UploadResultPage} para a qual o usuário é redirecionado.
     */
    public UploadResultPage carregar() {
        clicar(botaoCarregar);
        return new UploadResultPage(driver);
    }
}
