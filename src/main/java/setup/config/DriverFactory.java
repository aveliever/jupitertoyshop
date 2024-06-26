package setup.config;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * To create and handle web driver instances.
 *
 */
public final class DriverFactory {

	private static final List<WebDriverThread> driverThreadPool = Collections
			.synchronizedList(new ArrayList<>());
	private static ThreadLocal<WebDriverThread> driverThread;

	private DriverFactory() {
	}

	/**
	 * Initialize the web driver thread.
	 */
	public static void instantiateDriverObject() {
		driverThread = ThreadLocal.withInitial(DriverFactory::createWebDriverThread);
	}

	/**
	 * Get the current thread's web driver instance.
	 * 
	 * @return web driver instance
	 */
	public static WebDriver getDriver() {
		return driverThread.get().getDriver();
	}

	/**
	 * Clear all cookies in the current thread's web driver instance.
	 */
	public static void clearCookies() {
		getDriver().manage().deleteAllCookies();
	}

	/**
	 * Close the all the driver instances.
	 */
	public static void closeDriverObjects() {
		driverThreadPool.forEach(WebDriverThread::quitDriver);
	}

	/**
	 * Create a web driver thread object.
	 * 
	 * @return web driver thread object
	 */
	private static WebDriverThread createWebDriverThread() {
		WebDriverThread webDriverThread = new WebDriverThread();
		driverThreadPool.add(webDriverThread);
		return webDriverThread;
	}

}
