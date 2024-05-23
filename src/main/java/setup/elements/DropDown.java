package setup.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import setup.logger.Log;
import setup.utilities.ImplicitWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static setup.config.DriverFactory.getDriver;

/**
 * This Class is used to handle <b>Drop Down</b>.
 */
public final class DropDown extends Element {

    /**
     * This Constructor is used to create an object to access a <b>Drop Down</b>.
     *
     * @param description description of the dropdown
     * @param locator     locator of the dropdown
     */
    public DropDown(String description, By locator) {
        super(description, locator);
    }

    /**
     * Select the dropdown option by index.
     *
     * @param index the index to be selected
     */
    public void selectByIndex(int index) {
        WebElement element = wait.until(elementToBeClickable(locator));
        element.click();
        Log.info("Clicked [" + description + "] drop down");

        //Special dropdown option selection due to element is not an actual select tag
        String locator = "div.ListView-ListViewDefaultStyle-item";
        List<WebElement> elements = getDriver().findElements(By.cssSelector(locator));
        WebElement option = wait.until(elementToBeClickable(elements.get(index)));
        option.click();
        Log.info("Selected option index [" + index + "] from the [" + description + "] drop down");
    }

    /**
     * Select the dropdown option by text.
     *
     * @param visibleText the text to be selected
     */
    public void selectByVisibleText(String visibleText) {
        ImplicitWait.waitForPageLoad();
        WebElement element = wait.until(elementToBeClickable(locator));
        element.click();
        Log.info("Clicked [" + description + "] drop down");

        //Special dropdown option selection due to element is not an actual select tag
        String patternItemByTextLocator = "//*[contains(@class, 'ListView-ListViewDefaultStyle-item') and text()='<TEXT>']";
        String xpath = patternItemByTextLocator.replace("<TEXT>", visibleText);
        WebElement option = getDriver().findElement(By.xpath(xpath));
        wait.until(elementToBeClickable(option));
        option.click();
        ImplicitWait.waitForLoadingInvisibility();
        Log.info("Selected option text [" + visibleText + "] from the [" + description + "] drop down");
    }

    /**
     * Get the number of options present in the dropdown
	 * Used method 'getNumberOfElements' of Elements
     *
     * @return number of options
     */
    public int getNumberOfOptionsByListItem() {
        WebElement element = wait.until(elementToBeClickable(locator));
        element.click(); //Click Dropdown to open list
        Log.info("Clicked [" + description + "] drop down");

        int numOptions = 0;

        try{
            numOptions = getDriver().findElements(By.cssSelector("div.ListView-ListViewDefaultStyle-item")).size();
            Log.info("Number of options from the [" + description + "] drop down = " + numOptions);
        }catch (Exception e){
            Log.warn("No options available in the dropdown");
        }

        element.click(); //Click Dropdown to close the list
        Log.info("Closed [" + description + "] drop down");
        return numOptions;
    }

    /**
     * Get text from all elements and store it in List<String>
     *
     * @return itemsText
     */

    public List<String> getAllOptions() {
        Log.info("Get all the options from the [" + description + "] drop down");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        WebElement element = wait.until(elementToBeClickable(locator));
        element.click();
        Log.info("Clicked [" + description + "] drop down");

        List<String> itemsText = null;

        try{
            String locator = "div.ListView-ListViewDefaultStyle-item";
            String[] elements = wait.until(visibilityOfAllElementsLocatedBy(By.cssSelector(locator))).stream().map(WebElement::getText)
                    .toArray(String[]::new);

            itemsText = new ArrayList<>();
            for (String s : elements) {
                itemsText.add(s);
            }
            Log.info(description + " Dropdown Options {" + String.join(", ", elements) + "}");
        }catch (Exception e){
            Log.warn("No options available in dropdown");
        }

        element.click();
        Log.info("Closed [" + description + "] drop down");
        return itemsText;
    }

    /**
     * Special method that checks if a certain value is present in Dropdown
     *
     * @param value is the target value
     * @return boolean (true/false)
     */
    public boolean isValuePresentInDropDown(String value) {
        WebElement element = wait.until(elementToBeClickable(locator));
        try {
            List<String> itemsText = getAllOptions();
            element.click();
            Log.info("Clicked [" + description + "] drop down");

            if (itemsText.contains(value)) {
                Log.info("Dropdown contains value: " + value);
                element.click();
                return true;
            } else {
                Log.info("Dropdown does NOT contain value: " + value);
                element.click();
                return false;
            }

        } catch (Exception e) {
            Log.warn("No dropdown value.");
            return false;
        }

    }

}
