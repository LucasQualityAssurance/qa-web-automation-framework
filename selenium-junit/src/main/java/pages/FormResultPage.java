package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Represents the result page shown after submitting the Large Form
 * (https://the-internet.herokuapp.com/large_form).
 */
public class FormResultPage {

    private final WebDriver driver;
    private final By heading = By.tagName("h3");

    public FormResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getHeadingText() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(heading))
                .getText();
    }

    /**
     * Waits for Chrome's automatic translation to replace the heading with the
     * Portuguese version before reading it, since the page content is served only in English.
     *
     * @return the (translated) heading text.
     */
    public String getTranslatedHeadingText() {
        WebDriverWait translationWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return translationWait.until(driver -> {
            try {
                String text = driver.findElement(heading).getText();
                return text.toLowerCase().contains("enviad") ? text : null;
            } catch (org.openqa.selenium.WebDriverException e) {
                return null;
            }
        });
    }
}
