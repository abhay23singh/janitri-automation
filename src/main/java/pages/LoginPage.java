package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final WebDriver driver;

    @FindBy(id = "formEmail")
    private WebElement userIdInput;

    @FindBy(id = "formPassword")
    private WebElement passwordInput;

    @FindBy(css = "button.login-button")
    private WebElement loginButton;

    @FindBy(css = ".password_visible")
    public WebElement passwordEyeIcon;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoginButtonEnabled() {
        return loginButton.isEnabled();
    }

    public void enterUserId(String userId) {
        userIdInput.clear();
        userIdInput.sendKeys(userId);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void togglePasswordVisibility() {
        passwordEyeIcon.click();
    }

    public String getPasswordInputType() {
        return passwordInput.getAttribute("type"); // "password" or "text"
    }

    public boolean isEyeIconVisible() {
        try {
            return passwordEyeIcon.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isUserIdFieldPresent() {
        try {
            return userIdInput.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPasswordFieldPresent() {
        try {
            return passwordInput.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isLoginButtonPresent() {
        try {
            return loginButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            WebElement errorMsg = driver.findElement(By.cssSelector(".MuiAlert-message, .message, .error"));
            return errorMsg.getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}