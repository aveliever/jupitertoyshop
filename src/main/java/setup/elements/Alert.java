package setup.elements;

import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

import setup.logger.Log;
import setup.utilities.ExplicitWait;
import org.openqa.selenium.TimeoutException;

/**
 * This Class is used to handle <b>Alerts</b>.
 *
 */
public final class Alert {

	private final String description;
	private final ExplicitWait wait;

	/**
	 * This Constructor is used to create an object to access an <b>Alert</b>.
	 * 
	 * @param description description of the Alert
	 */
	public Alert(String description) {
		wait = new ExplicitWait();
		this.description = description;
	}

	/**
	 * To check if an alert is present.
	 * 
	 * @return true if present, false otherwise
	 */
	public boolean isPresent() {
		try {
			wait.until(alertIsPresent());
			Log.info("Alert [" + description + "] is present");
			return true;
		} catch (TimeoutException e) {
			Log.info("Alert [" + description + "] is not present");
			return false;
		}
	}

	/**
	 * Accept the alert.
	 */
	public void accept() {
		wait.until(alertIsPresent()).accept();
		Log.info("Accepted the [" + description + "] alert");
	}

	/**
	 * Dismiss the alert.
	 */
	public void dismiss() {
		wait.until(alertIsPresent()).dismiss();
		Log.info("Dismissed the [" + description + "] alert");
	}

	/**
	 * Get the text present in an alert.
	 * 
	 * @return the text
	 */
	public String getText() {
		String alertText = wait.until(alertIsPresent()).getText();
		Log.info("Text from the [" + description + "] alert = " + alertText);
		return alertText;
	}

	/**
	 * Enter text in the alert.
	 * 
	 * @param textToEnter text to enter
	 */
	public void enterText(String textToEnter) {
		wait.until(alertIsPresent()).sendKeys(textToEnter);
		Log.info("Entered text [" + textToEnter + "] in the [" + description + "] text box");
	}

}
