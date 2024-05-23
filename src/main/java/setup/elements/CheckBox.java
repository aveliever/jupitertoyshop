package setup.elements;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static setup.config.DriverFactory.getDriver;

import org.openqa.selenium.interactions.Actions;
import setup.logger.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import setup.utilities.ImplicitWait;

import java.time.Duration;

/**
 * This Class is used to handle <b>Check Box</b>.
 */
public final class CheckBox extends Element {

    /**
     * This Constructor is used to create an object to access a <b>CheckBox</b>.
     * @param description description of the checkbox
     * @param locator     locator of the checkbox
     */
    public CheckBox(String description, By locator) {
        super(description, locator);
    }

    /**
     * Check the checkbox.
     */
    public void check() {
        WebElement element = wait.until(elementToBeClickable(locator));
        if (element.isSelected()) {
            Log.info("Checkbox [" + description + "] is already checked");
        } else {
            element.click();
            Log.info("Checked [" + description + "] checkbox");
        }
        ImplicitWait.waitForLoadingInvisibility();
    }

    /**
     * Un-check the checkbox.
     */
    public void uncheck() {
        WebElement element = wait.until(elementToBeClickable(locator));
        if (element.isSelected()) {
            element.click();
            Log.info("Unchecked [" + description + "] checkbox");
        } else {
            Log.info("Checkbox [" + description + "] is already unchecked");
        }
        ImplicitWait.waitForLoadingInvisibility();
    }

    /**
     * Is the checkbox checked.
     * @return true if checked, false if un-checked
     */
    public boolean isChecked() {
        ImplicitWait.waitForLoadingInvisibility();
        WebElement element = wait.until(visibilityOfElementLocated(locator));
        boolean state =  element.isSelected();
        Log.info("Is [" + description + "] checkbox checked? = " + state);
        return state;
    }


    public void setCheckboxState(boolean state){
        WebElement element = wait.until(elementToBeClickable(locator));
        if (element.isSelected() == state) {
            Log.info("Checkbox [" + description + "] was not clicked. Current state is already " + state);
            ImplicitWait.waitForLoadingInvisibility();
            return;
        }
        element.click();
        Log.info("Checkbox [" + description + "]  was changed to " + state);
        ImplicitWait.waitForLoadingInvisibility();
    }

}
