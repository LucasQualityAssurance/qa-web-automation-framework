package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representa a página JavaScript Alerts (https://the-internet.herokuapp.com/javascript_alerts),
 * com um botão para cada tipo de alerta nativo do navegador: alert, confirm e prompt.
 * <p>
 * Os alertas não fazem parte do HTML da página: o Selenium precisa trocar o foco para eles
 * ({@code switchTo().alert()}) para ler o texto, digitar, aceitar ou cancelar. Depois que o
 * alerta é fechado, a página registra o que aconteceu no campo "Result".
 */
public class JavaScriptAlertsPage extends BasePage {

    private final By botaoAlerta = By.cssSelector("button[onclick='jsAlert()']");
    private final By botaoConfirmacao = By.cssSelector("button[onclick='jsConfirm()']");
    private final By botaoPrompt = By.cssSelector("button[onclick='jsPrompt()']");
    private final By resultado = By.id("result");

    public JavaScriptAlertsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega para a página JavaScript Alerts, aguardando até que o botão de alerta esteja visível.
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public JavaScriptAlertsPage abrir() {
        navegarPara("/javascript_alerts", botaoAlerta);
        return this;
    }

    /**
     * Clica em "Click for JS Alert" e aguarda o alerta abrir.
     *
     * @return o alerta aberto.
     */
    public Alert abrirAlerta() {
        clicar(botaoAlerta);
        return aguardarAlerta();
    }

    /**
     * Clica em "Click for JS Confirm" e aguarda a confirmação abrir.
     *
     * @return a confirmação aberta (aceitar = OK, cancelar = Cancel).
     */
    public Alert abrirConfirmacao() {
        clicar(botaoConfirmacao);
        return aguardarAlerta();
    }

    /**
     * Clica em "Click for JS Prompt" e aguarda o prompt abrir.
     *
     * @return o prompt aberto, que aceita texto com {@link Alert#sendKeys(String)}.
     */
    public Alert abrirPrompt() {
        clicar(botaoPrompt);
        return aguardarAlerta();
    }

    /**
     * Lê o texto do campo "Result", atualizado pela página depois que o alerta é fechado.
     */
    public String obterResultado() {
        return obterTexto(resultado);
    }
}
