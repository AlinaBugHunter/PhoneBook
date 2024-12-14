package tests;

import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

import java.util.Random;

public class RegistrationTests extends ApplicationManager {

    @Test
    public void registrationPositiveTest() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeRegistrationForm("testemail" + i + "@example.com", "Password123!");
        Assert.assertTrue(new ContactsPage(getDriver()).isSignOutPresent());
    }

    @Test
    public void registrationNegativeTest_emptyEmail() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeRegistrationForm("", "Password123!");
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

    @Test
    public void registrationNegativeTest_emptyPassword() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeRegistrationForm("testemail" + i + "@example.com", "");
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

    // # TODO: Create a Bug Report
    // The "Email" field allows registration with invalid email addresses,
    // such as "testemail@examplecom", which are missing the "." before the domain.
    @Test
    public void registrationNegativeTest_wrongEmail() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeRegistrationForm("testemail" + i + "@examplecom", "Password123!");
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

    @Test
    public void registrationNegativeTest_wrongEmail1() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver())
                .typeRegistrationForm("testemail" + i + "example.com", "Password123!"); // An email is missing an at symbol
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

    @Test
    public void registrationNegativeTest_wrongEmail2() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver())
                .typeRegistrationForm("@example.com", "Password123!"); // An email is missing the local part
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

    // TODO: Create a Bug Report
    // It is possible to create a new user with just one character after the last '.' in the email
    @Test
    public void registrationNegativeTest_wrongEmail3() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver())
                .typeRegistrationForm("testemail" + i + "@example.c", "Password123!");
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

    @Test
    public void registrationNegativeTest_wrongEmail4() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver())
                .typeRegistrationForm("testemail" + i + "@example.", "Password123!"); // The email is missing the last part after the last '.'
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

    @Test
    public void registrationNegativeTest_wrongEmail5() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver())
                .typeRegistrationForm("testemail" + i + "@@example.com", "Password123!"); // The email includes two '@' symbols
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

    @Test
    public void registrationNegativeTest_wrongPassword() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeRegistrationForm("testemail" + i + "@example.com", "Password123"); // The password is missing a special symbol
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

    @Test
    public void registrationNegativeTest_wrongPassword1() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeRegistrationForm("testemail" + i + "@example.com", "password123!"); // The password is missing an uppercase letter
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

    @Test
    public void registrationNegativeTest_wrongPassword2() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeRegistrationForm("testemail" + i + "@example.com", "PASSWORD123!"); // The password is missing a lowercase letter
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

    @Test
    public void registrationNegativeTest_wrongPassword3() {
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeRegistrationForm("testemail" + i + "@example.com", "Password!"); // The password is missing a digit
        new LoginPage(getDriver()).closeAlert();
        Assert.assertTrue(new LoginPage(getDriver()).validateErrorMessageLoginPage("Registration failed"));
    }

}
