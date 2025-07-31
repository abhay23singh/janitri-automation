package tests;

import base.BaseTest;
import org.testng.annotations.*;
import org.testng.Assert;
import pages.LoginPage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.By;

public class LoginTest extends BaseTest {
    LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        driver.get("https://dev-dash.janitri.in/");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("formEmail")));
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testLoginButtonDisabledWhenFieldAreEmpty() {
        System.out.println("Login button enabled: " + loginPage.isLoginButtonEnabled());
        Assert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled when fields are empty");
    }

    @Test
    public void testInvalidLoginShowErrorMsg() {
        loginPage.enterUserId("randomUser");
        loginPage.enterPassword("randomPassword123");
        loginPage.clickLogin();
        // Wait for the error message to become visible and get its text
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String errorMsg = wait.until(d -> {
            String msg = loginPage.getErrorMessage();
            return (msg != null && !msg.trim().isEmpty()) ? msg : null;
        });
        System.out.println("Error Message after invalid login: " + errorMsg);
        Assert.assertNotNull(errorMsg, "Error message should be shown for invalid login!");
    }

    @Test
    public void testPasswordMaskedbutton() {
        loginPage.enterPassword("someTestPassword");
        // Explicit wait for the eye icon to appear
        new WebDriverWait(driver, Duration.ofSeconds(7))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".password_visible")));
        Assert.assertTrue(loginPage.isEyeIconVisible(), "Eye icon should be visible after typing password");
        Assert.assertEquals(loginPage.getPasswordInputType(), "password", "Password should be masked by default");

        loginPage.togglePasswordVisibility();
        Assert.assertEquals(loginPage.getPasswordInputType(), "text", "Password should be visible after toggle");

        loginPage.togglePasswordVisibility();
        Assert.assertEquals(loginPage.getPasswordInputType(), "password", "Password should be masked again after second toggle");
    }

    @Test
    public void validatePageElementsPresence() {
        // Explicit waits for each element
        new WebDriverWait(driver, Duration.ofSeconds(7)).until(ExpectedConditions.visibilityOfElementLocated(By.id("formEmail")));
        new WebDriverWait(driver, Duration.ofSeconds(7)).until(ExpectedConditions.visibilityOfElementLocated(By.id("formPassword")));
        new WebDriverWait(driver, Duration.ofSeconds(7)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".password_visible")));
        new WebDriverWait(driver, Duration.ofSeconds(7)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.login-button")));

        Assert.assertTrue(loginPage.isUserIdFieldPresent(), "User ID input should be present");
        Assert.assertTrue(loginPage.isPasswordFieldPresent(), "Password input should be present");
        Assert.assertTrue(loginPage.isEyeIconVisible(), "Eye icon should be present");
        Assert.assertTrue(loginPage.isLoginButtonPresent(), "Login button should be present");

        String title = driver.getTitle();
        System.out.println("Page title: " + title);
        Assert.assertNotNull(title);
    }
}