package hooks;

import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import utils.AllureAmbiente;
import utils.TestLogger;

/**
 * Hooks do Cucumber: substituem o {@code BaseTest} dos testes JUnit, cuidando da criação e do
 * encerramento do WebDriver em volta de cada cenário.
 * <p>
 * O driver fica em um {@link ThreadLocal} para que as classes de steps o obtenham por
 * {@link #getDriver()} e para que a execução continue correta caso os cenários rodem em paralelo.
 * <p>
 * A pausa de visualização entre os passos fica no {@link PausaEntrePassosPlugin}, e não aqui,
 * para não gerar um passo extra no relatório do Allure.
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

    @After
    public void finalizarCenario(Scenario scenario) {
        WebDriver driver = DRIVER.get();
        try {
            if (driver != null && scenario.isFailed()) {
                anexarEvidenciaDaFalha(driver, scenario);
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
            DRIVER.remove();
            TestLogger.info("Cenario finalizado: " + scenario.getName() + " - " + scenario.getStatus());
        }
    }

    /**
     * Anexa ao relatório um screenshot da tela no momento da falha.
     * <p>
     * O navegador não tira screenshot enquanto um alerta JavaScript está aberto: o Chrome fecha o
     * alerta e o Selenium lança {@link UnhandledAlertException}. Nesse caso o texto do alerta é
     * anexado como evidência, seguido de um screenshot da página depois que o alerta foi fechado.
     * Qualquer outro erro ao capturar a evidência é anexado como texto, para nunca esconder a falha
     * original do cenário.
     */
    private void anexarEvidenciaDaFalha(WebDriver driver, Scenario scenario) {
        try {
            anexarScreenshot(driver, scenario, "Screenshot da falha");
        } catch (UnhandledAlertException e) {
            scenario.attach("Havia um alerta JavaScript aberto no momento da falha: " + e.getAlertText(),
                    "text/plain", "Alerta aberto na falha");
            try {
                anexarScreenshot(driver, scenario, "Screenshot da falha (depois de fechar o alerta)");
            } catch (WebDriverException erroNoNovoScreenshot) {
                anexarErroDeCaptura(scenario, erroNoNovoScreenshot);
            }
        } catch (WebDriverException e) {
            anexarErroDeCaptura(scenario, e);
        }
    }

    private void anexarScreenshot(WebDriver driver, Scenario scenario, String nome) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", nome);
    }

    private void anexarErroDeCaptura(Scenario scenario, WebDriverException erro) {
        scenario.attach("Nao foi possivel capturar o screenshot: " + erro.getMessage(),
                "text/plain", "Screenshot indisponivel");
    }
}
