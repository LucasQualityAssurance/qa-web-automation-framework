package utils;

/**
 * Centralizes project-wide configuration values, such as the base URL under test.
 */
public final class ConfigReader {

    private static final String BASE_URL = "https://the-internet.herokuapp.com";

    private ConfigReader() {
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
