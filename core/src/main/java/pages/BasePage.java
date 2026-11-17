package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

/**
 * Classe base de todos os page objects: guarda o WebDriver, uma espera explícita padrão e
 * as interações comuns (navegar, aguardar visibilidade, clicar, digitar e ler texto).
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, ConfigReader.getTempoEsperaPadrao());
    }

    /**
     * Navega para um caminho do site sob teste e aguarda até que o elemento informado esteja visível.
     *
     * @param caminho          caminho relativo à URL base (ex.: "/login").
     * @param elementoDeCarga  elemento que indica que a página terminou de carregar.
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

    /**
     * Aguarda até que um alerta JavaScript (alert, confirm ou prompt) esteja aberto e troca
     * o foco para ele.
     *
     * @return o alerta aberto.
     */
    protected Alert aguardarAlerta() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }
}
