package setup.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import setup.logger.Log;
import setup.utilities.ImplicitWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * This Class is used to handle <b>Text Box</b>.
 */
public final class TextField extends Element {

    /**
     * This Constructor is used to create an object to access a <b>Text Box</b>.
     * @param description description of the text box
     * @param locator     locator of the text box
     */
    public TextField(String description, By locator) {
        super(description, locator);
    }

    /**
     * Enter text in the text box.
     * @param textToEnter text to enter (without pressing ENTER Key)
     */
    public void enterText(String textToEnter) {
        WebElement element = wait.until(elementToBeClickable(locator));
        element.clear();
        element.sendKeys(textToEnter);

        if (description.toLowerCase().contains("password"))
            Log.info("Entered text [********] in the [" + description + "] text box");
        else
            Log.info("Entered text [" + textToEnter + "] in the [" + description + "] text box");
    }

    /**
     * Enter text in the text box.
     * @param textToEnter text to enter plus pressing the ENTER Key to populate or trigger correct field value (especially for dynamic field - Dropdown + TextField)
     */
    public void populateText(String textToEnter) {
        WebElement element = wait.until(elementToBeClickable(locator));
        element.clear();
        element.sendKeys(textToEnter);
        ImplicitWait.sleep(1);
        element.sendKeys(Keys.ENTER);

        if (description.toLowerCase().contains("password"))
            Log.info("Entered text [********] in the [" + description + "] text box");
        else
            Log.info("Entered text [" + textToEnter + "] in the [" + description + "] text box");
    }

    /**
     * Get the text from the text box.
     * @return text
     */
    public String getText() {
        String text = wait.until(elementToBeClickable(locator)).getText();
        Log.info("Text from [" + description + "] text box = " + text);
        return text;

    }

    /**
     * Enter text in the text box.
     * @param key text to enter
     */
    public void enterText(Keys key) {
        WebElement element = wait.until(elementToBeClickable(locator));
        element.sendKeys(key);
        Log.info("Entered Key [" + key + "] in the [" + description + "] text box");
    }


    /**
     * Clear text in the text box.
     */
    public void clearText() {
        WebElement element = wait.until(elementToBeClickable(locator));
        element.clear();

        Log.info("Cleared text in the [" + description + "] text box");
    }

}
