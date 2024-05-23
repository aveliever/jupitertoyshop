package setup.config;

import org.openqa.selenium.WebDriver;
import setup.logger.Log;

import java.time.Duration;
import java.util.Optional;

/**
 * To create a thread safe web driver instances.
 *
 */
public final class WebDriverThread {

	private WebDriver driver;
	private DriverType selectedDriverType;
	public static final DriverType defaultDriverType = DriverType.CHROME;
	private final Optional<String> browser = Optional.ofNullable(System.getProperty("browser"));
	private final String operatingSystem = System.getProperty("os.name").toUpperCase();
	private final String systemArchitecture = System.getProperty("os.arch").toUpperCase();
	public static Duration INTERVAL_WAIT = Duration.ofSeconds(2);
	public static Duration MAX_WAIT = Duration.ofSeconds(120);

	/**
	 * Quit the web driver instance.
	 */
	public void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	/**
	 * Get the web driver instance.
	 *
	 * @return web driver instance
	 */
	public WebDriver getDriver() {
		if (driver == null) {
			selectedDriverType = determineEffectiveDriverType();
			instantiateWebDriver();
		}
		return driver;
	}

	/**
	 * Get the browser's driver type to use.
	 *
	 * @return browser's driver type
	 */
	private DriverType determineEffectiveDriverType() {
		DriverType driverType = defaultDriverType;
		try {
			driverType = DriverType.valueOf(browser.orElse("No browser specified").toUpperCase());
		} catch (IllegalArgumentException e) {
			Log.warn("No specified browser, defaulting to '" + driverType + "'...");
		}
		return driverType;
	}

	/**
	 * Create a new web driver instance.
	 *
	 */
	private void instantiateWebDriver() {
		Log.info("[Device Config] Operating System: '" + operatingSystem + "', System Architecture: '" + systemArchitecture + "', Browser Selection: '" + selectedDriverType +"'");
		driver = selectedDriverType.getWebDriverObject();
		driver.manage().window().maximize();
	}

}
