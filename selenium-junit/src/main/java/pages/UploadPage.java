package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

/**
 * Represents the File Uploader page (https://the-internet.herokuapp.com/upload).
 */
public class UploadPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By fileInput = By.id("file-upload");
    private final By submitButton = By.id("file-submit");

    public UploadPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigates to the File Uploader page, waiting until the file input is visible.
     *
     * @return this page, to allow fluent chaining.
     */
    public UploadPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/upload");
        wait.until(ExpectedConditions.visibilityOfElementLocated(fileInput));
        return this;
    }

    /**
     * Chooses the file to upload by sending its absolute path directly to the
     * (hidden) native file input, since Selenium cannot interact with OS file dialogs.
     *
     * @param caminhoAbsolutoDoArquivo absolute path of the file to upload.
     * @return this page, to allow fluent chaining.
     */
    public UploadPage escolherArquivo(String caminhoAbsolutoDoArquivo) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fileInput)).sendKeys(caminhoAbsolutoDoArquivo);
        return this;
    }

    /**
     * Clicks the "Upload" button, submitting the chosen file.
     *
     * @return the {@link UploadResultPage} the user is redirected to.
     */
    public UploadResultPage carregar() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        return new UploadResultPage(driver);
    }
}
