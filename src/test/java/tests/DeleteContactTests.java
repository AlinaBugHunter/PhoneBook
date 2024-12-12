package tests;

import dto.UserDTO;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

public class DeleteContactTests extends ApplicationManager {

    ContactsPage contactsPage;

    @BeforeMethod
    public void setUpPreconditions() {
        UserDTO user = new UserDTO("testemail@example.com", "Password123!");
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
        contactsPage = new ContactsPage(getDriver());
    }

    @Test
    public void deleteContactPositiveTest() {
        int quantityBeforeDelete = contactsPage.quantityContacts();
        System.out.println("Quantity before delete: " + quantityBeforeDelete);
        contactsPage.deleteFirstContact();
        int quantityAfterDelete = contactsPage.quantityContacts();
        System.out.println("Quantity after delete: " + quantityAfterDelete);
        Assert.assertEquals(quantityBeforeDelete-1, quantityAfterDelete);
    }

}
