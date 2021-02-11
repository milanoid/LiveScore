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
        // score is a bit tricky - a match yet to be played has "? - ?" instead of score result
        // at the Main page and "vs" in the detail, this would require additional logic
        // hence checking only home and away team names

        LiveScoreMainPage mainPage = new LiveScoreMainPage(driver).get();
        String homeAtMainPage = mainPage.getSecondEventDetail("home");
        String awayAtMainPage = mainPage.getSecondEventDetail("away");

        LiveScoreEventDetail pageDetail = mainPage.openSecondEventDetail();
        String homeAtDetailPage = pageDetail.getEventDetail("home");
        String awayAtDetailPage = pageDetail.getEventDetail("away");

        assert (homeAtMainPage.equals(homeAtDetailPage));
        assert (awayAtMainPage.equals(awayAtDetailPage));
    }

    @Test
    public void favouritesTest() {
        LiveScoreMainPage mainPage = new LiveScoreMainPage(driver).get();
        mainPage.setSecondEventAsFavourite();

        String homeTeamAtMainPage = mainPage.getSecondEventDetail("home");
        String awayTeamAtMainPage = mainPage.getSecondEventDetail("away");

        mainPage.openFavourites();
        String homeTeamAtFavourites = mainPage.getFavourites("home");
        String awayTeamAtFavourites = mainPage.getFavourites("away");

        assert (homeTeamAtMainPage.equals(homeTeamAtFavourites));
        assert (awayTeamAtMainPage.equals(awayTeamAtFavourites));

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
