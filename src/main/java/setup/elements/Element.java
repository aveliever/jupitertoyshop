package setup.elements;

import static java.util.stream.Collectors.joining;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static setup.config.DriverFactory.getDriver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import setup.utilities.ImplicitWait;
import setup.logger.Log;
import setup.utilities.ExplicitWait;
import org.openqa.selenium.interactions.Actions;

/**
 * This Class is used to handle a <b>Generic Element</b>.
 *
 */
public class Element {

	protected final String description;
	protected final By locator;
	protected final ExplicitWait wait;

	/**
	 * To create a generic element.
	 * 
	 * @param description description of the element
	 * @param locator     locator of the element
	 */
	public Element(String description, By locator) {
		this.description = description;
		this.locator = locator;
		wait = new ExplicitWait();
	}

	/**
	 * To provide the description
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * To provide the description
	 *
	 * @return description
	 */
	public By getLocator() {
		return locator;
	}

	/**
	 * To provide the selenium web element.
	 * 
	 * @return web element
	 */
	public WebElement getWebElement() {
		Log.info("Get the [" + description + "] web element");
		return wait.until(visibilityOfElementLocated(locator));
	}

	/**
	 * Get the text from the element.
	 * 
	 * @return the text.
	 */
	public String getText() {
		String text = wait.until(visibilityOfElementLocated(locator)).getText();
		Log.info("Text from the [" + description + "] element = " + text);
		return text;
	}

	/**
	 * Get the value of the "value"" attribute. (Created this method since it's used most of the time)
	 *
	 * @return value of the attribute "value"
	 */
	public String getAttributeValue() {
		String value = wait.until(visibilityOfElementLocated(locator)).getAttribute("value");
		Log.info("Value of the attribute [value] from [" + description + "] element = " + value);
		return value;
	}

	/**
	 * Get the value of the specified attribute.
	 *
	 * @param attribute attribute whose value is needed
	 * @return value of the attribute
	 */
	public String getAttributeValue(String attribute) {
		String value = wait.until(visibilityOfElementLocated(locator)).getAttribute(attribute);
		Log.info("Value of the attribute [" + attribute + "] from [" + description + "] element = " + value);
		return value;
	}

	/**
	 * Get the value of the specified CSS property.
	 * 
	 * @param property CSS property whose value is needed
	 * @return value of the CSS property
	 */
	public String getCssPropertyValue(String property) {
		String value = wait.until(visibilityOfElementLocated(locator)).getCssValue(property);
		Log.info("Value of the CSS property [" + property + "] from [" + description + "] element = " + value);
		return value;
	}

	/**
	 * Checks whether the element is visible.
	 *
	 * @return true if visible, false otherwise
	 */
	public boolean isVisible() {
		boolean state;
		try {
			state = wait.until(visibilityOfElementLocated(locator)).isDisplayed();
		} catch (TimeoutException e) {
			state = false;
		}

		Log.info("Is [" + description + "] element visible? = " + state);
		return state;
	}

	/**
	 * Checks whether the element is visible immediately.
	 *
	 * @return true if visible, false otherwise
	 */
	public boolean isVisible(Duration waitTime) {
		boolean state;
		WebDriverWait wait = new WebDriverWait(getDriver(),waitTime);
		try {
			state = wait.until(visibilityOfElementLocated(locator)).isDisplayed();
		} catch (TimeoutException e) {
			state = false;
		}

		Log.info("Is [" + description + "] element visible? = " + state);
		return state;
	}

	/**
	 * Checks whether the element is selected.
	 *
	 * @return true if visible, false otherwise
	 */
	public boolean isSelected() {
		boolean state;
		try {
			state = wait.until(visibilityOfElementLocated(locator)).isSelected();
		} catch (TimeoutException e) {
			state = false;
		}

		Log.info("Is [" + description + "] element selected? = " + state);
		return state;
	}

	/**
	 * Checks whether the element is invisible.
	 * 
	 * @return true if invisible, false otherwise
	 */
	public boolean isInvisible() {
		boolean state;
		try {
			state = wait.until(invisibilityOfElementLocated(locator));
		} catch (TimeoutException e) {
			state = false;
		}

		Log.info("Is [" + description + "] element invisible? = " + state);
		return state;
	}

