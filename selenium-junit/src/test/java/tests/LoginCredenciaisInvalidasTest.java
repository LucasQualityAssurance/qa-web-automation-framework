package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestLogger;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Validates the negative (unhappy path) scenarios of the form authentication flow at
 * https://the-internet.herokuapp.com/login, confirming the error messages shown
 * when the user fails to authenticate.
 */
public class LoginCredenciaisInvalidasTest extends BaseTest {

    @Test
    @DisplayName("Deve exibir erro ao informar somente o usuario")
    public void deveExibirErroAoInformarSomenteUsuario() {
        TestLogger.step("Abrindo a pagina de login (/login)");
        loginPage.open();

        TestLogger.step("Preenchendo somente o usuario (senha em branco) e enviando o formulario");
        loginPage.attemptLogin("tomsmith", "");

        TestLogger.step("Capturando a mensagem de erro (traduzida para PT)");
        String errorMessage = loginPage.getTranslatedErrorMessage();

        TestLogger.step("Validando que a mensagem indica senha invalida: " + errorMessage);
        assertTrue(errorMessage.contains("Sua senha é inválida"));
    }

    @Test
    @DisplayName("Deve exibir erro ao informar somente a senha")
    public void deveExibirErroAoInformarSomenteSenha() {
        TestLogger.step("Abrindo a pagina de login (/login)");
        loginPage.open();

        TestLogger.step("Preenchendo somente a senha (usuario em branco) e enviando o formulario");
        loginPage.attemptLogin("", "SuperSecretPassword!");

        TestLogger.step("Capturando a mensagem de erro (traduzida para PT)");
        String errorMessage = loginPage.getTranslatedErrorMessage();

        TestLogger.step("Validando que a mensagem indica usuario invalido: " + errorMessage);
        assertTrue(errorMessage.contains("Seu nome de usuário é inválido"));
    }

    @Test
    @DisplayName("Deve exibir erro ao informar usuario e senha invalidos")
    public void deveExibirErroAoInformarUsuarioESenhaInvalidos() {
        TestLogger.step("Abrindo a pagina de login (/login)");
        loginPage.open();

        TestLogger.step("Preenchendo usuario e senha invalidos e enviando o formulario");
        loginPage.attemptLogin("usuarioInvalido", "senhaInvalida");

        TestLogger.step("Capturando a mensagem de erro (traduzida para PT)");
        String errorMessage = loginPage.getTranslatedErrorMessage();

        TestLogger.step("Validando que a mensagem indica usuario invalido: " + errorMessage);
        assertTrue(errorMessage.contains("Seu nome de usuário é inválido"));
    }
}
