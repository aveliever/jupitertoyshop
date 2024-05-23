package setup.elements;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import setup.logger.Log;
import org.openqa.selenium.By;

/**
 * This Class is used to handle <b>Radio Button</b>.
 *
 */
public final class RadioButton extends Element {

	/**
	 * This Constructor is used to create an object to access a <b>RadioButton</b>.
	 * 
	 * @param description description of the RadioButton
	 * @param locator     locator of the RadioButton
	 */
	public RadioButton(String description, By locator) {
		super(description, locator);
	}

	/**
	 * Click on the radio button.
	 */
	public void click() {
		wait.until(elementToBeClickable(locator)).click();
		Log.info("Clicked [" + description + "] radio button");
	}

	/**
	 * Is the radio button selected.
	 * 
	 * @return true if selected, false otherwise
	 */
	public boolean isSelected() {
		boolean state = wait.until(elementToBeClickable(locator)).isSelected();
		Log.info("Is [" + description + "] radio button selected? = " + state);
		return state;
	}
}
