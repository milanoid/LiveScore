import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LiveScoreEventDetail;
import pages.LiveScoreMainPage;

import java.net.MalformedURLException;
import java.net.URL;

public class LiveScoreTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws MalformedURLException {

        // this works with docker-compose, modify if selenium runs elsewhere
        String seleniumServerAddress = "http://localhost:4444/wd/hub";

        if (driver == null) {
            driver = new RemoteWebDriver(new URL(seleniumServerAddress), new ChromeOptions());
            driver.manage().window().maximize();
        }
        wait = new WebDriverWait(driver, 10);
        driver.get("https://www.livescore.com/");
    }

    @Test
    public void eventInfoTest() {
        LiveScoreMainPage mainPage = new LiveScoreMainPage(driver).get();
        String homeAtMainPage = mainPage.getSecondEventDetail("home");
        String scoreAtMainPage = mainPage.getSecondEventDetail("score");
        String awayAtMainPage = mainPage.getSecondEventDetail("away");

        LiveScoreEventDetail pageDetail = mainPage.openSecondEventDetail();
        String homeAtDetailPage = pageDetail.getEventDetail("home");
        String scoreAtDetailPage = pageDetail.getEventDetail("score");
        String awayAtDetailPage = pageDetail.getEventDetail("away");

        assert (homeAtMainPage.equals(homeAtDetailPage));
        assert (scoreAtMainPage.equals(scoreAtDetailPage));
        assert (awayAtMainPage.equals(awayAtDetailPage));
    }

//    @Test
//    public void favouritesTest() {
//
//    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
