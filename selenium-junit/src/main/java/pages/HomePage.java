package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ConfigReader;

import java.util.List;

/**
 * Represents the home page of https://the-internet.herokuapp.com/.
 * Every test must land on this page before exercising any other flow, since all
 * the available scenarios are reachable from here. It is the pre-requisite
 * starting point for any tester running the automation.
 */
public class HomePage {

    private final WebDriver driver;

    private final By heading = By.tagName("h1");
    private final By availableExamples = By.cssSelector("ul li a");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigates to the home page, making sure every automation starts from a known state.
     *
     * @return this page, to allow fluent chaining.
     */
    public HomePage open() {
        driver.get(ConfigReader.getBaseUrl());
        return this;
    }

    public boolean isLoaded() {
        return driver.getCurrentUrl().startsWith(ConfigReader.getBaseUrl())
                && driver.findElement(heading).isDisplayed();
    }

    public String getHeadingText() {
        return driver.findElement(heading).getText();
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
