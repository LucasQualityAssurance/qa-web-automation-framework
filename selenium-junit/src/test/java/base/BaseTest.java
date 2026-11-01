package base;

import factory.DriverFactory;
import pages.HomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

/**
 * Base class for all tests: handles WebDriver setup and teardown.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.createDriver();
        homePage = new HomePage(driver).open();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
