package setup.elements;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import setup.utilities.ImplicitWait;
import setup.base.BasePage;
import setup.logger.Log;
import org.openqa.selenium.By;

import java.time.Duration;

/**
 * This Class is used to handle <b>Button</b>.
 *
 */
public final class Button extends Element {

	/**
	 * This Constructor is used to create an object to access a <b>Button</b>.
	 * 
	 * @param description description of the Button
	 * @param locator     locator of the Button
	 */
	public Button(String description, By locator) {
		super(description, locator);
	}

	/**
	 * Click on the button.
	 */
	public void click() {
		ImplicitWait.waitForLoadingInvisibility();
		ImplicitWait.isElementPresent(locator);
		wait.until(elementToBeClickable(locator)).click();
		ImplicitWait.waitForLoadingInvisibility();
		Log.info("Clicked [" + description + "] button");
	}
	public void click(Duration timeout) {
		ImplicitWait.waitForLoadingInvisibility();
		ImplicitWait.isElementPresent(locator);
		wait.until(elementToBeClickable(locator)).click();
		ImplicitWait.waitForLoadingInvisibility(timeout);
		Log.info("Clicked [" + description + "] button");
	}

	/**
	 * Click on the button.
	 * 
	 * @param <T>       the type of the page class
	 * @param pageClass expected class of the page after the click
	 * @return the pageClass object
	 */
	public <T extends BasePage> T click(Class<T> pageClass) {
		Log.info("Click [" + description + "] button");
		try {
			wait.until(elementToBeClickable(locator)).click();
			return pageClass.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			Log.error("Unable to create instance of the page class", e);
			throw new RuntimeException("Unable to create instance of the page class", e);
		}
	}
}
