package tests;

import data_provider.DPContact;
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

    @Test //(invocationCount = 5)
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
    public void addContactNegativeTest_emptyLastName() {
        UserContactDTO user = UserContactDTO.builder()
                .name("John")
                .lastName("")
                .phoneNumber("0534862279")
                .email("test" + randomInt + "@example.com")
                .address("Address")
                .description("")
                .build();
        addContactPage.typeAddContactForm(user);
        Assert.assertFalse(addContactPage.validateURLContacts());
    }

    @Test
    public void addContactNegativeTest_emptyPhoneNumber() {
        UserContactDTO user = UserContactDTO.builder()
                .name("John")
                .lastName("Smith")
                .phoneNumber("")
                .email("test" + randomInt + "@example.com")
                .address("Address")
                .description("")
                .build();
        addContactPage.typeAddContactForm(user);
        String message = addContactPage.closeAlertAndReturnText();
        softAssert.assertTrue(message.contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    // TODO: Create a Bug Report
    // It is possible to create a contact without an email address.
    @Test
    public void addContactNegativeTest_emptyEmail() {
        UserContactDTO user = UserContactDTO.builder()
                .name("John")
                .lastName("Smith")
                .phoneNumber("0112358132134")
                .email("")
                .address("Address")
                .description("description")
                .build();
        addContactPage.typeAddContactForm(user);
        Assert.assertFalse(new ContactsPage(getDriver()).validateLastElementContactList(user));
    }

    @Test
    public void addContactNegativeTest_emptyAddress() {
        UserContactDTO user = UserContactDTO.builder()
                .name("John")
                .lastName("Smith")
                .phoneNumber("0112358132134")
                .email("test" + randomInt + "@example.com")
                .address("")
                .description("description")
                .build();
        addContactPage.typeAddContactForm(user);
        Assert.assertFalse(addContactPage.validateURLContacts());
    }

    @Test
    public void addContactNegativeTest_spaceName() {
        UserContactDTO user = UserContactDTO.builder()
                .name(" ")
                .lastName("Smith")
                .phoneNumber("0112358132134")
                .email("test" + randomInt + "@example.com")
                .address("Israel")
                .description("HR Manager")
                .build();
        addContactPage.typeAddContactForm(user);
        Assert.assertFalse(addContactPage.validateURLContacts());
    }

    @Test
    public void addContactNegativeTest_spaceLastName() {
        UserContactDTO user = UserContactDTO.builder()
                .name("John")
                .lastName(" ")
                .phoneNumber("0112358132134")
                .email("test" + randomInt + "@example.com")
                .address("Israel")
                .description("HR Manager")
                .build();
        addContactPage.typeAddContactForm(user);
        Assert.assertFalse(addContactPage.validateURLContacts());
    }

    @Test
    public void addContactNegativeTest_spacePhoneNumber() {
        UserContactDTO user = UserContactDTO.builder()
                .name("John")
                .lastName("Smith")
                .phoneNumber(" ")
                .email("test" + randomInt + "@example.com")
                .address("Israel")
                .description("HR Manager")
                .build();
        addContactPage.typeAddContactForm(user);
        String message = addContactPage.closeAlertAndReturnText();
        softAssert.assertTrue(message.contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    @Test
    public void addContactNegativeTest_spaceEmail() {
        UserContactDTO user = UserContactDTO.builder()
                .name("John")
                .lastName("Smith")
                .phoneNumber("0112358132134")
                .email(" ")
                .address("Israel")
                .description("HR Manager")
                .build();
        addContactPage.typeAddContactForm(user);
        String message = addContactPage.closeAlertAndReturnText();
        softAssert.assertTrue(message.contains("Email not valid: must be a well-formed email address"));
        Assert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    @Test
    public void addContactNegativeTest_spaceAddress() {
        UserContactDTO user = UserContactDTO.builder()
                .name("John")
                .lastName("Smith")
                .phoneNumber("0112358132134")
                .email("test" + randomInt + "@example.com")
                .address(" ")
                .description("HR Manager")
                .build();
        addContactPage.typeAddContactForm(user);
        Assert.assertFalse(addContactPage.validateURLContacts());
    }

    @Test
    public void addContactNegativeTest_wrongName() {
        UserContactDTO user = UserContactDTO.builder()
                .name("%##$@@") // SUGGESTION: Add validation for the Name field to allow only letters.
                .lastName("Smith")
                .phoneNumber("0112358132134")
                .email("test" + randomInt + "@example.com")
                .address("Israel")
                .description("HR Manager")
                .build();
        addContactPage.typeAddContactForm(user);
        Assert.assertFalse(addContactPage.validateURLContacts());
    }

    @Test
    public void addContactNegativeTest_wrongLastName() {
        UserContactDTO user = UserContactDTO.builder()
                .name("John")
                .lastName("#$$#@@#") // SUGGESTION: Add validation for the Last Name field to allow only letters.
                .phoneNumber("0112358132134")
                .email("test" + randomInt + "@example.com")
                .address("Tel Aviv")
                .description("HR Manager")
                .build();
        addContactPage.typeAddContactForm(user);
        Assert.assertFalse(addContactPage.validateURLContacts());
    }

    @Test
    public void addContactNegativeTest_wrongPhoneNumber() {
        UserContactDTO user = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("0534862test") // Phone number contain not only digits
                .email("test" + randomInt + "@example.com")
                .address("Address")
                .description("description")
                .build();
        addContactPage.typeAddContactForm(user);
        String message = addContactPage.closeAlertAndReturnText();
        softAssert.assertTrue(message.contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    @Test
    public void addContactNegativeTest_wrongPhoneNumber1() {
        UserContactDTO user = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("123456789") // Phone number contain less than 10 digits
                .email("test" + randomInt + "@example.com")
                .address("Address")
                .description("description")
                .build();
        addContactPage.typeAddContactForm(user);
        String message = addContactPage.closeAlertAndReturnText();
        softAssert.assertTrue(message.contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    @Test
    public void addContactNegativeTest_wrongPhoneNumber2() {
        UserContactDTO user = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("012345678910111213") // Phone number contain more than 15 digits
                .email("test" + randomInt + "@example.com")
                .address("Address")
                .description("description")
                .build();
        addContactPage.typeAddContactForm(user);
        String message = addContactPage.closeAlertAndReturnText();
        softAssert.assertTrue(message.contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    @Test
    public void addContactNegativeTest_wrongPhoneNumber3() {
        UserContactDTO user = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("+972534720064") // SUGGESTION: A phone number can contain special symbols
                .email("test" + randomInt + "@example.com")
                .address("Address")
                .description("description")
                .build();
        addContactPage.typeAddContactForm(user);
        String message = addContactPage.closeAlertAndReturnText();
        softAssert.assertTrue(message.contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    @Test
    public void addContactNegativeTest_wrongPhoneNumber4() {
        UserContactDTO user = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("053 472 0064") // A phone number contains spaces
                .email("test" + randomInt + "@example.com")
                .address("Address")
                .description("description")
                .build();
        addContactPage.typeAddContactForm(user);
        String message = addContactPage.closeAlertAndReturnText();
        softAssert.assertTrue(message.contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    @Test
    public void addNewContactNegativeTest_wrongEmail(){
        UserContactDTO contact = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("05365362782")
                .email("testgmail.com") // An email is missing an at symbol
                .address("Israel")
                .description("Test")
                .build();
        addContactPage.typeAddContactForm(contact);
        String message = addContactPage.closeAlertAndReturnText();
        softAssert.assertTrue(message.contains("Email not valid: must be a well-formed email address"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    // TODO: Create a Bug Report
    // It is possible to submit a form with an invalid email if the domain is incorrect
    @Test
    public void addNewContactNegativeTest_wrongEmail1(){
        UserContactDTO contact = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("05365362782")
                .email("test@wrong.domain")
                .address("Israel")
                .description("Test")
                .build();
        addContactPage.typeAddContactForm(contact);
        String message = addContactPage.closeAlertAndReturnText();
        softAssert.assertTrue(message.contains("Email not valid: must be a well-formed email address"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    @Test
    public void addNewContactNegativeTest_wrongEmail2(){
        UserContactDTO contact = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("05365362782")
                .email("@wrong.domain") // An email is missing the local part
                .address("Israel")
                .description("Test")
                .build();
        addContactPage.typeAddContactForm(contact);
        String message = addContactPage.closeAlertAndReturnText();
        softAssert.assertTrue(message.contains("Email not valid: must be a well-formed email address"));
        softAssert.assertFalse(addContactPage.validateURLContacts());
        softAssert.assertAll();
    }

    @Test
    public void addNewContactNegativeTest_wrongAddress(){
        UserContactDTO contact = UserContactDTO.builder()
                .name("Test")
                .lastName("Test")
                .phoneNumber("05365362782")
                .email("test" + randomInt +"@example.com")
                .address("@%^&*!") // SUGGESTION: The address field can't include only special symbols
                .description("Test")
                .build();
        addContactPage.typeAddContactForm(contact);
        Assert.assertTrue(addContactPage.validateURLContacts());
    }

    @Test(dataProvider = "newContactDP", dataProviderClass = DPContact.class)
    public void addContactDPTest(UserContactDTO user) {
        addContactPage.typeAddContactForm(user);
        Assert.assertTrue(new ContactsPage(getDriver()).validateLastElementContactList(user));
    }

    @Test(dataProvider = "newContactDPFile", dataProviderClass = DPContact.class)
    public void addContactDPFileTest(UserContactDTO user) {
        addContactPage.typeAddContactForm(user);
        Assert.assertTrue(new ContactsPage(getDriver()).validateLastElementContactList(user));
    }

    @Test(dataProvider = "newContactDP_negativeEmptyFields", dataProviderClass = DPContact.class)
    public void addContactDPTest_negativeEmptyFields(UserContactDTO user) {
        addContactPage.typeAddContactForm(user);
        if (user.getName().isEmpty()) {
            softAssert.assertFalse(addContactPage.validateURLContacts());
        }
        if (user.getLastName().isEmpty()) {
            softAssert.assertFalse(addContactPage.validateURLContacts());
        }
        if (user.getPhoneNumber().isEmpty()) {
            String message = addContactPage.closeAlertAndReturnText();
            softAssert.assertTrue(message.contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"));
            softAssert.assertFalse(addContactPage.validateURLContacts());
        }
        if (user.getEmail().isEmpty()) {
            softAssert.assertFalse(new ContactsPage(getDriver()).validateLastElementContactList(user));
        }
        if (user.getAddress().isEmpty()) {
            softAssert.assertFalse(addContactPage.validateURLContacts());
        }
        if (user.getDescription().isEmpty()) {
            softAssert.assertTrue(new ContactsPage(getDriver()).validateLastElementContactList(user));
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "newContactDPFile_negativeInvalidData", dataProviderClass = DPContact.class)
    public void addContactDPFileTest_negativeInvalidData(UserContactDTO user) {
        addContactPage.typeAddContactForm(user);
        if (!addContactPage.isValidPhoneNumber(user.getPhoneNumber())) {
            String message = addContactPage.closeAlertAndReturnText();
            softAssert.assertTrue(message.contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"));
            softAssert.assertFalse(addContactPage.validateURLContacts());
        }
        if (!addContactPage.isValidEmail(user.getEmail())) {
            String message = addContactPage.closeAlertAndReturnText();
            softAssert.assertTrue(message.contains("Email not valid: must be a well-formed email address"));
            softAssert.assertFalse(addContactPage.validateURLContacts());
        }
        softAssert.assertAll();
    }

}
