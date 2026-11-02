package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

/**
 * Represents the home page of https://the-internet.herokuapp.com/.
 * Every test must land on this page before exercising any other flow, since all
 * the available scenarios are reachable from here. It is the pre-requisite
 * starting point for any tester running the automation.
 */
public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By heading = By.tagName("h1");
    private final By availableExamples = By.cssSelector("ul li a");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigates to the home page, waiting until the heading is visible so every
     * automation starts from a known, fully-loaded state.
     *
     * @return this page, to allow fluent chaining.
     */
    public HomePage open() {
        driver.get(ConfigReader.getBaseUrl());
        wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        return this;
    }

    public boolean isLoaded() {
        return driver.getCurrentUrl().startsWith(ConfigReader.getBaseUrl())
                && driver.findElement(heading).isDisplayed();
    }

    public String getHeadingText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(heading)).getText();
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
            String text = driver.findElement(heading).getText();
            return text.toLowerCase().contains("bem-vindo") ? text : null;
        });
    }

    public List<WebElement> getAvailableExamples() {
        return driver.findElements(availableExamples);
    }

    /**
     * Clicks the example link that matches the given visible text (e.g. "Form Authentication").
     *
     * @param linkText visible text of the example link.
     */
    public void goToExample(String linkText) {
        driver.findElement(By.linkText(linkText)).click();
    }
}
