package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

/**
 * Represents the File Uploader page (https://the-internet.herokuapp.com/upload).
 */
public class UploadPage extends BasePage {

    private final By campoArquivo = By.id("file-upload");
    private final By botaoCarregar = By.id("file-submit");

    public UploadPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to the File Uploader page, waiting until the file input is visible.
     *
     * @return this page, to allow fluent chaining.
     */
    public UploadPage abrir() {
        navegarPara("/upload", campoArquivo);
        return this;
    }

    /**
     * Chooses the file to upload by sending its absolute path directly to the native
     * file input, since Selenium cannot interact with OS file dialogs. The browser then
     * reads the file from this machine (in this project, from the downloads folder).
     *
     * @param arquivo path of the file to upload.
     * @return this page, to allow fluent chaining.
     */
    public UploadPage escolherArquivo(Path arquivo) {
        escrever(campoArquivo, arquivo.toAbsolutePath().toString());
        return this;
    }

    /**
     * Clicks the "Upload" button, submitting the chosen file.
     *
     * @return the {@link UploadResultPage} the user is redirected to.
     */
    public UploadResultPage carregar() {
        clicar(botaoCarregar);
        return new UploadResultPage(driver);
    }
}
