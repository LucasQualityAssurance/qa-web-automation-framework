package base;

import factory.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import utils.AllureAmbiente;
import utils.TestLogger;

/**
 * Classe base de todos os testes: cuida da criação e do encerramento do WebDriver.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeAll
    public static void registrarAmbienteNoAllure() {
        AllureAmbiente.registrar();
    }

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        TestLogger.info("Iniciando teste: " + testInfo.getDisplayName());
        driver = DriverFactory.criarDriver();
        homePage = new HomePage(driver);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (driver != null) {
            driver.quit();
        }
        TestLogger.info("Teste finalizado: " + testInfo.getDisplayName());
    }
}
