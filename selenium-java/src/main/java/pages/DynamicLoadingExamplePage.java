package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representa um dos exemplos de Dynamic Loading
 * (https://the-internet.herokuapp.com/dynamic_loading/1 e /2).
 * <p>
 * Ao clicar em "Start", o site mostra um indicador de carregamento por cerca de 5 segundos
 * e só então exibe a mensagem "Hello World!". No exemplo 1 a mensagem já existe na página,
 * apenas oculta; no exemplo 2 ela só é criada no fim do carregamento. Nos dois casos a espera
 * explícita por visibilidade funciona, sem nenhuma pausa fixa.
 */
public class DynamicLoadingExamplePage extends BasePage {

    private final By botaoIniciar = By.cssSelector("#start button");
    private final By mensagemFinal = By.cssSelector("#finish h4");

    public DynamicLoadingExamplePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clica no botão "Start", que dispara o carregamento.
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public DynamicLoadingExamplePage iniciar() {
        clicar(botaoIniciar);
        return this;
    }

    /**
     * Indica, sem esperar, se a mensagem final está visível neste momento. Retorna
     * {@code false} tanto quando ela está oculta quanto quando ainda não existe na página.
     */
    public boolean mensagemFinalEstaVisivel() {
        List<WebElement> elementos = driver.findElements(mensagemFinal);
        return !elementos.isEmpty() && elementos.get(0).isDisplayed();
    }

    /**
     * Aguarda o fim do carregamento e lê a mensagem final exibida.
     *
     * @return o texto da mensagem final (ex.: "Hello World!").
     */
    public String obterMensagemFinal() {
        return obterTexto(mensagemFinal);
    }
}
