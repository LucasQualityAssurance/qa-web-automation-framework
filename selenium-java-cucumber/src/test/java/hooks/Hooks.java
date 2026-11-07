package hooks;

import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.AllureAmbiente;
import utils.TestLogger;

/**
 * Hooks do Cucumber: substituem o {@code BaseTest} dos testes JUnit, cuidando da criação e do
 * encerramento do WebDriver em volta de cada cenário.
 * <p>
 * O driver fica em um {@link ThreadLocal} para que as classes de steps o obtenham por
 * {@link #getDriver()} e para que a execução continue correta caso os cenários rodem em paralelo.
 */
public class Hooks {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    /**
     * Retorna o WebDriver do cenário em execução, criado em {@link #iniciarCenario(Scenario)}.
     */
    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver nao iniciado: o hook @Before do Cucumber nao foi executado.");
        }
        return driver;
    }

    @BeforeAll
    public static void registrarAmbienteNoAllure() {
        AllureAmbiente.registrar();
    }

    @Before
    public void iniciarCenario(Scenario scenario) {
        TestLogger.info("Iniciando cenario: " + scenario.getName());
        DRIVER.set(DriverFactory.criarDriver());
    }

    /**
     * Pausa depois de cada passo do Gherkin para dar tempo de ver a ação no navegador
     * (nenhuma pausa no modo headless; veja {@code ConfigReader.getPausaEntrePassos()}).
     */
    @AfterStep
    public void pausarEntrePassos() {
        TestLogger.pausarEntrePassos();
    }

    @After
    public void finalizarCenario(Scenario scenario) {
        WebDriver driver = DRIVER.get();
        try {
            if (driver != null && scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot da falha");
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
            DRIVER.remove();
            TestLogger.info("Cenario finalizado: " + scenario.getName() + " - " + scenario.getStatus());
        }
    }
}
