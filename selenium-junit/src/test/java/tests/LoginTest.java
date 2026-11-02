package tests;

import base.BaseTest;
import dados.Credenciais;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.SecurePage;
import utils.TestLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Validates the form authentication flow at https://the-internet.herokuapp.com/login.
 * The site does not redirect automatically to the home page after a successful login,
 * so this test manually navigates to the home page afterwards and confirms the
 * "Welcome to the-internet" heading is displayed, closing out the login flow.
 */
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
