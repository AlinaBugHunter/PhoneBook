package tests;

import dto.UserContactDTO;
import dto.UserDTO;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.EditContactPage;
import pages.HomePage;
import pages.LoginPage;

public class EditContactTests extends ApplicationManager {

    EditContactPage editContactPage;

    @BeforeMethod
    public void setUpPreconditions() {
        UserDTO user = new UserDTO("testemail@example.com", "Password123!");
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
        new ContactsPage(getDriver()).editFirstContact();
        editContactPage = new EditContactPage(getDriver());
    }

    // TODO: Create a Bug Report
    // When trying to edit the 'description' field, the ability to make changes disappears,
    // and the text '[object Undefined]' appears.
    @Test
    public void editContactPositiveTest_Name() {
        UserContactDTO user = UserContactDTO.builder()
                .name("Peter")
                .lastName("Jackson")
                .phoneNumber("0843626783")
                .email("peter@example.com")
                .address("Pathos, Cyprus")
                .description("Architecture Engineer")
                .build();
        editContactPage.editContactForm(user);
        Assert.assertTrue(new ContactsPage(getDriver()).validateUrl("contacts", 2));
    }

}
