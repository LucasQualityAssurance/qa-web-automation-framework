package tests;

import base.BaseTest;
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
 * (Chrome-translated) "Bem-vindo à internet" message is displayed, closing out the login flow.
 */
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Deve fazer login com credenciais validas e finalizar exibindo a home page")
    public void deveFazerLoginEExibirHomePage() {
        TestLogger.step("Abrindo a pagina de login (/login)");
        loginPage.open();

        TestLogger.step("Preenchendo usuario e senha validos e enviando o formulario");
        SecurePage securePage = loginPage.login("tomsmith", "SuperSecretPassword!");

        TestLogger.step("Validando mensagem da area segura (traduzida para PT)");
        assertTrue(securePage.getTranslatedFlashMessage().contains("Você entrou em uma área segura"));

        TestLogger.step("Navegando manualmente para a home page");
        homePage.open();

        TestLogger.step("Validando mensagem de boas-vindas na home page (traduzida para PT)");
        assertEquals("Bem-vindo à internet", homePage.getTranslatedHeadingText());

        TestLogger.step("Login finalizado com sucesso");
    }
}
