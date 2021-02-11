package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.SlowLoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Clock;

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

    @FindBy(xpath = "//a[@data-testid='menu-link-common.favourites']")
    WebElement favourites;

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
            secondEvent.isDisplayed();
            favourites.isDisplayed();
            consentAcceptButton.isDisplayed();
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
        wait.until(ExpectedConditions.elementToBeClickable(consentAcceptButton)).click();


    }

    /**
     * item - home | score | away
     */
    public String getSecondEventDetail(String item) {
        return wait.until(visibilityOf(secondEvent.findElement(By.className(item)))).getText();
    }

    public LiveScoreMainPage setSecondEventAsFavourite() {
        wait.until(elementToBeClickable(By.xpath("//div[@data-testid='match-row-1']//button[contains(@class,'styled__Favorite')]"))).click();
        return this;
    }

    public LiveScoreMainPage openFavourites() {
        wait.until(elementToBeClickable(favourites)).click();
        return this;
    }

    /**
     * @param team - home | away
     * @return
     */
    public String getFavourites(String team) {
        return wait.until(presenceOfElementLocated(By.xpath(String.format("//span[@class='%s']/span[@class='team-name']", team)))).getText();
    }

    public LiveScoreEventDetail openSecondEventDetail() {
        // element.click(); - this is getting ElementClickInterceptedException
        Actions act = new Actions(driver);
        act.moveToElement(secondEvent).click().perform();
        return new LiveScoreEventDetail(driver).get();
    }
}
