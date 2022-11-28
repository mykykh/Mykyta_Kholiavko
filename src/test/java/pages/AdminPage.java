package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends Page  {
    @FindBy(xpath = "/html/body/div/div[1]/div[1]/header/div[2]/nav/ul/li[2]")
    WebElement jobSelector;
    @FindBy(xpath = "/html/body/div/div[1]/div[1]/header/div[2]/nav/ul/li[2]/ul/li[5]/a")
    WebElement workShiftsOption;

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public WorkShiftsPage selectJobWorkShifts(){
        jobSelector.click();
        workShiftsOption.click();
        return new WorkShiftsPage(driver);
    }
}
