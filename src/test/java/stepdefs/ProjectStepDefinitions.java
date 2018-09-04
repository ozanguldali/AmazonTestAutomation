package stepdefs;

import config.amazon.checkProduct;
import config.browserDecision;
import config.jsonParser;
import config.amazon.saveProduct;
import config.selectDecision;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Component
public class ProjectStepDefinitions {

    private String osValue;
    private WebDriver chromeDriver;
    private WebDriver[] listDriver;
    private List<WebDriver> webDriverList;
    private HashMap<String, Boolean> driverMap = new HashMap<String, Boolean>();
    private By by;
    private List<String> productList = new ArrayList<String>();

    private JsonObject pageObject;

    private static int scenariosCounter = 0;
    private static int failedScenariosCounter = 0;

    private static Logger LOGGER = Logger.getLogger(ProjectStepDefinitions.class);


// multiple browser support üzerine çalışılacak
    public ProjectStepDefinitions() {

        osValue = System.getProperty("os.name");
        this.driverMap.put("chrome", false);
        this.driverMap.put("firefox", false);

    }

//    @Before
//    public void beforeScenario(Scenario scenario){
//
//        String browser = "chrome";
//
//        this.chromeDriver = browserDecision.browser(osValue, browser);
//
//        LOGGER.info(String.format("\n\n\t[%d] > Scenario [%s] started\t", ++scenariosCounter, scenario.getName()));
//
//    }

    @Before("@chrome")
    public void chromeScenario(Scenario scenario){

        String browser = "chrome";
        this.driverMap.put("chrome",true);

        this.chromeDriver = browserDecision.browser(osValue, browser);

        LOGGER.info(String.format("\n\n\t[%d] > Scenario [%s] started\t", ++scenariosCounter, scenario.getName()));

    }

    @Before("@firefox")
    public void firefoxScenario(Scenario scenario){

        String browser = "firefox";
        this.driverMap.put("firefox",true);

        this.chromeDriver = browserDecision.browser(osValue, browser);

        LOGGER.info(String.format("\n\n\t[%d] > Scenario [%s] started\t", ++scenariosCounter, scenario.getName()));

    }


    @After
    public void afterScenario(Scenario scenario) {

        if (scenario.isFailed()) {
            ++failedScenariosCounter;
        } else
            chromeDriver.quit();

        String result = scenario.isFailed() ? "with errors" : "succesfully";
        LOGGER.info(String.format("\n\t[%d] > Scenario [%s] finished %s\t", scenariosCounter, scenario.getName(), result));
        LOGGER.info(String.format("\n\t%d of %d scenarios failed so far\t", failedScenariosCounter, scenariosCounter));

    }

    @Given("^I use (\\w+(?: \\w+)*) driver")
    public void useDriver(String browserKey) {



    }

    @When("^I open (\\w+(?: \\w+)*) page$")
    public void openPage(String flowKey) {

        JsonObject jsonObject = jsonParser.main();
        this.pageObject = jsonObject.get(flowKey).getAsJsonObject();
        String urlString = this.pageObject.get("url").getAsString();

        chromeDriver.get(urlString);

        LOGGER.info(String.format("\n\tNavigate to the website: %s\n\t", urlString));

    }

    @When("^I wait for (\\d+) seconds$")
    public void waitForNSeconds(long seconds) throws Exception {

        Thread.sleep(seconds * 1000L);

        LOGGER.info(String.format("\n\tWait for %d seconds\n\t", seconds));

    }

    @When("^I wait for page$")
    public void iWaitForPage() {

        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        LOGGER.info("\n\tWait for page\n\t");

    }

    @Then("^I see the url is \"([^\"]*)\"$")
    public void iSeeTheUrlIs(String urlExpected) {

        String currentUrl = chromeDriver.getCurrentUrl();
        Assert.assertEquals(currentUrl, urlExpected);

        LOGGER.info(String.format("\n\tCheck if the url is: %s\n\t", urlExpected));

    }

