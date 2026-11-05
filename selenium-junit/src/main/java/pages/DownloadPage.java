package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

/**
 * Representa a página File Downloader (https://the-internet.herokuapp.com/download),
 * que lista todos os arquivos disponíveis para download.
 * <p>
 * Além dos arquivos enviados por qualquer pessoa, o site sempre traz um conjunto fixo de arquivos
 * que nunca são removidos nem sobrescritos (sample.pdf, sample.csv, sample.json, some-file.txt e
 * textfile.txt), então os testes devem baixar um deles para continuarem determinísticos.
 */
public class DownloadPage extends BasePage {

    private final By listaDeArquivos = By.cssSelector("#content a");

    public DownloadPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega para a página File Downloader, aguardando até que a lista de arquivos esteja visível.
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public DownloadPage abrir() {
        navegarPara("/download", listaDeArquivos);
        return this;
    }

    /**
     * Clica no link do arquivo informado e aguarda o Chrome terminar de salvá-lo em
     * {@link ConfigReader#getDiretorioDownload()} (nenhuma janela de salvar é exibida).
     *
     * @param nomeArquivo nome do arquivo, exatamente como aparece na página (ex.: "some-file.txt").
     * @return o caminho do arquivo baixado.
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
