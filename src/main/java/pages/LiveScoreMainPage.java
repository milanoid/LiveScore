package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.SlowLoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Clock;
import java.util.List;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class LiveScoreMainPage extends SlowLoadableComponent<LiveScoreMainPage> {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//div[contains(@class, 'styled__FooterHolder')]")
    WebElement footer;

    @FindBy(xpath = "//div[@data-testid='match-row-1']")
    WebElement secondEvent;

    @FindBy(id = "onetrust-accept-btn-handler")
    WebElement consentAcceptButton;

    public LiveScoreMainPage(WebDriver webDriver) {
        super(Clock.systemDefaultZone(), 10);
        driver = webDriver;
        wait = new WebDriverWait(driver, 10);
    }

    @Override
    protected void load() {
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            footer.isDisplayed();
            secondEvent.isDisplayed(); // second event
            consentAcceptButton.isDisplayed();
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
        // get rid of cookie consent
        wait.until(ExpectedConditions.elementToBeClickable(consentAcceptButton)).click();
    }

    /**
     * item - home | score | away
     */
    public String getSecondEventDetail(String item) {
        return wait.until(visibilityOf(secondEvent.findElement(By.className(item)))).getText();
    }

    public LiveScoreEventDetail openSecondEventDetail() {
        // element.click(); - this is getting ElementClickInterceptedException
        Actions act =  new Actions(driver);
        act.moveToElement(secondEvent).click().perform();
        return new LiveScoreEventDetail(driver).get();
    }
}
