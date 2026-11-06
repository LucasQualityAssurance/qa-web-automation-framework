package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representa a página "New Window" (https://the-internet.herokuapp.com/windows/new), aberta
 * em uma nova aba a partir da {@link MultipleWindowsPage}.
 */
public class NewWindowPage extends BasePage {

    private final By titulo = By.tagName("h3");
    private final String janelaOriginal;

    /**
     * @param driver         driver já com o foco na nova aba.
     * @param janelaOriginal identificador da janela que abriu esta aba, para onde o foco volta
     *                       em {@link #fecharEVoltarParaJanelaOriginal()}.
     */
    public NewWindowPage(WebDriver driver, String janelaOriginal) {
        super(driver);
        this.janelaOriginal = janelaOriginal;
    }

    public String obterTitulo() {
        return obterTexto(titulo);
    }

    /**
     * Título da aba (conteúdo da tag {@code <title>}).
     */
    public String obterTituloDaAba() {
        return driver.getTitle();
    }

    /**
     * Fecha esta aba e devolve o foco do Selenium para a janela original.
     *
     * @return a página da janela original.
     */
    public MultipleWindowsPage fecharEVoltarParaJanelaOriginal() {
        driver.close();
        driver.switchTo().window(janelaOriginal);
        return new MultipleWindowsPage(driver);
    }
}
