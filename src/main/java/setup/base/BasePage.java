package setup.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import setup.elements.Element;
import setup.logger.Log;

import static setup.config.DriverFactory.getDriver;


/**
 * To extend every page class created.
 *
 */
public abstract class BasePage {

	private String parentWindow;

	/**
	 * Open the specified URL.
	 *
	 * @param url URL to open
	 */
	public static void openUrl(String url) {
		getDriver().get(url);
		Log.info("Navigated to [" + url + "]");
	}

	/**
	 * Get the URL of the current page.
	 *
	 * @return page URL
	 */
	public static String getPageUrl() {
		String url = getDriver().getCurrentUrl();
		Log.info("The Current URL is " + url);
		return url;
	}

	/**
	 * Get the Title of the current page.
	 *
	 * @return page title
	 */
	public String getPageTitle() {
		String title = getDriver().getTitle();
		Log.info("The Current Page Title is " + title);
		return title;
	}

	/**
	 * Refresh the current page.
	 */
	public static void refreshPage() {
		getDriver().navigate().refresh();
		Log.info("Refreshed the browser");
	}

	/**
	 * Switch to the newly opened window.
	 *
	 * @param description description of the new window
	 */
	protected void switchToWindow(String description) {
		Log.info("Switch to window [" + description + "]");
		parentWindow = getDriver().getWindowHandle();
		for (String windowHandle : getDriver().getWindowHandles())
			if (!windowHandle.equals(parentWindow))
				getDriver().switchTo().window(windowHandle);
	}

	/**
	 * Switch to the window containing the specified URL text.
	 *
	 * @param description description of the new window
	 * @param urlText     URL text that the window contains
	 */
	protected void switchToWindowContainingUrlText(String description, String urlText) {
		Log.info("Switch to window [" + description + "] which contains URL text [" + urlText + "]");
		parentWindow = getDriver().getWindowHandle();
		getDriver().getWindowHandles().stream().map(getDriver().switchTo()::window)
				.filter(driver -> driver.getCurrentUrl().contains(urlText)).findFirst()
				.orElseThrow(() -> new NoSuchWindowException(
						"Unable to find window [" + description + "] which contains URL text [" + urlText + "]"));
	}

	/**
	 * Switch to the window containing the specified title.
	 *
	 * @param description description of the new window
	 * @param title       title that the window contains
	 */
	protected void switchToWindowContainingTitle(String description, String title) {
		Log.info("Switch to window [" + description + "] which contains title [" + title + "]");
		parentWindow = getDriver().getWindowHandle();
		getDriver().getWindowHandles().stream().map(getDriver().switchTo()::window)
				.filter(driver -> driver.getTitle().contains(title)).findFirst()
				.orElseThrow(() -> new NoSuchWindowException(
						"Unable to find window [" + description + "] which contains title [" + title + "]"));
	}

	/**
	 * Switch to the Main window.
	 *
	 * @param description description of the main window
	 */
	protected void switchToParentWindow(String description) {
		Log.info("Switch to parent window [" + description + "]");
		getDriver().switchTo().window(parentWindow);
	}

	/**
	 * Switch to the frame containing the specified element.
	 *
	 * @param element     element of the frame
	 */
	protected void switchToFrame(Element element) {
		Log.info("Switch to frame [" + element.getDescription() + "]");
		getDriver().switchTo().frame(element.getWebElement());
	}

	/**
	 * Switch to the frame containing the specified name or ID.
	 *
	 * @param description description of the frame
	 * @param nameOrId    name or ID of the frame
	 */
	protected void switchToFrame(String description, String nameOrId) {
		Log.info("Switch to frame [" + description + "]");
		getDriver().switchTo().frame(nameOrId);
	}

	/**
	 * Switch to the frame containing the specified index number.
	 *
	 * @param description description of the frame
	 * @param index       index number of the frame
	 */
	protected void switchToFrame(String description, int index) {
		Log.info("Switch to frame [" + description + "]");
		getDriver().switchTo().frame(index);
	}

	/**
	 * Switch to the default window.
	 *
	 * @param description description of the window
	 */
	protected void switchToDefaultContent(String description) {
		Log.info("Switch to main window [" + description + "]");
		getDriver().switchTo().defaultContent();
	}


	/**
	 * Execute script using JS Executor
	 * @param script
	 */
	public static void executeJSScript(String script) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript(script);
	}

}
