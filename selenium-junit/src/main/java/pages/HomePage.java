package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representa a página inicial de https://the-internet.herokuapp.com/.
 * Todo teste deve passar por esta página antes de exercitar qualquer outro fluxo, já que
 * todos os cenários disponíveis são acessados a partir daqui. É o ponto de partida
 * obrigatório para qualquer testador que execute a automação.
 * <p>
 * Os links dos exemplos são localizados pelo href, que é mais estável que o texto visível.
 */
public class HomePage extends BasePage {

    private final By titulo = By.tagName("h1");
    private final By linkFormularioGrande = By.cssSelector("a[href='/large_form']");
    private final By linkDownloadDeArquivos = By.cssSelector("a[href='/download']");
    private final By linkUploadDeArquivos = By.cssSelector("a[href='/upload']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega para a página inicial, aguardando até que o título esteja visível, para que toda
     * automação comece de um estado conhecido e totalmente carregado.
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public HomePage abrir() {
        navegarPara("/", titulo);
        return this;
    }

    public String obterTitulo() {
        return obterTexto(titulo);
    }

    public LargeFormPage acessarFormularioGrande() {
        clicar(linkFormularioGrande);
        return new LargeFormPage(driver);
    }

    public DownloadPage acessarDownloadDeArquivos() {
        clicar(linkDownloadDeArquivos);
        return new DownloadPage(driver);
    }

    public UploadPage acessarUploadDeArquivos() {
        clicar(linkUploadDeArquivos);
        return new UploadPage(driver);
    }
}
