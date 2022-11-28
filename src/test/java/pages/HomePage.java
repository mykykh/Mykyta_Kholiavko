package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends Page  {
    @FindBy(xpath = "/html/body/div/div[1]/div[1]/aside/nav/div[2]/ul/li[1]/a")
    WebElement goToAdminPageButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public AdminPage goToAdminPage(){
        goToAdminPageButton.click();
        return new AdminPage(driver);
    }
}
