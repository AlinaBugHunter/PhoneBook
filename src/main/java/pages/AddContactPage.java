package pages;

import dto.UserContactDTO;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

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

    public void typeAddContactForm(UserContactDTO user) {
        inputName.sendKeys(user.getName());
        inputLastName.sendKeys(user.getLastName());
        inputPhoneNumber.sendKeys(user.getPhoneNumber());
        inputEmail.sendKeys(user.getEmail());
        inputAddress.sendKeys(user.getAddress());
        inputDescription.sendKeys(user.getDescription());
    }

    @FindBy(xpath = "//button/b[text()='Save']")
    WebElement btnSave;

    public void clickBtnSave() {
        btnSave.click();
    }

}
