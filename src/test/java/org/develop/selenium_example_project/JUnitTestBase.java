package org.develop.selenium_example_project;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Base class for all the JUnit-based test classes
 */
public class JUnitTestBase {

    protected static URL gridHubUrl;
    protected static String baseUrl;
    protected static Capabilities capabilities;

    protected WebDriver driver;

    @BeforeAll
    public static void loadConfig() throws Throwable {
        SuiteConfiguration config = new SuiteConfiguration();
        baseUrl = config.getProperty("site.url");
        if (config.hasProperty("grid.url") && !"".equals(config.getProperty("grid.url"))) {
            gridHubUrl = new URL(config.getProperty("grid.url"));
        }
        capabilities = config.getCapabilities();
    }

    @BeforeEach
    public void initDriver() throws Throwable {
        if ("chrome".equals(capabilities.getBrowserName())) {
            WebDriverManager.chromedriver().setup();
        }
        //Create a map to store  preferences
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }
}
