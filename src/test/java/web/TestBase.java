package web;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;

public class TestBase {

    @BeforeAll
    public static void beforeAll() {

        Configuration.browser = "chrome";
        Configuration.browserVersion = "89.0"; // 88.0 // 87.0

//            Configuration.browser = "opera";
//            Configuration.browserVersion = "75.0"; // 74.0 // 73.0
//
//            Configuration.browser = "firefox";
//            Configuration.browserVersion = "87.0"; // 86.0 // 85.0
//
//            Configuration.browser = "safari";
//            Configuration.browserVersion = "13.0";

        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("browserName", "opera");
//        capabilities.setCapability("browserVersion", "73.0");
        capabilities.setCapability("enableVNC", "true");
        capabilities.setCapability("enableVideo", "true");
        Configuration.browserCapabilities = capabilities;

//        if (!System.getProperty("selenoid.url").equals(""))
//            Configuration.remote = System.getProperty("selenoid.url");

        addListener("AllureSelenide", new AllureSelenide());
    }

    public static String getSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }

    @AfterEach
    public void afterEach() {
        String sessionId = getSessionId();

        attachScreenshot("Last screenshot");
        attachPageSource();

        closeWebDriver();

        if (System.getProperty("video.url") != "")
            attachVideo(sessionId);
    }

}
