package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import pages.JavaScriptAlertsPage;
import utils.TestLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Valida os três tipos de alerta nativo do navegador em
 * https://the-internet.herokuapp.com/javascript_alerts: alert, confirm (OK e Cancel) e
 * prompt (com texto e cancelado). Cada teste confirma o texto do alerta e o resultado que
 * a página registra depois que ele é fechado.
 */
public class AlertasJavaScriptTest extends BaseTest {

    private static final String TEXTO_DO_PROMPT = "Teste de automacao";

    private JavaScriptAlertsPage alertsPage;

    @BeforeEach
    public void acessarPaginaDeAlertas() {
        TestLogger.step("Abrindo a home page (/)");
        homePage.abrir();

        TestLogger.step("Clicando no link 'JavaScript Alerts'");
        alertsPage = homePage.acessarAlertasJavaScript();
    }

    @Test
    @DisplayName("Deve aceitar o alerta simples")
    public void deveAceitarAlertaSimples() {
        TestLogger.step("Clicando em 'Click for JS Alert'");
        Alert alerta = alertsPage.abrirAlerta();

        TestLogger.step("Validando o texto do alerta: " + alerta.getText());
        assertEquals("I am a JS Alert", alerta.getText());

        TestLogger.step("Clicando em OK");
        alerta.accept();

        TestLogger.step("Validando o resultado");
        assertEquals("You successfully clicked an alert", alertsPage.obterResultado());
    }

    @Test
    @DisplayName("Deve confirmar a caixa de confirmacao")
    public void deveConfirmarCaixaDeConfirmacao() {
        TestLogger.step("Clicando em 'Click for JS Confirm'");
        Alert confirmacao = alertsPage.abrirConfirmacao();

        TestLogger.step("Validando o texto da confirmacao: " + confirmacao.getText());
        assertEquals("I am a JS Confirm", confirmacao.getText());

        TestLogger.step("Clicando em OK");
        confirmacao.accept();

        TestLogger.step("Validando o resultado");
        assertEquals("You clicked: Ok", alertsPage.obterResultado());
    }

    @Test
    @DisplayName("Deve cancelar a caixa de confirmacao")
    public void deveCancelarCaixaDeConfirmacao() {
        TestLogger.step("Clicando em 'Click for JS Confirm'");
        Alert confirmacao = alertsPage.abrirConfirmacao();

        TestLogger.step("Clicando em Cancel");
        confirmacao.dismiss();

        TestLogger.step("Validando o resultado");
        assertEquals("You clicked: Cancel", alertsPage.obterResultado());
    }

    @Test
    @DisplayName("Deve digitar um texto no prompt e confirmar")
    public void deveDigitarTextoNoPromptEConfirmar() {
        TestLogger.step("Clicando em 'Click for JS Prompt'");
        Alert prompt = alertsPage.abrirPrompt();

        TestLogger.step("Validando o texto do prompt: " + prompt.getText());
        assertEquals("I am a JS prompt", prompt.getText());

        TestLogger.step("Digitando '" + TEXTO_DO_PROMPT + "' e clicando em OK");
        prompt.sendKeys(TEXTO_DO_PROMPT);
        prompt.accept();

        TestLogger.step("Validando o resultado");
        assertEquals("You entered: " + TEXTO_DO_PROMPT, alertsPage.obterResultado());
    }

    @Test
    @DisplayName("Deve cancelar o prompt sem informar texto")
    public void deveCancelarPrompt() {
        TestLogger.step("Clicando em 'Click for JS Prompt'");
        Alert prompt = alertsPage.abrirPrompt();

        TestLogger.step("Clicando em Cancel");
        prompt.dismiss();

        TestLogger.step("Validando o resultado (o prompt cancelado devolve null)");
        assertEquals("You entered: null", alertsPage.obterResultado());
    }
}
