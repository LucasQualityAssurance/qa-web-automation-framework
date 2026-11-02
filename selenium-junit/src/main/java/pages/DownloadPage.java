package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

/**
 * Represents the File Downloader page (https://the-internet.herokuapp.com/download),
 * which lists every file available for download.
 * <p>
 * Besides files uploaded by anyone, the site always ships a fixed set of files that are
 * never removed nor overwritten (sample.pdf, sample.csv, sample.json, some-file.txt and
 * textfile.txt), so tests should download one of those to stay deterministic.
 */
public class DownloadPage extends BasePage {

    private final By listaDeArquivos = By.cssSelector("#content a");

    public DownloadPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to the File Downloader page, waiting until the file list is visible.
     *
     * @return this page, to allow fluent chaining.
     */
    public DownloadPage abrir() {
        navegarPara("/download", listaDeArquivos);
        return this;
    }

    /**
     * Clicks the link of the given file and waits until Chrome finishes saving it into
     * {@link ConfigReader#getDiretorioDownload()} (no save dialog is shown).
     *
     * @param nomeArquivo name of the file, exactly as listed on the page (e.g. "some-file.txt").
     * @return the path of the downloaded file.
     */
    public Path baixarArquivo(String nomeArquivo) {
        aguardarVisivel(listaDeArquivos);
        clicar(By.cssSelector("#content a[href='download/" + nomeArquivo + "']"));
        return aguardarDownloadConcluido(nomeArquivo);
    }

    private Path aguardarDownloadConcluido(String nomeArquivo) {
        Path arquivoFinal = ConfigReader.getDiretorioDownload().resolve(nomeArquivo);
        Path arquivoParcial = ConfigReader.getDiretorioDownload().resolve(nomeArquivo + ".crdownload");

        Instant limite = Instant.now().plus(ConfigReader.getTempoEsperaDownload());
        while (Instant.now().isBefore(limite)) {
            if (Files.exists(arquivoFinal) && !Files.exists(arquivoParcial)) {
                return arquivoFinal;
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }
        throw new IllegalStateException("Download do arquivo '" + nomeArquivo + "' nao foi concluido a tempo.");
    }
}
