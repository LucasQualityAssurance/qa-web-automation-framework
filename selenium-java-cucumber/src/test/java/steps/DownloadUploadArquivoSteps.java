package steps;

import hooks.Hooks;
import io.cucumber.java.Before;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.DownloadPage;
import pages.UploadPage;
import pages.UploadResultPage;
import utils.ArquivoUtils;
import utils.ConfigReader;
import utils.TestLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Steps da funcionalidade de download e upload ({@code features/download_upload_arquivo.feature}).
 * O arquivo é salvo na pasta {@code Files} do repositório (veja {@link ConfigReader#getDiretorioDownload()})
 * e enviado a partir dela.
 */
public class DownloadUploadArquivoSteps {

    private Path arquivoBaixado;
    private UploadResultPage uploadResultPage;

    /**
     * Esvazia a pasta de downloads antes dos cenários com a tag {@code @arquivos}, para que
     * nenhuma sobra de execuções anteriores interfira na validação do download.
     */
    @Before("@arquivos")
    public void limparPastaDeDownload() {
        TestLogger.info("Limpando a pasta de download: " + ConfigReader.getDiretorioDownload());
        ArquivoUtils.limparDiretorio(ConfigReader.getDiretorioDownload());
    }

    @Quando("baixo o arquivo {string}")
    public void baixoOArquivo(String nomeArquivo) {
        arquivoBaixado = new DownloadPage(Hooks.getDriver()).baixarArquivo(nomeArquivo);
        TestLogger.info("Arquivo baixado: " + arquivoBaixado);
    }

    @Entao("o arquivo deve ser salvo na pasta de downloads com conteúdo")
    public void oArquivoDeveSerSalvoNaPastaDeDownloadsComConteudo() throws IOException {
        assertTrue(Files.size(arquivoBaixado) > 0, "O arquivo baixado esta vazio: " + arquivoBaixado);
    }

    @Quando("envio o arquivo baixado pelo upload")
    public void envioOArquivoBaixadoPeloUpload() {
        uploadResultPage = new UploadPage(Hooks.getDriver())
                .escolherArquivo(arquivoBaixado)
                .carregar();
    }

    @Entao("devo ver a confirmação de upload {string}")
    public void devoVerAConfirmacaoDeUpload(String tituloEsperado) {
        assertEquals(tituloEsperado, uploadResultPage.obterTitulo());
    }

    @E("o nome do arquivo enviado deve ser o do arquivo baixado")
    public void oNomeDoArquivoEnviadoDeveSerODoArquivoBaixado() {
        assertEquals(arquivoBaixado.getFileName().toString(), uploadResultPage.obterNomeDoArquivoEnviado());
    }
}
