package tests;

import base.BaseTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.TestLogger;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Valida os cenários negativos (caminho infeliz) do fluxo de autenticação por formulário em
 * https://the-internet.herokuapp.com/login, confirmando as mensagens de erro exibidas
 * quando o usuário não consegue se autenticar. Cada linha da tabela é um cenário.
 */
@Feature("Login")
public class LoginCredenciaisInvalidasTest extends BaseTest {

    @ParameterizedTest(name = "{0}")
    @DisplayName("Deve exibir erro ao informar credenciais invalidas")
    @CsvSource({
            "Somente o usuario (senha em branco),    tomsmith,        '',                   Your password is invalid!",
            "Somente a senha (usuario em branco),    '',              SuperSecretPassword!, Your username is invalid!",
            "Usuario e senha invalidos,              usuarioInvalido, senhaInvalida,        Your username is invalid!"
    })
    public void deveExibirErroAoInformarCredenciaisInvalidas(String cenario, String usuario, String senha,
                                                             String mensagemEsperada) {
        TestLogger.step("Cenario: " + cenario);

        TestLogger.step("Abrindo a pagina de login (/login)");
        loginPage.abrir();

        TestLogger.step("Preenchendo as credenciais e enviando o formulario");
        loginPage.tentarLogin(usuario, senha);

        TestLogger.step("Capturando a mensagem de erro");
        String mensagemDeErro = loginPage.obterMensagemDeErro();

        TestLogger.step("Validando a mensagem de erro: " + mensagemDeErro);
        assertTrue(mensagemDeErro.contains(mensagemEsperada),
                "Esperado conter '" + mensagemEsperada + "', mas foi: " + mensagemDeErro);
    }
}
