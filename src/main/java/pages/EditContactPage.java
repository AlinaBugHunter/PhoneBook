package pages;

import dto.UserContactDTO;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class EditContactPage extends BasePage {

    public EditContactPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastName;

    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputPhone;

    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement inputEmail;

    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement inputAddress;

    @FindBy(xpath = "//input[@placeholder='desc']")
    WebElement inputDescription;

    @FindBy(xpath = "//button[text()='Save']")
    WebElement btnSave;

    public void editContactForm(UserContactDTO user) {
        inputName.clear();
        inputName.sendKeys(user.getName());
        inputLastName.clear();
        inputLastName.sendKeys(user.getLastName());
        inputPhone.clear();
        inputPhone.sendKeys(user.getPhoneNumber());
        inputEmail.clear();
        inputEmail.sendKeys(user.getEmail());
        inputAddress.clear();
        inputAddress.sendKeys(user.getAddress());
//      inputDescription.clear();
//      inputDescription.sendKeys(user.getDescription());
        btnSave.click();
    }
}
