package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Represents the Large Form page (https://the-internet.herokuapp.com/large_form),
 * a long form with many kinds of fields (good ids, only a name, or nothing useful at all).
 * Every field on the screen is covered here: text inputs, date, number, selects (single
 * and multiple), textarea, radio buttons, checkboxes, range slider and color picker.
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
     * Navigates to the Large Form page, waiting until the first name field is visible.
     *
     * @return this page, to allow fluent chaining.
     */
    public LargeFormPage abrir() {
        navegarPara("/large_form", campoPrimeiroNome);
        return this;
    }

    /**
     * Fills in every text/date/number/select/textarea field of the "Account" and
     * "Details" sections.
     *
     * @return this page, to allow fluent chaining.
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
     * Selects one or more options in the "Languages" multiple-select list.
     *
     * @param idiomas visible text of each language (English, German, Hindi or Spanish).
     * @return this page, to allow fluent chaining.
     */
    public LargeFormPage selecionarIdiomas(String... idiomas) {
        Select select = new Select(aguardarVisivel(listaIdiomas));
        for (String idioma : idiomas) {
            select.selectByVisibleText(idioma);
        }
        return this;
    }

    /**
     * Selects the "Contact me by" radio option.
     *
     * @param valor the radio's value attribute: "email", "phone" or "none".
     * @return this page, to allow fluent chaining.
     */
    public LargeFormPage selecionarContato(String valor) {
        clicar(By.cssSelector("input[name='contact'][value='" + valor + "']"));
        return this;
    }

    /**
     * Checks one or more "Interests" checkboxes.
     *
     * @param valores the checkbox's value attribute(s): "testing", "automation", "devops".
     * @return this page, to allow fluent chaining.
     */
    public LargeFormPage selecionarInteresses(String... valores) {
        for (String valor : valores) {
            clicar(By.cssSelector("input.interest[value='" + valor + "']"));
        }
        return this;
    }

    /**
     * Sets the "Years of experience" range slider via JavaScript, since range sliders
     * cannot be reliably moved with sendKeys/click.
     *
     * @param anos value between 0 and 30.
     * @return this page, to allow fluent chaining.
     */
    public LargeFormPage definirAnosDeExperiencia(int anos) {
        definirValorViaJavaScript(aguardarVisivel(controleExperiencia), String.valueOf(anos));
        return this;
    }

    /**
     * Sets the "Favorite color" picker via JavaScript, since color inputs open a native
     * OS color-picker dialog that Selenium cannot interact with directly.
     *
     * @param corHexadecimal color in hex format (e.g. "#00ff00").
     * @return this page, to allow fluent chaining.
     */
    public LargeFormPage definirCorFavorita(String corHexadecimal) {
        definirValorViaJavaScript(aguardarVisivel(campoCor), corHexadecimal);
        return this;
    }

    /**
     * Checks the "I accept the terms" checkbox, required to submit the form.
     *
     * @return this page, to allow fluent chaining.
     */
    public LargeFormPage aceitarTermos() {
        clicar(caixaTermos);
        return this;
    }

    /**
     * Submits the form.
     *
     * @return the {@link FormResultPage} the user is redirected to after submitting.
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
