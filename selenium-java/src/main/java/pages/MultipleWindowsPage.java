package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Representa a página Multiple Windows (https://the-internet.herokuapp.com/windows), cujo
 * link "Click Here" abre a página "New Window" em uma nova aba do navegador.
 */
public class MultipleWindowsPage extends BasePage {

    private final By titulo = By.tagName("h3");
    private final By linkNovaJanela = By.cssSelector("a[href='/windows/new']");

    public MultipleWindowsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega para a página Multiple Windows, aguardando até que o título esteja visível.
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public MultipleWindowsPage abrir() {
        navegarPara("/windows", titulo);
        return this;
    }

    public String obterTitulo() {
        return obterTexto(titulo);
    }

    /**
     * Quantidade de janelas e abas abertas pelo navegador neste momento.
     */
    public int obterQuantidadeDeJanelas() {
        return driver.getWindowHandles().size();
    }

    /**
     * Clica em "Click Here", aguarda a nova aba abrir e troca o foco do Selenium para ela.
     * A janela original é guardada para que a nova página possa voltar para ela.
     *
     * @return a página aberta na nova aba.
     */
    public NewWindowPage abrirNovaJanela() {
        String janelaOriginal = driver.getWindowHandle();
        int janelasAntes = obterQuantidadeDeJanelas();

        clicar(linkNovaJanela);
        wait.until(ExpectedConditions.numberOfWindowsToBe(janelasAntes + 1));

        String novaJanela = driver.getWindowHandles().stream()
                .filter(janela -> !janela.equals(janelaOriginal))
                .findFirst()
                .orElseThrow();
        driver.switchTo().window(novaJanela);

        return new NewWindowPage(driver, janelaOriginal);
    }
}
