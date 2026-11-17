package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representa a página de resultado exibida após o envio do formulário File Uploader
 * (https://the-internet.herokuapp.com/upload).
 */
public class UploadResultPage extends BasePage {

    private final By titulo = By.tagName("h3");
    private final By arquivosEnviados = By.id("uploaded-files");

    public UploadResultPage(WebDriver driver) {
        super(driver);
    }

    public String obterTitulo() {
        return obterTexto(titulo);
    }

    public String obterNomeDoArquivoEnviado() {
        return obterTexto(arquivosEnviados);
    }
}
