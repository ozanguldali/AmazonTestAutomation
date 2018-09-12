package config;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import stepdefs.SeleniumStepDefinitions;
import java.io.File;

public class browserDecision {

    private static Logger LOGGER = Logger.getLogger(SeleniumStepDefinitions.class);

    public static WebDriver browser(String os, String browser) {

        String osLower = os.toLowerCase();

        if (osLower.contains("win")) {

            if (browser.equals("chrome"))
                System.setProperty("webdriver.chrome.driver", (new File("tools/drivers/chromedriver.exe")).getAbsolutePath());
            else if (browser.contains("firefox"))
                System.setProperty("webdriver.gecko.driver", (new File("tools/drivers/geckodriver.exe")).getAbsolutePath());
            else
                LOGGER.info("\n\n\tBrowser Type Could NOT Been Found !!!");

        } else if (os.toLowerCase().contains("mac") || osLower.contains("sunos") || osLower.contains("nix") || osLower.contains("nux") || osLower.contains("aix")) {

            if (browser.equals("chrome"))
                System.setProperty("webdriver.chrome.driver", (new File("tools/drivers/chromedriver")).getAbsolutePath());
            else if (browser.contains("firefox"))
                System.setProperty("webdriver.gecko.driver", (new File("tools/drivers/geckodriver")).getAbsolutePath());
            else
                LOGGER.info("\n\n\tBrowser Type Could NOT Been Found !!!");

        } else

            LOGGER.info("\n\n\tOperating System Could NOT Been Found !!!");

        return ( options(browser) );

    }

    private static WebDriver options(String browser){

        switch (browser) {
            case "chrome": {

                ChromeOptions options;

                options = new ChromeOptions();
                options.addArguments("test-type");
                options.addArguments("start-fullscreen");
                options.addArguments("incognito");
                options.addArguments("no-sandbox");

                return (new ChromeDriver(options));

            }
            case "firefox": {

                FirefoxOptions options;

                options = new FirefoxOptions();
                options.addArguments("test-type");

                return (new FirefoxDriver(options));

            }
            default:
                return null;
        }

    }

}