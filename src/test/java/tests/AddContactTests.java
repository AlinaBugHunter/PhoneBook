package tests;

import dto.UserContactDTO;
import dto.UserDTO;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddContactPage;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

import java.util.Random;

public class AddContactTests extends ApplicationManager {

    AddContactPage addContactPage;

    int randomInt = new Random().nextInt(1000) + 1000;

    @BeforeMethod
    public void setUpPreconditions() {
        UserDTO user = new UserDTO("testemail@example.com", "Password123!");
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
        new ContactsPage(getDriver()).clickBtnAdd();
        addContactPage = new AddContactPage(getDriver());
    }

    @Test
    public void addContactPositiveTest() {
        UserContactDTO user = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("0534862279")
                .email("test" + randomInt + "@example.com")
                .address("Address")
                .description("Lorem Ipsum")
                .build();
        addContactPage.typeAddContactForm(user);
        addContactPage.clickBtnSave();
        Assert.assertTrue(new ContactsPage(getDriver()).validateLastElementContactList(user));
    }

}
