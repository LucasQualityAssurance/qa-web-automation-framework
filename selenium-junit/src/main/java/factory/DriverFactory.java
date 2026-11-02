package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Responsible for creating and configuring the WebDriver instance used in the tests.
 */
public class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--lang=pt-BR");
        options.setExperimentalOption("prefs", java.util.Map.of(
                "intl.accept_languages", "pt-BR,pt",
                "translate", java.util.Map.of("enabled", true),
                "translate_whitelists", java.util.Map.of("en", "pt")
        ));

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        return driver;
    }
}
