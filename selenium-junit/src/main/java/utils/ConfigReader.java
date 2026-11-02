package utils;

/**
 * Centralizes project-wide configuration values, such as the base URL under test.
 */
public final class ConfigReader {

    private static final String BASE_URL = "https://the-internet.herokuapp.com";
    private static final String DOWNLOAD_DIRECTORY =
            "C:\\Ambiente\\IDE\\IntelliJ IDEA Community Edition 2025.1.3\\Projetos\\selenium-junit\\Files";

    private ConfigReader() {
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * Directory where Chrome is configured to automatically save downloaded files.
     *
     * @return the absolute path of the downloads directory.
     */
    public static String getDownloadDirectory() {
        return DOWNLOAD_DIRECTORY;
    }
}
