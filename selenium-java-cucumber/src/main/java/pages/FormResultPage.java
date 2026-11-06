package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representa a página de resultado exibida após o envio do Large Form
 * (https://the-internet.herokuapp.com/large_form).
 */
public class FormResultPage extends BasePage {

    private final By titulo = By.tagName("h3");

    public FormResultPage(WebDriver driver) {
        super(driver);
    }

    public String obterTitulo() {
        return obterTexto(titulo);
    }
}
