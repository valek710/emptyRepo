import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.*;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {
    protected Logger Log = LoggerFactory.getLogger(this.getClass().getName());

    @BeforeSuite
    public void printClass() {
        System.out.println("Suite");
        Log.info("Before Suite");
        reportsFolder = "test-result/reports";
        Reporter.log("TSNG Logger", 0, true);
        baseUrl = "https://id-dpss.kitsoft.kiev.ua/";
        timeout = 4000;
    }

    @BeforeTest
    public void printTest() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        Log.info("Before Test");

        remote = "http://yandextank.kitsoft.kiev.ua:8080/wd/hub";
        browser = "chrome";
        browserSize = "1920x1080";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("timeZone", "Europe/Kiev");
        browserCapabilities = capabilities;

        open(baseUrl);
    }

    @BeforeClass
    public void preConfig() {
        Log.info("Before Class");
    }

    @AfterClass
    public void printAfterClass() {
        try {
            WebDriverRunner.getWebDriver().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void closeDriver() {
    }
}
