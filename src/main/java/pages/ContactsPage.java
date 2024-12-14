package pages;

import dto.UserContactDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactsPage extends BasePage {

    public ContactsPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[@href='/add']")
    WebElement btnAdd;

    public void clickBtnAdd() {
        btnAdd.click();
    }

    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM']")
    WebElement firstElementContactList;

    @FindBy(xpath = "//button[text()='Edit']")
    WebElement btnEdit;

    public void editFirstContact() {
        firstElementContactList.click();
        btnEdit.click();
    }

    @FindBy(xpath = "//button[text()='Remove']")
    WebElement btnRemove;

    public void deleteFirstContact() {
        clickWait(firstElementContactList, 3);
        clickWait(btnRemove, 3);
        pause(3);
    }

    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']/div/div[last()]")
    WebElement lastElementContactList;

    public boolean validateLastElementContactList(UserContactDTO user) {
        return lastElementContactList.getText().contains(user.getName());
    }

    public int quantityContacts() {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By
                                .xpath("//div[@class='contact-item_card__2SOIM']"))).size();
    }

    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOut;

    public void clickBtnSignOut() {
        btnSignOut.click();
    }

    public boolean isSignOutPresent() {
        return btnSignOut.isDisplayed();
    }

}
