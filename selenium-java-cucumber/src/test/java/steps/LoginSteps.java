package steps;

import dados.Credenciais;
import hooks.Hooks;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.HomePage;
import pages.LoginPage;
import pages.SecurePage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Steps da funcionalidade de login ({@code features/login.feature}).
 * <p>
 * O Cucumber cria uma nova instância desta classe a cada cenário, então o estado guardado aqui
 * (páginas e o resultado do login) não vaza de um cenário para outro. O WebDriver vem dos
 * {@link Hooks}, que o abrem antes e o fecham depois de cada cenário.
 */
public class LoginSteps {

    private LoginPage loginPage;
    private SecurePage securePage;

    @Dado("que estou na página de login")
    public void queEstouNaPaginaDeLogin() {
        loginPage = new LoginPage(Hooks.getDriver()).abrir();
    }

    @Quando("faço login com credenciais válidas")
    public void facoLoginComCredenciaisValidas() {
        securePage = loginPage.fazerLogin(Credenciais.USUARIO_VALIDO, Credenciais.SENHA_VALIDA);
    }

    @Quando("tento fazer login com o usuário {string} e a senha {string}")
    public void tentoFazerLoginComOUsuarioEASenha(String usuario, String senha) {
        loginPage.tentarLogin(usuario, senha);
    }

    @Entao("devo ser direcionado à área segura com a mensagem {string}")
    public void devoSerDirecionadoAAreaSeguraComAMensagem(String mensagemEsperada) {
        String mensagem = securePage.obterMensagem();
        assertTrue(mensagem.contains(mensagemEsperada),
                "Esperado conter '" + mensagemEsperada + "', mas foi: " + mensagem);
    }

    @E("ao acessar a página inicial devo ver o título {string}")
    public void aoAcessarAPaginaInicialDevoVerOTitulo(String tituloEsperado) {
        HomePage homePage = new HomePage(Hooks.getDriver()).abrir();
        assertEquals(tituloEsperado, homePage.obterTitulo());
    }

    @Entao("devo ver a mensagem de erro {string}")
    public void devoVerAMensagemDeErro(String mensagemEsperada) {
        String mensagem = loginPage.obterMensagemDeErro();
        assertTrue(mensagem.contains(mensagemEsperada),
                "Esperado conter '" + mensagemEsperada + "', mas foi: " + mensagem);
    }
}
