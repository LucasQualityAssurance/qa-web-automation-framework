package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Represents the secure area page (https://the-internet.herokuapp.com/secure) that a user
 * is redirected to after a successful login.
 */
public class SecurePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By heading = By.tagName("h2");
    private final By flashMessage = By.id("flash");

    public SecurePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getHeadingText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(heading)).getText();
    }

    public String getFlashMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessage)).getText();
    }

    /**
     * Waits for Chrome's automatic translation to replace the flash message with the
     * Portuguese version before reading it, since the page content is served only in English.
     *
     * @return the (translated) flash message text.
     */
    public String getTranslatedFlashMessage() {
        WebDriverWait translationWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return translationWait.until(driver -> {
            String text = driver.findElement(flashMessage).getText();
            return text.contains("segura") ? text : null;
        });
    }
}
