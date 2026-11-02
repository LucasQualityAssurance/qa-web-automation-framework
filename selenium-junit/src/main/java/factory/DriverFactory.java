package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for creating and configuring the WebDriver instance used in the tests.
 * The driver binary is resolved automatically by Selenium Manager (bundled with Selenium).
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
