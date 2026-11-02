package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

/**
 * Base class for every page object: holds the WebDriver, a default explicit wait and
 * the common interactions (navigate, wait for visibility, click, type and read text).
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, ConfigReader.getTempoEsperaPadrao());
    }

    /**
     * Navigates to a path of the site under test and waits until the given element is visible.
     *
     * @param caminho          path relative to the base URL (e.g. "/login").
     * @param elementoDeCarga  element that indicates the page has finished loading.
     */
    protected void navegarPara(String caminho, By elementoDeCarga) {
        driver.get(ConfigReader.getBaseUrl() + caminho);
        aguardarVisivel(elementoDeCarga);
    }

    protected WebElement aguardarVisivel(By localizador) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(localizador));
    }

    protected void clicar(By localizador) {
        wait.until(ExpectedConditions.elementToBeClickable(localizador)).click();
    }

    protected void escrever(By localizador, String texto) {
        aguardarVisivel(localizador).sendKeys(texto);
    }

    protected String obterTexto(By localizador) {
        return aguardarVisivel(localizador).getText();
    }
}
