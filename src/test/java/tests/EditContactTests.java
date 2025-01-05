package tests;

import data_provider.DPContact;
import dto.UserContactDTO;
import dto.UserDTO;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ContactsPage;
import pages.EditContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestNGListener;

import static utils.RandomUtils.*;

@Listeners(TestNGListener.class)

public class EditContactTests extends ApplicationManager {

    ContactsPage contactsPage;
    EditContactPage editContactPage;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void setUpPreconditions() {
        UserDTO user = new UserDTO("testemail@example.com", "Password123!");
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
        contactsPage = new ContactsPage(getDriver());
        contactsPage.editFirstContact();
        editContactPage = new EditContactPage(getDriver());
    }

    // TODO: Create a Bug Report
    // When trying to edit the 'description' field, the ability to make changes disappears,
    // and the text '[object Undefined]' appears.
    @Test
    public void editContactPositiveTest() {
        UserContactDTO user = UserContactDTO.builder()
                .name("Maxim")
                .lastName("Jackson")
                .phoneNumber("0843626783")
                .email("peter@example.com")
                .address("Pathos, Cyprus")
                .description("Architecture Engineer")
                .build();
        editContactPage.editContactForm(user);
        softAssert.assertTrue(contactsPage.validateUrl("contacts", 2));
        softAssert.assertTrue(contactsPage.validateContactCard(user));
        softAssert.assertAll();

    }

    @Test
    public void editContactPositiveTest_WODescription() {
        UserContactDTO user = UserContactDTO.builder()
                .name(generateString(7))
                .lastName(generateString(11))
                .phoneNumber(generatePhone(15))
                .email(generateEmail(5))
                .address(generateString(10))
                .build();
        editContactPage.editContactForm(user);
        Assert.assertTrue(contactsPage.validateContactCard(user));
    }

    @Test(dataProvider = "contactDP", dataProviderClass = DPContact.class)
    public void editContactTestDP(UserContactDTO user) {
        editContactPage.editContactForm(user);
        Assert.assertTrue(contactsPage.validateContactCard(user));
    }

    @Test(dataProvider = "contactDPFile", dataProviderClass = DPContact.class)
    public void editContactTestDPFile(UserContactDTO user) {
        editContactPage.editContactForm(user);
        Assert.assertTrue(contactsPage.validateContactCard(user));
    }

    @Test(dataProvider = "contactDP_negativeEmptyFields", dataProviderClass = DPContact.class)
    public void editContactTestDP_negativeEmptyFields(UserContactDTO user) {
        editContactPage.editContactForm(user);
        Assert.assertTrue(editContactPage.btnSaveDisabled());
    }

    @Test(dataProvider = "contactDPFile_negativeInvalidData", dataProviderClass = DPContact.class)
    public void editContactTest_negativeInvalidData(UserContactDTO user) {
        editContactPage.editContactForm(user);
        Assert.assertTrue(editContactPage.btnSaveDisabled());
    }

}
