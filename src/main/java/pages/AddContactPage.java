package pages;

import dto.UserContactDTO;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddContactPage extends BasePage {

    public AddContactPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastName;

    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputPhoneNumber;

    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement inputEmail;

    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement inputAddress;

    @FindBy(xpath = "//input[@placeholder='description']")
    WebElement inputDescription;

    @FindBy(xpath = "//button/b[text()='Save']")
    WebElement btnSave;

    public void typeAddContactForm(UserContactDTO user) {
        inputName.sendKeys(user.getName());
        inputLastName.sendKeys(user.getLastName());
        inputPhoneNumber.sendKeys(user.getPhoneNumber());
        inputEmail.sendKeys(user.getEmail());
        inputAddress.sendKeys(user.getAddress());
        inputDescription.sendKeys(user.getDescription());
        btnSave.click();
    }

    public boolean validateURLContacts(){
        return validateUrl( "contacts", 5);
    }

    public String closeAlertAndReturnText() {
        Alert alert = new WebDriverWait(driver, 5)
                .until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }

    // Method to check that the phone number contains only digits and has a length between 10 and 15 characters
    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{10,15}$");
    }

    // Method to validate email considering both the local and domain parts
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

}
