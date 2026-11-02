package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the secure area page (https://the-internet.herokuapp.com/secure) that a user
 * is redirected to after a successful login.
 */
public class SecurePage extends BasePage {

    private final By titulo = By.tagName("h2");
    private final By mensagem = By.id("flash");

    public SecurePage(WebDriver driver) {
        super(driver);
    }

    public String obterTitulo() {
        return obterTexto(titulo);
    }

    public String obterMensagem() {
        return obterTexto(mensagem);
    }
}
