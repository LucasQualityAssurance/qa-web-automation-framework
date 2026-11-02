package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

/**
 * Represents the Large Form page (https://the-internet.herokuapp.com/large_form),
 * a long form with many kinds of fields (good ids, only a name, or nothing useful at all).
 * Every field on the screen is covered here: text inputs, date, number, selects (single
 * and multiple), textarea, radio buttons, checkboxes, range slider and color picker.
 */
public class LargeFormPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By email = By.name("email");
    private final By password = By.name("password");
    private final By nickname = By.name("nickname");
    private final By birthday = By.id("birthday");
    private final By age = By.name("age");
    private final By country = By.id("country");
    private final By languages = By.cssSelector("select[name='languages[]']");
    private final By bio = By.id("bio");
    private final By experience = By.id("experience");
    private final By color = By.name("color");
    private final By termsCheckbox = By.id("terms");
    private final By submitButton = By.id("submit");

    public LargeFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigates to the Large Form page, waiting until the first name field is visible.
     *
     * @return this page, to allow fluent chaining.
     */
    public LargeFormPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/large_form");
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(primeiroNome);
        driver.findElement(lastName).sendKeys(sobrenome);
        driver.findElement(this.email).sendKeys(email);
        driver.findElement(password).sendKeys(senha);
        driver.findElement(nickname).sendKeys(apelido);
        driver.findElement(birthday).sendKeys(aniversario);
        driver.findElement(age).sendKeys(idade);
        new Select(driver.findElement(country)).selectByValue(pais);
        driver.findElement(bio).sendKeys(sobreVoce);
        return this;
    }

    /**
     * Selects one or more options in the "Languages" multiple-select list by position,
     * since the visible option text changes once Chrome auto-translates the page
     * (e.g. "English" becomes "Inglês"), which breaks selection by visible text.
     *
     * @param idiomas visible (English) text of each language to select, used only to
     *                look up its fixed position in the list: English, German, Hindi, Spanish.
     * @return this page, to allow fluent chaining.
     */
    public LargeFormPage selecionarIdiomas(String... idiomas) {
        List<String> ordemDasOpcoes = List.of("English", "German", "Hindi", "Spanish");
        Select select = new Select(driver.findElement(languages));
        for (String idioma : idiomas) {
            int indice = ordemDasOpcoes.indexOf(idioma);
            if (indice == -1) {
                throw new IllegalArgumentException("Idioma desconhecido: " + idioma);
            }
            select.selectByIndex(indice);
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
        driver.findElement(By.cssSelector("input[name='contact'][value='" + valor + "']")).click();
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
            driver.findElement(By.cssSelector("input.interest[value='" + valor + "']")).click();
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
        setValueWithJavaScript(driver.findElement(experience), String.valueOf(anos));
        return this;
    }

    /**
     * Sets the "Favorite color" picker via JavaScript, since color inputs open a native
     * OS color-picker dialog that Selenium cannot interact with directly.
     *
     * @param hexColor color in hex format (e.g. "#00ff00").
     * @return this page, to allow fluent chaining.
     */
    public LargeFormPage definirCorFavorita(String hexColor) {
        setValueWithJavaScript(driver.findElement(color), hexColor);
        return this;
    }

    /**
     * Checks the "I accept the terms" checkbox, required to submit the form.
     *
     * @return this page, to allow fluent chaining.
     */
    public LargeFormPage aceitarTermos() {
        driver.findElement(termsCheckbox).click();
        return this;
    }

    /**
     * Submits the form.
     *
     * @return the {@link FormResultPage} the user is redirected to after submitting.
     */
    public FormResultPage enviar() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        return new FormResultPage(driver);
    }

    public List<String> getIdiomasSelecionados() {
        return new Select(driver.findElement(languages)).getAllSelectedOptions()
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private void setValueWithJavaScript(WebElement element, String value) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];"
                        + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
                        + "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                element, value);
    }
}
