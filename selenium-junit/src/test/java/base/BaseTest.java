package base;

import factory.DriverFactory;
import pages.HomePage;
import pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

/**
 * Base class for all tests: handles WebDriver setup and teardown.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    protected LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.createDriver();
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
