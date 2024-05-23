package setup.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import setup.config.DriverFactory;

import java.time.Duration;
import java.util.function.Function;

/**
 * To handle conditions that require synchronization.
 *
 */
public final class ExplicitWait {

	private Duration timeout, polling;
	private Wait<WebDriver> wait;

	/**
	 * Create wait object with default timeout and polling interval.
	 */
	public ExplicitWait() {
		timeout = Duration.ofMinutes(2);
		polling = Duration.ofSeconds(2);
		wait = new WebDriverWait(DriverFactory.getDriver(), timeout, polling);
	}

	/**
	 * Create wait object with specified timeout and polling interval.
	 * 
	 * @param timeout time to wait
	 * @param polling poll interval
	 */
	public ExplicitWait(Duration timeout, Duration polling) {
		wait = new WebDriverWait(DriverFactory.getDriver(), timeout, polling);
	}

	/**
	 * Wait until the specified expected condition is met.
	 * 
	 * @param <R>               the output to return
	 * @param expectedCondition condition to wait
	 * @return output
	 */
	public <R> R until(Function<WebDriver, R> expectedCondition) {
		return wait.until(expectedCondition);
	}

}
