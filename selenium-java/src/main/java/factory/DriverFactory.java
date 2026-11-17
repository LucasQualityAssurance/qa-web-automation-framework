package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsável por criar e configurar a instância do WebDriver usada nos testes.
 * O binário do driver é resolvido automaticamente pelo Selenium Manager (incluído no Selenium).
 */
public class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver criarDriver() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", ConfigReader.getDiretorioDownload().toString());
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }

        WebDriver driver = new ChromeDriver(options);
        if (!ConfigReader.isHeadless()) {
            driver.manage().window().maximize();
        }

        return driver;
    }
}
