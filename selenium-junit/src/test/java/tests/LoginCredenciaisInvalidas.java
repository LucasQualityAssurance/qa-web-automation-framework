package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Validates the negative (unhappy path) scenarios of the form authentication flow at
 * https://the-internet.herokuapp.com/login, confirming the error messages shown
 * when the user fails to authenticate.
 */
public class LoginCredenciaisInvalidas extends BaseTest {

    @Test
    @DisplayName("Deve exibir erro ao informar somente o usuario")
    public void deveExibirErroAoInformarSomenteUsuario() {
        String errorMessage = loginPage.open().attemptLogin("tomsmith", "").getTranslatedErrorMessage();

        assertTrue(errorMessage.contains("Sua senha é inválida"));
    }

    @Test
    @DisplayName("Deve exibir erro ao informar somente a senha")
    public void deveExibirErroAoInformarSomenteSenha() {
        String errorMessage = loginPage.open().attemptLogin("", "SuperSecretPassword!").getTranslatedErrorMessage();

        assertTrue(errorMessage.contains("Seu nome de usuário é inválido"));
    }

    @Test
    @DisplayName("Deve exibir erro ao informar usuario e senha invalidos")
    public void deveExibirErroAoInformarUsuarioESenhaInvalidos() {
        String errorMessage = loginPage.open().attemptLogin("usuarioInvalido", "senhaInvalida").getTranslatedErrorMessage();

        assertTrue(errorMessage.contains("Seu nome de usuário é inválido"));
    }
}
