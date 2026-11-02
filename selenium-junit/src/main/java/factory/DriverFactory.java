package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigReader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for creating and configuring the WebDriver instance used in the tests.
 */
public class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver() {
        WebDriverManager.chromedriver().setup();

        new File(ConfigReader.getDownloadDirectory()).mkdirs();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("intl.accept_languages", "pt-BR,pt");
        prefs.put("translate", Map.of("enabled", true));
        prefs.put("translate_whitelists", Map.of("en", "pt"));
        prefs.put("download.default_directory", ConfigReader.getDownloadDirectory());
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--lang=pt-BR");
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        return driver;
    }
}
