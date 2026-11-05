package tests;

import base.BaseTest;
import dados.Credenciais;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.SecurePage;
import utils.TestLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Valida o fluxo de autenticação por formulário em https://the-internet.herokuapp.com/login.
 * O site não redireciona automaticamente para a página inicial após um login bem-sucedido,
 * então este teste navega manualmente para a página inicial em seguida e confirma que o
 * título "Welcome to the-internet" é exibido, encerrando o fluxo de login.
 */
@Feature("Login")
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Deve fazer login com credenciais validas e finalizar exibindo a home page")
    public void deveFazerLoginEExibirHomePage() {
        TestLogger.step("Abrindo a pagina de login (/login)");
        loginPage.abrir();

        TestLogger.step("Preenchendo usuario e senha validos e enviando o formulario");
        SecurePage securePage = loginPage.fazerLogin(Credenciais.USUARIO_VALIDO, Credenciais.SENHA_VALIDA);

        TestLogger.step("Validando mensagem da area segura");
        assertTrue(securePage.obterMensagem().contains("You logged into a secure area!"));

        TestLogger.step("Navegando manualmente para a home page");
        homePage.abrir();

        TestLogger.step("Validando mensagem de boas-vindas na home page");
        assertEquals("Welcome to the-internet", homePage.obterTitulo());

        TestLogger.step("Login finalizado com sucesso");
    }
}
