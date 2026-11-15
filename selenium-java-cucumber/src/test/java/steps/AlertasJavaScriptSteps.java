package steps;

import hooks.Hooks;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.openqa.selenium.Alert;
import pages.JavaScriptAlertsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Steps da funcionalidade de alertas JavaScript ({@code features/alertas_javascript.feature}).
 * <p>
 * O alerta aberto fica guardado nesta instância, que o Cucumber recria a cada cenário.
 */
public class AlertasJavaScriptSteps {

    private Alert alerta;

    private JavaScriptAlertsPage alertsPage() {
        return new JavaScriptAlertsPage(Hooks.getDriver());
    }

    @Quando("abro o alerta pelo botão {string}")
    public void abroOAlertaPeloBotao(String botao) {
        JavaScriptAlertsPage alertsPage = alertsPage();
        alerta = switch (botao) {
            case "Click for JS Alert" -> alertsPage.abrirAlerta();
            case "Click for JS Confirm" -> alertsPage.abrirConfirmacao();
            case "Click for JS Prompt" -> alertsPage.abrirPrompt();
            default -> throw new IllegalArgumentException("Botão desconhecido na página de alertas: " + botao);
        };
    }

    @Entao("o alerta deve exibir o texto {string}")
    public void oAlertaDeveExibirOTexto(String textoEsperado) {
        assertEquals(textoEsperado, alerta.getText());
    }

    @Quando("aceito o alerta")
    public void aceitoOAlerta() {
        alerta.accept();
    }

    @Quando("cancelo o alerta")
    public void canceloOAlerta() {
        alerta.dismiss();
    }

    @Quando("digito {string} no alerta e o aceito")
    public void digitoNoAlertaEOAceito(String texto) {
        alerta.sendKeys(texto);
        alerta.accept();
    }

    @Entao("devo ver o resultado {string}")
    public void devoVerOResultado(String resultadoEsperado) {
        assertEquals(resultadoEsperado, alertsPage().obterResultado());
    }
}
