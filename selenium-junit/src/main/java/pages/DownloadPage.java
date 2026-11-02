package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.io.File;
import java.time.Duration;
import java.util.List;

/**
 * Represents the File Downloader page (https://the-internet.herokuapp.com/download),
 * which lists every file available for download.
 */
public class DownloadPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By fileLinks = By.cssSelector("#content a");

    public DownloadPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigates to the File Downloader page, waiting until the file list is visible.
     *
     * @return this page, to allow fluent chaining.
     */
    public DownloadPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/download");
        wait.until(ExpectedConditions.visibilityOfElementLocated(fileLinks));
        return this;
    }

    /**
     * Clicks the first available file link and waits until the download finishes
     * (Chrome is configured to save it directly into {@link ConfigReader#getDownloadDirectory()}
     * without a save dialog).
     *
     * @return the name of the downloaded file.
     */
    public String baixarPrimeiroArquivo() {
        List<WebElement> links = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(fileLinks));
        WebElement primeiroLink = links.get(0);

        String href = primeiroLink.getAttribute("href");
        String nomeArquivo = href.substring(href.lastIndexOf('/') + 1);

        primeiroLink.click();
        aguardarDownloadConcluido(nomeArquivo);

        return nomeArquivo;
    }

    private void aguardarDownloadConcluido(String nomeArquivo) {
        File arquivoFinal = new File(ConfigReader.getDownloadDirectory(), nomeArquivo);
        File arquivoParcial = new File(arquivoFinal.getPath() + ".crdownload");

        long limiteDeTempo = System.currentTimeMillis() + Duration.ofSeconds(20).toMillis();
        while (System.currentTimeMillis() < limiteDeTempo) {
            if (arquivoFinal.exists() && !arquivoParcial.exists()) {
                return;
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        throw new IllegalStateException("Download do arquivo '" + nomeArquivo + "' não foi concluído a tempo.");
    }
}