    @Then("^I see the url contains \"([^\"]*)\"$")
    public void iSeeTheUrlContains(String urlExpected) {

        try {
            String currentUrl = chromeDriver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains(urlExpected));

            LOGGER.info(String.format("\n\tCheck if the url contains: %s\n\t", urlExpected));

        } catch (AssertionError e) {
            e.printStackTrace();
        }

    }

    @Then("^I click (\\w+(?: \\w+)*) element by (\\w+(?: \\w+)*)$")
    public void iClickElement(String pageKey, String selectKey) {

        try {
            JsonObject pageElementObject = this.pageObject.get("elements").getAsJsonObject();
            String pageElement = pageElementObject.get(pageKey).getAsString();

            this.by = selectDecision.main(this.by, selectKey, pageElement);

            if (pageKey.contains("product"))
                this.productList.add(saveProduct.main(chromeDriver, this.by));

            //WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
            //wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            //WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
            WebElement element = this.chromeDriver.findElement(by);
            Actions actions = new Actions(chromeDriver);
            actions.moveToElement(element).click().perform();

            LOGGER.info(String.format("\n\tClicking link with label %s\n\t", pageKey));

        } catch ( AssertionError e ) {
            e.printStackTrace();
        }

    }

    @Then("^I mouse hover on (\\w+(?: \\w+)*) element by (\\w+(?: \\w+)*)$")
    public void iMouseHover(String pageKey, String selectKey) {

        try {

            JsonObject pageElementObject = this.pageObject.get("elements").getAsJsonObject();
            String pageElement = pageElementObject.get(pageKey).getAsString();

            this.by = selectDecision.main(this.by, selectKey, pageElement);

            WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
            Actions actions = new Actions(chromeDriver);
            actions.moveToElement(element).moveToElement(this.chromeDriver.findElement(by)).build().perform();

            LOGGER.info(String.format("\n\tMouse hover on %s\n\t", pageKey));

        } catch ( AssertionError e ) {
            e.printStackTrace();
        }

    }

    @Then("^I see (\\w+(?: \\w+)*) element by (\\w+(?: \\w+)*)$")
    public void iSeeElement(String pageKey, String selectKey) {

        JsonObject pageElementObject = this.pageObject.get("elements").getAsJsonObject();
        String pageElement = pageElementObject.get(pageKey).getAsString();

        this.by = selectDecision.main(this.by, selectKey, pageElement);

        boolean existElement = chromeDriver.findElements(by).size() != 0;

        if ( existElement ) {
            LOGGER.info(String.format("\n\tCheck web element: %s\n\t", pageKey));
        } else {
            throw new java.lang.AssertionError("No such element "+pageKey+" on the webpage!");
        }

    }

    @Then("^I fill by (\\w+(?: \\w+)*)$")
    public void iFillBy(String selectKey, DataTable table) {

        for ( DataTableRow row : table.getGherkinRows() ) {

            String key = row.getCells().get(0);
            String value = row.getCells().get(1);

            JsonObject pageElementObject = this.pageObject.get("elements").getAsJsonObject();
            String pageElement = pageElementObject.get(key).getAsString();

            this.by = selectDecision.main(this.by, selectKey, pageElement);

            chromeDriver.findElements( this.by ).clear();
            chromeDriver.findElement( this.by ).sendKeys( value );

            LOGGER.info(String.format("\n\tFilling the key: [%s] \t with the value: [%s]\n\t", key, value));

        }

    }

    @Then("^I see webpage title as \"([^\"]*)\"$")
    public void iSeeWebpageTitleAs(String expectedTitle) {

        String actualTitle = chromeDriver.getTitle();

        if (expectedTitle.equals(actualTitle)) {
            LOGGER.info(String.format("\n\tThe webpage title is [%s] as expected [%s]\n\t", actualTitle, expectedTitle));
        } else {
            throw new java.lang.AssertionError(String.format("\n\tThe webpage title is NOT [%s] as expected [%s]\n\t", actualTitle, expectedTitle));
        }

    }

    @And("^I see text$")
    public void iSeeText(DataTable table) {

        JsonObject pageElementObject = this.pageObject.get("elements").getAsJsonObject();
        String pageElement = pageElementObject.get("mainPanel").getAsString();
        this.by = selectDecision.main(this.by, "xpath", pageElement);

        for ( DataTableRow row : table.getGherkinRows() ) {

            String key = row.getCells().get(0);

            boolean isThere = chromeDriver.getPageSource().contains(key);
            if (isThere) {
                LOGGER.info(String.format("\n\tThe text is in the page: %s\n\t", key));
            } else {
                String temp = chromeDriver.getPageSource();
                System.out.println(temp);
                throw new java.lang.AssertionError(String.format("\n\tThe text is NOT in the page: %s\n\t", key));
            }

        }
    }

    @Then("^I see my added product is on the list$")
    public void iSeeMyAddedProductIsOnTheList() {

        boolean isThere = checkProduct.main(this.productList, chromeDriver);

        if (isThere) {
            LOGGER.info("\n\tThe product is on the list as expected.\n\t");
        } else {
            throw new java.lang.AssertionError("The product is NOT on the list!");
        }

    }

    @Then("^I see my added product is not on the list$")
    public void iSeeMyAddedProductIsNotOnTheList() {

        boolean isThere = checkProduct.main(this.productList, chromeDriver);

        if (!isThere) {
            LOGGER.info("\n\tThe product is not on the list as expected.\n\t");
        } else {
            throw new java.lang.AssertionError("The product is STILL on the list!");
        }

    }

    @When("^I refresh the page$")
    public void iRefreshThePage() {

        try {
            String currentUrl = chromeDriver.getCurrentUrl();
            chromeDriver.navigate().refresh();
            LOGGER.info(String.format("\n\tThe page [ %s ] has been refreshed\n\t", currentUrl));
        } catch ( AssertionError e ) {
            e.printStackTrace();
        }

    }

}
