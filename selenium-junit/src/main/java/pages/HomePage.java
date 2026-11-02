package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the home page of https://the-internet.herokuapp.com/.
 * Every test must land on this page before exercising any other flow, since all
 * the available scenarios are reachable from here. It is the pre-requisite
 * starting point for any tester running the automation.
 * <p>
 * Example links are located by their href, which is more stable than the visible text.
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
     * Navigates to the home page, waiting until the heading is visible so every
     * automation starts from a known, fully-loaded state.
     *
     * @return this page, to allow fluent chaining.
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
