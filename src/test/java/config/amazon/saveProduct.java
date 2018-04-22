package config.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class saveProduct {

    public static String main(WebDriver driver, By by){

        return driver.findElement(by).getText();

    }

}
