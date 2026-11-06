package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Representa a página Large Form (https://the-internet.herokuapp.com/large_form),
 * um formulário longo com vários tipos de campo (com bons ids, só com name ou sem nada útil).
 * Todos os campos da tela são cobertos aqui: campos de texto, data, número, selects (simples
 * e múltiplo), textarea, radio buttons, checkboxes, slider de intervalo e seletor de cor.
 */
public class LargeFormPage extends BasePage {

    private final By campoPrimeiroNome = By.id("first-name");
    private final By campoSobrenome = By.id("last-name");
    private final By campoEmail = By.name("email");
    private final By campoSenha = By.name("password");
    private final By campoApelido = By.name("nickname");
    private final By campoAniversario = By.id("birthday");
    private final By campoIdade = By.name("age");
    private final By listaPais = By.id("country");
    private final By listaIdiomas = By.cssSelector("select[name='languages[]']");
    private final By campoSobreVoce = By.id("bio");
    private final By controleExperiencia = By.id("experience");
    private final By campoCor = By.name("color");
    private final By caixaTermos = By.id("terms");
    private final By botaoEnviar = By.id("submit");

    public LargeFormPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega para a página Large Form, aguardando até que o campo de primeiro nome esteja visível.
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public LargeFormPage abrir() {
        navegarPara("/large_form", campoPrimeiroNome);
        return this;
    }

    /**
     * Preenche todos os campos de texto/data/número/select/textarea das seções
     * "Account" e "Details".
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public LargeFormPage preencherFormulario(String primeiroNome, String sobrenome, String email,
                                             String senha, String apelido, String aniversario,
                                             String idade, String pais, String sobreVoce) {
        escrever(campoPrimeiroNome, primeiroNome);
        escrever(campoSobrenome, sobrenome);
        escrever(campoEmail, email);
        escrever(campoSenha, senha);
        escrever(campoApelido, apelido);
        escrever(campoAniversario, aniversario);
        escrever(campoIdade, idade);
        new Select(aguardarVisivel(listaPais)).selectByValue(pais);
        escrever(campoSobreVoce, sobreVoce);
        return this;
    }

    /**
     * Seleciona uma ou mais opções na lista de seleção múltipla "Languages".
     *
     * @param idiomas texto visível de cada idioma (English, German, Hindi ou Spanish).
     * @return esta página, para permitir encadeamento fluente.
     */
    public LargeFormPage selecionarIdiomas(String... idiomas) {
        Select select = new Select(aguardarVisivel(listaIdiomas));
        for (String idioma : idiomas) {
            select.selectByVisibleText(idioma);
        }
        return this;
    }

    /**
     * Seleciona a opção de radio "Contact me by".
     *
     * @param valor atributo value do radio: "email", "phone" ou "none".
     * @return esta página, para permitir encadeamento fluente.
     */
    public LargeFormPage selecionarContato(String valor) {
        clicar(By.cssSelector("input[name='contact'][value='" + valor + "']"));
        return this;
    }

    /**
     * Marca um ou mais checkboxes de "Interests".
     *
     * @param valores atributo(s) value do checkbox: "testing", "automation", "devops".
     * @return esta página, para permitir encadeamento fluente.
     */
    public LargeFormPage selecionarInteresses(String... valores) {
        for (String valor : valores) {
            clicar(By.cssSelector("input.interest[value='" + valor + "']"));
        }
        return this;
    }

    /**
     * Define o slider "Years of experience" via JavaScript, já que sliders de intervalo
     * não podem ser movidos de forma confiável com sendKeys/click.
     *
     * @param anos valor entre 0 e 30.
     * @return esta página, para permitir encadeamento fluente.
     */
    public LargeFormPage definirAnosDeExperiencia(int anos) {
        definirValorViaJavaScript(aguardarVisivel(controleExperiencia), String.valueOf(anos));
        return this;
    }

    /**
     * Define o seletor "Favorite color" via JavaScript, já que campos de cor abrem a janela
     * nativa de seleção de cor do sistema, com a qual o Selenium não consegue interagir.
     *
     * @param corHexadecimal cor em formato hexadecimal (ex.: "#00ff00").
     * @return esta página, para permitir encadeamento fluente.
     */
    public LargeFormPage definirCorFavorita(String corHexadecimal) {
        definirValorViaJavaScript(aguardarVisivel(campoCor), corHexadecimal);
        return this;
    }

    /**
     * Marca o checkbox "I accept the terms", obrigatório para enviar o formulário.
     *
     * @return esta página, para permitir encadeamento fluente.
     */
    public LargeFormPage aceitarTermos() {
        clicar(caixaTermos);
        return this;
    }

    /**
     * Envia o formulário.
     *
     * @return a {@link FormResultPage} para a qual o usuário é redirecionado após o envio.
     */
    public FormResultPage enviar() {
        clicar(botaoEnviar);
        return new FormResultPage(driver);
    }

    private void definirValorViaJavaScript(WebElement elemento, String valor) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];"
                        + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
                        + "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                elemento, valor);
    }
}
