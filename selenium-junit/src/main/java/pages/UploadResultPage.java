package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Represents the result page shown after submitting the File Uploader form
 * (https://the-internet.herokuapp.com/upload).
 */
public class UploadResultPage {

    private final WebDriver driver;

    private final By heading = By.tagName("h3");
    private final By uploadedFiles = By.id("uploaded-files");

    public UploadResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getHeadingText() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(heading))
                .getText();
    }

    public String getUploadedFileName() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(uploadedFiles))
                .getText();
    }

    /**
     * Waits for Chrome's automatic translation to replace the heading with the
     * Portuguese version before reading it. Chrome does not always auto-translate this
     * page (it's rendered from a form POST instead of a plain GET navigation), so after
     * a short wait this falls back to the original English heading instead of failing.
     *
     * @return the heading text, translated to PT when Chrome manages to translate it in time.
     */
    public String getTranslatedHeadingText() {
        try {
            WebDriverWait translationWait = new WebDriverWait(driver, Duration.ofSeconds(8));
            return translationWait.until(driver -> {
                try {
                    String text = driver.findElement(heading).getText();
                    return text.toLowerCase().contains("arquivo") ? text : null;
                } catch (org.openqa.selenium.WebDriverException e) {
                    return null;
                }
            });
        } catch (org.openqa.selenium.TimeoutException e) {
            return getHeadingText();
        }
    }
}
