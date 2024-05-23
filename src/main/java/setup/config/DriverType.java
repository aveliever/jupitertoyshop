package setup.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.HashMap;

import static setup.base.BaseTest.DOWNLOADS_DIRECTORY;
import static setup.base.BaseTest.FOR_CHECKING;

/**
 * To handle instances of all the driver types.
 *
 */
public enum DriverType implements DriverSetup {

	/**
	 * This is the Chrome browser driver implementation.
	 */
	CHROME {

		@Override
		public WebDriver getWebDriverObject() {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("enable-automation");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-extensions");
			options.addArguments("--dns-prefetch-disable");
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--remote-allow-origins=*");
			//options.addArguments("incognito");
			options.addArguments("disable-dev-shm-usage");

//			// Set the default download folder
//			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
//			chromePrefs.put("profile.default_content_settings.popups", 0);
//			chromePrefs.put("download.default_directory", DOWNLOADS_DIRECTORY); // Set your download path here
//			chromePrefs.put("download.prompt_for_download", false);
//			chromePrefs.put("safebrowsing.enabled", "true"); // Disable the safe browsing feature that blocks certain downloads
//			options.setExperimentalOption("prefs", chromePrefs);

			WebDriverManager.chromedriver().clearDriverCache().setup();
			return new ChromeDriver(options);
		}
	},

	/**
	 * This is the Edge browser driver implementation.
	 */
	EDGE {
		@Override
		public WebDriver getWebDriverObject() {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("enable-automation");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-extensions");
			options.addArguments("--dns-prefetch-disable");
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
			// For Edge specific, this allows allowing all origins to access file URLs.
			options.addArguments("--allow-file-access-from-files");
			options.addArguments("disable-gpu"); // If necessary
			options.addArguments("disable-dev-shm-usage"); // Overcome limited resource problems
			options.addArguments("--remote-allow-origins=*");

//			// Set the default download folder using preferences
//			HashMap<String, Object> edgePrefs = new HashMap<>();
//			edgePrefs.put("profile.default_content_settings.popups", 0);
//			edgePrefs.put("download.default_directory", DOWNLOADS_DIRECTORY); // Specify your download path here
//			edgePrefs.put("download.prompt_for_download", false); // If you don't want Edge to prompt for downloading files
//			edgePrefs.put("download.directory_upgrade", true); // Use this to overcome default download directory limitations
//			options.setExperimentalOption("prefs", edgePrefs);

			WebDriverManager.edgedriver().clearDriverCache().setup();
			return new EdgeDriver(options);
		}
	},

	/**
	 * This is the Firefox browser driver implementation.
	 */
	FIREFOX {
		@Override
		public WebDriver getWebDriverObject() {
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--width=1920");
			options.addArguments("--height=1080");
			options.addArguments("-private");
			options.addPreference("dom.webnotifications.enabled", false);
			options.addPreference("dom.push.enabled", false);
			options.setCapability("marionette", true);
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-extensions");

//			FirefoxProfile profile = new FirefoxProfile();
//			// Set preferences for file downloading
//			profile.setPreference("browser.download.folderList", 2);
//			profile.setPreference("browser.download.dir", DOWNLOADS_DIRECTORY); // Set your download path here
//			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf"); // MIME type
//			options.setProfile(profile);

			// Note: Firefox might not support some arguments as Chrome or Edge does, e.g., "dns-prefetch-disable"
			WebDriverManager.firefoxdriver().clearDriverCache().setup();
			return new FirefoxDriver(options);
		}
	}

}
