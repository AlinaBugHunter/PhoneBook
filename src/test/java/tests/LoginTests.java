package tests;

import dto.UserDTO;
import manager.ApplicationManager;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestNGListener;

import java.util.Random;

import static utils.TakeScreenShot.*;

@Listeners(TestNGListener.class)

public class LoginTests extends ApplicationManager {

    private String email, password;

    @BeforeMethod
    public void registration() {
        int i = new Random().nextInt(1000);
        email = "testemail" + i + "@example.com";
        password = "Password123!";
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeRegistrationForm(email, password);
        new ContactsPage(getDriver()).clickBtnSignOut();
    }

    @Test
    public void loginPositiveTest() {
        UserDTO user = new UserDTO(email, password);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
        Assert.assertTrue(new ContactsPage(getDriver()).isSignOutPresent());
    }

    @Test
    public void loginNegativeTest_emptyEmail() {
        UserDTO user = new UserDTO("", password);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
        new LoginPage(getDriver()).closeAlert();
        takeScreenShot((TakesScreenshot) getDriver());
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Login Failed with code 401"));
    }

    @Test
    public void loginNegativeTest_emptyPassword() {
        UserDTO user = new UserDTO(email, "");
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Login Failed with code 401"));
    }

    @Test
    public void loginNegativeTest_wrongEmail() {
        UserDTO user = new UserDTO("testemailexample.com", password); // An email is missing an at symbol
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Login Failed with code 401"));
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        UserDTO user = new UserDTO(email, "Password123");
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Login Failed with code 401"));
    }

}
