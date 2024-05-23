package setup.config;

import org.openqa.selenium.WebDriver;

/**
 * To provide a setup for driver instance.
 *
 */
public interface DriverSetup {

	/**
	 * Get the web driver object with the specified capabilities.
	 *
	 * @return web driver instance
	 */
	WebDriver getWebDriverObject();


}
