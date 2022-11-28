package hellocucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.HashMap;

public class WorkShiftsTestStepDefinitions {
    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    AdminPage adminPage;
    WorkShiftsPage workShiftsPage;
    AddWorkShiftPage addWorkShiftPage;

    DeletePopup deletePopup;

    HashMap<String, String> workShiftInfo = new HashMap<>();

    @Before
    public void setup(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Given("I am on the Login page")
    public void iAmOnTheLoginPage() {
        loginPage = new LoginPage(driver);
        loginPage.loadPage();
    }

    @When("I logging in")
    public void iLoggingIn() {
        String username = loginPage.getUsername();
        String password = loginPage.getPassword();
        homePage = loginPage.loginWithCredentials(username, password);
    }

    @When("I go to Admin page")
    public void iGoToAdminPage() {
        adminPage = homePage.goToAdminPage();
    }

    @And("I select job - work shifts")
    public void iSelectJobWorkShifts() {
        workShiftsPage = adminPage.selectJobWorkShifts();
    }

    @And("I click on the Add button")
    public void iClickOnTheAddButton() {
        addWorkShiftPage = workShiftsPage.goToAddWorkShiftPage();
    }

    @And("I enter work shift name {string}, from {string}, to {string}, employee {string}")
    public void iEnterWorkShiftNameFromToEmployee(String name, String from, String to, String employeeName) {
        workShiftInfo.put("Name", name);
        workShiftInfo.put("From", from);
        workShiftInfo.put("To", to);
        workShiftInfo.put("Employee", employeeName);
        addWorkShiftPage.enterWorkShiftInfo(name, from, to, employeeName);
        workShiftsPage = addWorkShiftPage.saveWorkShift();
    }

    private String getTimeIn24HFormat(String time12HFormat){
        String[] timeElements = time12HFormat.split(" ");
        String[] HoursMinutes = timeElements[0].split(":");
        if (timeElements[1].equals("PM")){
            HoursMinutes[0] = String.valueOf((Integer.parseInt(HoursMinutes[0]) + 12));
            HoursMinutes[0] = HoursMinutes[0].equals("24") ? "00" : HoursMinutes[0];
        }
        return HoursMinutes[0] + ":" + HoursMinutes[1];
    }

    private String getShiftDuration(String start, String end){
        String[] startTimeElements = start.split(":");
        String[] endTimeElements = end.split(":");
        int minutes = Integer.parseInt(endTimeElements[1]) - Integer.parseInt(startTimeElements[1]);
        int hours = Integer.parseInt(endTimeElements[0]) - Integer.parseInt(startTimeElements[0]);
        if (minutes < 0){
            hours -= 1;
            minutes += 60;
        }
        if (minutes < 10){
            return  hours + ".0" + minutes;
        }else {
            return  hours + "." + minutes;
        }
    }

    @Then("I should see my work shift added")
    public void iShouldSeeMyWorkShiftAdded() {
        HashMap<String, String> savedWorkShiftInfo = workShiftsPage.getWorkShiftInfoByName("test");
        assertEquals(workShiftInfo.get("Name"), savedWorkShiftInfo.get("Name"));
        assertEquals(getTimeIn24HFormat(workShiftInfo.get("From")), savedWorkShiftInfo.get("From"));
        assertEquals(getTimeIn24HFormat(workShiftInfo.get("To")), savedWorkShiftInfo.get("To"));
        assertEquals(getShiftDuration(getTimeIn24HFormat(workShiftInfo.get("From")), getTimeIn24HFormat(workShiftInfo.get("To"))), savedWorkShiftInfo.get("Hours"));
    }

    @When("I click Delete Selected button")
    public void iClickDeleteSelectedButton() {
        deletePopup = workShiftsPage.deleteWorkShiftWithName("test");
    }

    @And("I click Yes, Delete button")
    public void iClickYesDeleteButton() {
        workShiftsPage = deletePopup.clickDeleteButton();
    }

    @Then("I should not see my work shift")
    public void iShouldNotSeeMyWorkShift() {
        assertFalse(workShiftsPage.isWorkShiftExists("test"));
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }
}
