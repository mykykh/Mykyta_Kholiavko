package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddWorkShiftPage extends Page{

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div/div/div[2]/input")
    WebElement shiftNameField;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/div/div/input")
    WebElement workingHoursFromField;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/div/div[2]/div[1]/input")
    WebElement workingHoursFromHoursField;
    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/div/div[2]/div[3]/input")
    WebElement workingHoursFromMinutesField;
    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/div/div[2]/div[4]/div[1]/input")
    WebElement workingHoursFromAMButton;
    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/div/div[2]/div[4]/div[2]/input")
    WebElement workingHoursFromPMButton;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/div/div/input")
    WebElement workingHoursToField;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/div/div[2]/div[1]/input")
    WebElement workingHoursToHoursField;
    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/div/div[2]/div[3]/input")
    WebElement workingHoursToMinutesField;
    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/div/div[2]/div[4]/div[1]/input")
    WebElement workingHoursToAMButton;
    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/div/div[2]/div[4]/div[2]/input")
    WebElement workingHoursToPMButton;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div/div/div[2]/div/div[1]/input")
    WebElement assignedEmployeesField;

    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[4]/button[2]")
    WebElement saveButton;

    public AddWorkShiftPage(WebDriver driver) {
        super(driver);
    }

    private void enterSiftName(String shiftName){
        shiftNameField.sendKeys(shiftName);
    }

    private void enterWorkingHoursFrom(String fromHours, String fromMinutes, boolean fromAM){
        workingHoursFromField.click();

        workingHoursFromHoursField.sendKeys(Keys.BACK_SPACE);
        workingHoursFromHoursField.sendKeys(Keys.BACK_SPACE);
        workingHoursFromHoursField.sendKeys(fromHours);

        workingHoursFromMinutesField.sendKeys(Keys.BACK_SPACE);
        workingHoursFromMinutesField.sendKeys(Keys.BACK_SPACE);
        workingHoursFromMinutesField.sendKeys(fromMinutes);
        if (fromAM){
            workingHoursFromAMButton.click();
        }else{
            workingHoursFromPMButton.click();
        }
    }

    private void enterWorkingHoursTo(String toHours, String toMinutes, boolean toAM){
        workingHoursToField.click();

        workingHoursToHoursField.sendKeys(Keys.BACK_SPACE);
        workingHoursToHoursField.sendKeys(Keys.BACK_SPACE);
        workingHoursToHoursField.sendKeys(toHours);

        workingHoursToMinutesField.sendKeys(Keys.BACK_SPACE);
        workingHoursToMinutesField.sendKeys(Keys.BACK_SPACE);
        workingHoursToMinutesField.sendKeys(toMinutes);

        if (toAM){
            workingHoursToAMButton.click();
        }else{
            workingHoursToPMButton.click();
        }
    }

    private void assignEmployee(String employeeName){
        assignedEmployeesField.sendKeys(employeeName);
        String[] employeeNames = employeeName.split(" ");
        driver.findElement(By.xpath("//span[starts-with(., '" + employeeNames[0] + "') and substring(., string-length(.) - string-length('" + employeeNames[1] + "') + 1) = 'Adalwin']")).click();
    }

    public void enterWorkShiftInfo(String shiftName, String from, String to, String employeeName){
        enterSiftName(shiftName);

        String[] fromElements = from.split(" ");
        enterWorkingHoursFrom(fromElements[0].split(":")[0], fromElements[0].split(":")[1], fromElements[1].equals("AM"));

        String[] toElements = to.split(" ");
        enterWorkingHoursTo(toElements[0].split(":")[0], toElements[0].split(":")[1], toElements[1].equals("AM"));

        assignEmployee(employeeName);
    }

    public WorkShiftsPage saveWorkShift(){
        saveButton.click();
        return new WorkShiftsPage(driver);
    }
}
