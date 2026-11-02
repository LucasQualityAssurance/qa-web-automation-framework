package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

/**
 * Represents the login page of https://the-internet.herokuapp.com/login.
 * Responsible for filling in credentials and submitting the authentication form.
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By submitButton = By.cssSelector("button[type='submit']");
    private final By errorMessage = By.id("flash");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigates to the login page, waiting until the username field is visible.
     *
     * @return this page, to allow fluent chaining.
     */
    public LoginPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(username));
        return this;
    }

    /**
     * Fills in the credentials and submits the login form, waiting for each
     * field/button to become visible before interacting with it.
     *
     * @param user the username to authenticate with.
     * @param pass the password to authenticate with.
     * @return the {@link SecurePage} the user is redirected to after a successful login.
     */
    public SecurePage login(String user, String pass) {
        submitForm(user, pass);
        return new SecurePage(driver);
    }

    /**
     * Fills in the credentials (either of which may be blank) and submits the login form,
     * staying on the login page. Use this for negative scenarios where authentication is
     * expected to fail and the error message needs to be captured.
     *
     * @param user the username to authenticate with (may be blank).
     * @param pass the password to authenticate with (may be blank).
     * @return this page, to allow fluent chaining.
     */
    public LoginPage attemptLogin(String user, String pass) {
        submitForm(user, pass);
        return this;
    }

    /**
     * Reads the error message displayed on the login page after a failed authentication attempt.
     *
     * @return the error message text.
     */
    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    /**
     * Waits for Chrome's automatic translation to replace the error message with the
     * Portuguese version before reading it, since the page content is served only in English.
     *
     * @return the (translated) error message text.
     */
    public String getTranslatedErrorMessage() {
        WebDriverWait translationWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return translationWait.until(driver -> {
            try {
                String text = driver.findElement(errorMessage).getText();
                return text.toLowerCase().contains("inválid") ? text : null;
            } catch (org.openqa.selenium.WebDriverException e) {
                return null;
            }
        });
    }

    private void submitForm(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(username)).sendKeys(user);
        wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(pass);
        wait.until(ExpectedConditions.visibilityOfElementLocated(submitButton)).click();
    }
}
