package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representa a página Dynamic Loading (https://the-internet.herokuapp.com/dynamic_loading),
 * que dá acesso aos dois exemplos de elementos carregados dinamicamente.
 */
public class DynamicLoadingPage extends BasePage {

    private final By titulo = By.tagName("h3");

    public DynamicLoadingPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega para a página Dynamic Loading, aguardando até que o título esteja visível.
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public DynamicLoadingPage abrir() {
        navegarPara("/dynamic_loading", titulo);
        return this;
    }

    /**
     * Abre um dos exemplos da página.
     *
     * @param numero 1 (elemento já existe na página, mas está oculto) ou
     *               2 (elemento só é adicionado à página depois do carregamento).
     * @return a página do exemplo escolhido.
     */
    public DynamicLoadingExamplePage acessarExemplo(int numero) {
        clicar(By.cssSelector("a[href='/dynamic_loading/" + numero + "']"));
        return new DynamicLoadingExamplePage(driver);
    }
}
