package config.amazon;

import org.openqa.selenium.WebDriver;
import java.util.List;

public class checkProduct {

    public static boolean main(List<String> productsList, WebDriver driver) {

        boolean isThere = false;

        for ( String product : productsList ) {

            if( driver.getPageSource().contains(product) ){
                isThere = true;
            }

        }

        return isThere;

    }

}
