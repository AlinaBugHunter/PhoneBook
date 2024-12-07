package tests;

import dto.UserDTO;
import manager.ApplicationManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

public class ContactsTests extends ApplicationManager {

    @BeforeMethod
    public void login() {
        UserDTO user = new UserDTO("testemail@example.com", "Password123!");
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
    }

    @Test
    public void addContact() {
        new ContactsPage(getDriver()).clickBtnAdd();
    }

}
