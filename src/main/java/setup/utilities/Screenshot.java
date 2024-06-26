package setup.utilities;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.openqa.selenium.OutputType.BASE64;
import static org.openqa.selenium.OutputType.FILE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import setup.logger.Log;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import setup.config.DriverFactory;

/**
 *
 * To take Screenshots
 *
 */
public final class Screenshot {

	private Screenshot() {
	}

	/**
	 * Take <b>Screenshot</b> of the current page.
	 * 
	 * @return base64 string
	 */
	public static synchronized String takeScreenShot() {
		String base64 = "";
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss.SSS");
		String fileName = "ScreenShot_" + datePattern.format(dateTime) + ".png";
		Path screenshot = Paths.get(System.getProperty("user.dir"), "target", "screenshots", fileName);
		try {
			if (Files.notExists(screenshot.getParent(), NOFOLLOW_LINKS))
				Files.createDirectory(screenshot.getParent());
			try {
				base64 = writeScreenshotToFile(DriverFactory.getDriver(), screenshot);
			} catch (ClassCastException e) {
				WebDriver driver = new Augmenter().augment(DriverFactory.getDriver());
				base64 = writeScreenshotToFile(driver, screenshot);
			}
		} catch (IOException e) {
			Log.error("Unable to take screen shot", e);
		}
		return base64;
	}

	/**
	 * Take <b>Screenshot</b> of the current page.
	 * 
	 * @param name screenshot file name
	 * @return base64 string
	 */
	public static synchronized String takeScreenShot(String name) {
		String base64 = "";
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss.SSS");
		String fileName = name + "_" + datePattern.format(dateTime) + ".png";
		Path screenshot = Paths.get(System.getProperty("user.dir"), "target", "screenshots", fileName);
		try {
			if (Files.notExists(screenshot.getParent(), NOFOLLOW_LINKS))
				Files.createDirectory(screenshot.getParent());
			try {
				base64 = writeScreenshotToFile(DriverFactory.getDriver(), screenshot);
			} catch (ClassCastException e) {
				WebDriver driver = new Augmenter().augment(DriverFactory.getDriver());
				base64 = writeScreenshotToFile(driver, screenshot);
			}
		} catch (IOException e) {
			Log.error("Unable to take screen shot", e);
		}
		return base64;
	}

	/**
	 * Write the screenshot taken to a file.
	 * 
	 * @param driver     web driver instance
	 * @param screenshot screenshot path
	 * @return base64 string
	 * @throws IOException
	 */
	private static String writeScreenshotToFile(WebDriver driver, Path screenshot) throws IOException {
		Path screenshotOutput = ((TakesScreenshot) driver).getScreenshotAs(FILE).toPath();
		Files.copy(screenshotOutput, screenshot, REPLACE_EXISTING);
		return ((TakesScreenshot) driver).getScreenshotAs(BASE64);
	}

}
