package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page {
    @FindBy(xpath = "/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/div/div/p[1]")
    WebElement username;
    @FindBy(xpath = "/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/div/div/p[2]")
    WebElement password;
    @FindBy(name = "username")
    WebElement usernameField;
    @FindBy(name = "password")
    WebElement passwordField;
    @FindBy(xpath = "/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button")
    WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void loadPage(){
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    public String getUsername(){
        return username.getText().replace("Username : ", "");
    }

    public String getPassword(){
        return password.getText().replace("Password : ", "");
    }

    public HomePage loginWithCredentials(String username, String password){
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
        return new HomePage(driver);
    }
}
