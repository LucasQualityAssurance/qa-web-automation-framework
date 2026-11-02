package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the login page of https://the-internet.herokuapp.com/login.
 * Responsible for filling in credentials and submitting the authentication form.
 */
public class LoginPage extends BasePage {

    private final By campoUsuario = By.id("username");
    private final By campoSenha = By.id("password");
    private final By botaoEntrar = By.cssSelector("button[type='submit']");
    private final By mensagemDeErro = By.id("flash");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to the login page, waiting until the username field is visible.
     *
     * @return this page, to allow fluent chaining.
     */
    public LoginPage abrir() {
        navegarPara("/login", campoUsuario);
        return this;
    }

    /**
     * Fills in valid credentials and submits the login form.
     *
     * @return the {@link SecurePage} the user is redirected to after a successful login.
     */
    public SecurePage fazerLogin(String usuario, String senha) {
        enviarFormulario(usuario, senha);
        return new SecurePage(driver);
    }

    /**
     * Fills in the credentials (either of which may be blank) and submits the login form,
     * staying on the login page. Use this for negative scenarios where authentication is
     * expected to fail and the error message needs to be captured.
     *
     * @return this page, to allow fluent chaining.
     */
    public LoginPage tentarLogin(String usuario, String senha) {
        enviarFormulario(usuario, senha);
        return this;
    }

    /**
     * Reads the error message displayed on the login page after a failed authentication attempt.
     */
    public String obterMensagemDeErro() {
        return obterTexto(mensagemDeErro);
    }

    private void enviarFormulario(String usuario, String senha) {
        escrever(campoUsuario, usuario);
        escrever(campoSenha, senha);
        clicar(botaoEntrar);
    }
}
