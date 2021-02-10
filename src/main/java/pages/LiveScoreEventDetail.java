package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.SlowLoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Clock;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class LiveScoreEventDetail extends SlowLoadableComponent<LiveScoreEventDetail> {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//div[contains(@class, 'styled__FooterContainer')]")
    WebElement footer;

    @FindBy(xpath = "//div[contains(@class, 'styled__MatchScoreRow')]")
    List<WebElement> events;

    /**
     * The page opens in a new window
     * @param webDriver
     */
    public LiveScoreEventDetail(WebDriver webDriver) {
        super(Clock.systemDefaultZone(), 10);
        driver = webDriver;
        wait = new WebDriverWait(driver, 10);
    }

    @Override
    protected void load() {
        // Switch to new window opened
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            footer.isDisplayed();
            events.get(1).isDisplayed();
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    /**
     * item - home | score | away
     */
    public String getEventDetail(String item) {
        if (item.equals("score")) {
            return wait.until(visibilityOfElementLocated(By.xpath("//span[contains(@class, 'styled__ScoresMatch')]"))).getText().replace("\n", "");
        } else {
            return wait.until(visibilityOfElementLocated(By.className(item))).getText();
        }
    }
}
