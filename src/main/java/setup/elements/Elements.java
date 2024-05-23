package setup.elements;

import java.util.ArrayList;
import java.util.List;

import setup.logger.Log;
import setup.utilities.ExplicitWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * This Class is used to handle <b>List of Elements</b> in a Web Page.
 *
 */
public final class Elements {

	private final String description;
	private final By locator;
	private final ExplicitWait wait;

	/**
	 * This Constructor is used to create an object to access a <b>Text</b>.
	 * 
	 * @param description description of the text
	 * @param locator     locator of the text
	 */
	public Elements(String description, By locator) {
		this.description = description;
		this.locator = locator;
		wait = new ExplicitWait();
	}

	/**
	 * To provide the selenium web element.
	 * 
	 * @return list of web elements
	 */
	public List<WebElement> getWebElements() {
		Log.info("Get the list of [" + description + "] web elements");
		return wait.until(visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * Get the text from all the text elements with the mentioned locator.
	 *
	 * @return list of texts
	 */
	public List<String> getTextFromAllElements() {
		Log.info("Get the list of text from [" + description + "]");
		String[] elements = wait.until(visibilityOfAllElementsLocatedBy(locator)).stream().map(WebElement::getText)
				.toArray(String[]::new);

		List<String> itemsText = new ArrayList<>();
		for (String item : elements) {
			itemsText.add(item);
			Log.info("Items on the list: "+ item);
		}
		return itemsText;
	}

	/**
	 * Get the text from all the text elements with the mentioned locator.
	 *
	 * @return list of texts
	 */
	public List<String> getTextFromVisibleElementsViaAttribute() {
		Log.info("Get the list of text from [" + description + "]");
		List<WebElement> elements = wait.until(visibilityOfAllElementsLocatedBy(locator));

		List<String> itemsText = new ArrayList<>();
		for (WebElement item : elements) {
			String itemValue = item.getAttribute("value");
			itemsText.add(itemValue);
			Log.info("Items on the list: "+ itemValue);
		}
		return itemsText;
	}

	/**
	 * Get the text from all the text elements even if element is not visible on the current screen
	 *
	 * @return list of texts
	 */
	public List<String> getTextFromAllElementsViaAttribute() {
		Log.info("Get the list of text from [" + description + "]");
		List<WebElement> elements = wait.until(presenceOfAllElementsLocatedBy(locator));

		List<String> itemsText = new ArrayList<>();
		for (WebElement item : elements) {
			String itemValue = item.getAttribute("value");
			itemsText.add(itemValue);
			Log.info("Items on the list: "+ itemValue);
		}
		return itemsText;
	}

	/**
	 * Get the number of elements present for the mentioned locator
	 * 
	 * @return number of elements
	 */
	public int getNumberOfElements() {
		int size = wait.until(visibilityOfAllElementsLocatedBy(locator)).size();
		Log.info("Number of [" + description + "] web elements = " + size);
		return size;
	}

}
