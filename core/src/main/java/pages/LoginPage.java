package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representa a página de login de https://the-internet.herokuapp.com/login.
 * Responsável por preencher as credenciais e enviar o formulário de autenticação.
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
     * Navega para a página de login, aguardando até que o campo de usuário esteja visível.
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public LoginPage abrir() {
        navegarPara("/login", campoUsuario);
        return this;
    }

    /**
     * Preenche credenciais válidas e envia o formulário de login.
     *
     * @return a {@link SecurePage} para a qual o usuário é redirecionado após um login bem-sucedido.
     */
    public SecurePage fazerLogin(String usuario, String senha) {
        enviarFormulario(usuario, senha);
        return new SecurePage(driver);
    }

    /**
     * Preenche as credenciais (qualquer uma pode ficar em branco) e envia o formulário de login,
     * permanecendo na página de login. Use nos cenários negativos, em que se espera que a
     * autenticação falhe e a mensagem de erro precisa ser capturada.
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public LoginPage tentarLogin(String usuario, String senha) {
        enviarFormulario(usuario, senha);
        return this;
    }

    /**
     * Lê a mensagem de erro exibida na página de login após uma tentativa de autenticação malsucedida.
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
