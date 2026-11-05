package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representa a página da área segura (https://the-internet.herokuapp.com/secure) para a qual
 * o usuário é redirecionado após um login bem-sucedido.
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
