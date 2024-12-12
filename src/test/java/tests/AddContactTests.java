package tests;

import dto.UserContactDTO;
import dto.UserDTO;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddContactPage;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

import java.util.Random;

public class AddContactTests extends ApplicationManager {

    SoftAssert softAssert = new SoftAssert();
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
        Assert.assertTrue(new ContactsPage(getDriver()).validateLastElementContactList(user));
    }

    @Test
    public void addContactPositiveTest_emptyDescription() {
        UserContactDTO user = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("0534862279")
                .email("test" + randomInt + "@example.com")
                .address("Address")
                .description("")
                .build();
        addContactPage.typeAddContactForm(user);
        Assert.assertTrue(new ContactsPage(getDriver()).validateLastElementContactList(user));
    }

    @Test
    public void addContactNegativeTest_emptyName() {
        UserContactDTO user = UserContactDTO.builder()
                .name("")
                .lastName("Test")
                .phoneNumber("0534862279")
                .email("test" + randomInt + "@example.com")
                .address("Address")
                .description("")
                .build();
        addContactPage.typeAddContactForm(user);
        Assert.assertFalse(addContactPage.validateURLContacts());
    }

    @Test
    public void addContactNegativeTest_wrongPhoneNumber() {
        UserContactDTO user = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("0534862test")
                .email("test" + randomInt + "@example.com")
                .address("Address")
                .description("description")
                .build();
        addContactPage.typeAddContactForm(user);
        String message = addContactPage.closeAlertAndReturnText();
        System.out.println(message);
        softAssert.assertTrue(message.contains("Phone number must contain only digits! And length min 10, max 15!"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    @Test
    public void addNewContactNegativeTest_wrongEmail(){
        UserContactDTO contact = UserContactDTO.builder()
                .name("name 123")
                .lastName("Last name123")
                .phoneNumber("1234567890")
                .email("lastname")
                .address("address st.1")
                .description("description")
                .build();
        addContactPage.typeAddContactForm(contact);
        String message = addContactPage.closeAlertAndReturnText();
        System.out.println(message);
        softAssert.assertTrue(message.contains("Email not valid"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

}
