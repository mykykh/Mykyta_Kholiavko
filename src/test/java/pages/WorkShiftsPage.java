package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;

public class WorkShiftsPage extends Page {
    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/div[1]/div/button")
    WebElement addWorkShiftButton;
    public WorkShiftsPage(WebDriver driver) {
        super(driver);
    }

    public AddWorkShiftPage goToAddWorkShiftPage(){
        addWorkShiftButton.click();
        return new AddWorkShiftPage(driver);
    }

    public boolean isWorkShiftExists(String shiftName){
        return !driver.findElements(By.xpath("//div[.='" + shiftName + "']")).isEmpty();
    }

    public HashMap<String, String> getWorkShiftInfoByName(String shiftName){
        HashMap<String, String> workShiftInfo = new HashMap<>();
        WebElement workShiftElement = driver.findElement(By.xpath("//div[.='" + shiftName + "']/ancestor::div[2]"));

        workShiftInfo.put("Name", workShiftElement.findElement(By.xpath("./div/div[2]/div")).getText());
        workShiftInfo.put("From", workShiftElement.findElement(By.xpath("./div/div[3]/div")).getText());
        workShiftInfo.put("To", workShiftElement.findElement(By.xpath("./div/div[4]/div")).getText());
        workShiftInfo.put("Hours", workShiftElement.findElement(By.xpath("./div/div[5]/div")).getText());

        return workShiftInfo;
    }

    public DeletePopup deleteWorkShiftWithName(String shiftName){
        WebElement workShiftElement = driver.findElement(By.xpath("//div[.='" + shiftName + "']/ancestor::div[2]"));
        workShiftElement.findElement(By.xpath("./div/div[6]/div/button[1]")).click();
        return new DeletePopup(driver);
    }
}
