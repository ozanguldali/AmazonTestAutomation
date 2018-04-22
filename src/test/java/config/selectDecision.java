package config;

import org.openqa.selenium.By;

public class selectDecision {

    static public By main(By by, String decisionVariable, String pageElement) {

        try {

            if (decisionVariable.equals("id")) {
                by = By.id(pageElement);
            } else if (decisionVariable.equals("name")) {
                by = By.name(pageElement);
            } else if (decisionVariable.equals("xpath")) {
                by = By.xpath(pageElement);
            } else if (decisionVariable.equals("className")) {
                by = By.className(pageElement);
            } else if (decisionVariable.equals("cssSelector")) {
                by = By.cssSelector(pageElement);
            } else if (decisionVariable.equals("tagName")) {
                by = By.tagName(pageElement);
            } else if (decisionVariable.equals("linkText")) {
                by = By.linkText(pageElement);
            } else if (decisionVariable.equals("partialLinkText")) {
                by = By.partialLinkText(pageElement);
            } else
                throw new java.lang.Error("Not a valid selector type: %s" + decisionVariable);

        } catch ( AssertionError e ) {
            e.printStackTrace();
        }

        return by;

    }

}