	/**
	 * Checks whether the element is enabled.
	 * 
	 * @return true if enabled, false otherwise
	 */
	public boolean isEnabled() {
		boolean state;
		try {
			state = wait.until(driver -> driver.findElement(locator).isEnabled());
		} catch (TimeoutException e) {
			state = false;
		}

		Log.info("Is [" + description + "] element enabled? = " + state);
		return state;
	}

	/**
	 * Checks whether the element is disabled.
	 * 
	 * @return true if disabled, false otherwise
	 */
	public boolean isDisabled() {
		boolean state;
		try {
			state = wait.until(driver -> !driver.findElement(locator).isEnabled());
		} catch (TimeoutException e) {
			state = false;
		}

		Log.info("Is [" + description + "] element disabled? = " + state);
		return state;
	}

	/**
	 * Move the mouse pointer to the element.
	 */
	public void moveToElement() {
		WebElement element = wait.until(elementToBeClickable(locator));
		new Actions(getDriver()).moveToElement(element).perform();
		Log.info("Moved mouse to [" + description + "] element");
	}

	/**
	 * Right click on an element.
	 */
	public void rightClick() {
		WebElement element = wait.until(elementToBeClickable(locator));
		new Actions(getDriver()).contextClick(element).perform();
		Log.info("Right clicked [" + description + "] element");
	}

	/**
	 * Drag the element to the specified destination element.
	 * 
	 * @param destination element to drop on
	 */
	public void dragTo(Element destination) {
		WebElement source = wait.until(elementToBeClickable(locator));
		WebElement target = wait.until(elementToBeClickable(destination.locator));
		new Actions(getDriver()).dragAndDrop(source, target).perform();
		Log.info("Dragged [" + description + "] element to [" + destination.description + "] element");
	}

	/**
	 * Drag the element to the specified destination element using java script.
	 * 
	 * @param destination element to drop on
	 */
	public void dragToUsingJs(Element destination) {
		WebElement source = wait.until(elementToBeClickable(locator));
		WebElement target = wait.until(elementToBeClickable(destination.locator));
		InputStream in = getClass().getResourceAsStream("/dragdrop.js");
		InputStreamReader isr = new InputStreamReader(in, Charset.defaultCharset());
		String dragDropJs = new BufferedReader(isr).lines().collect(joining("\n"));
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript(dragDropJs, source, target);
		Log.info("Dragged [" + description + "] element to [" + destination.description + "] element");
	}

	/**
	 * Click on the Element.
	 */
	public void click() {
		ImplicitWait.isElementPresent(locator);
		wait.until(elementToBeClickable(locator)).click();
		ImplicitWait.waitForLoadingInvisibility();
		Log.info("Clicked [" + description + "] element");
	}

	/**
	 * Double Click on the Element.
	 */
	public void doubleClick() {
		WebElement element = wait.until(elementToBeClickable(locator));
		new Actions(getDriver()).doubleClick(element).perform();
		ImplicitWait.waitForLoadingInvisibility();
		Log.info("Double clicked [" + description + "] element");
	}

	/**
	 * Double Click on the Element via JS
	 */
	public void doubleClickViaJS() {
		WebElement element = wait.until(elementToBeClickable(locator));

		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("var evt = document.createEvent('MouseEvents'); evt.initMouseEvent('dblclick', true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);arguments[0].dispatchEvent(evt);", element);

		Log.info("Double clicked [" + description + "] element");
	}

	/**
	 * Enter text in the text box.
	 *
	 * @param key text to enter
	 */
	public void enterText(Keys key) {
		WebElement element = wait.until(elementToBeClickable(locator));
		element.sendKeys(key);
	}

	/**
	 * Scroll Into View of Element.
	 */
	public void scrollIntoView() {
		WebElement element = getDriver().findElement(locator);

		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].scrollIntoView();", element);

		Log.info("Scrolled into view of [" + description + "] element");
	}

}
