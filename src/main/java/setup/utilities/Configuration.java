package setup.utilities;

import static java.nio.charset.Charset.defaultCharset;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

import setup.logger.Log;

/**
 * To get the configuration details present in config.properties file.
 *
 */
public final class Configuration {

	private static Properties properties;

	private Configuration() {
	}

	/**
	 * Load the properties present in configuration file.
	 */
	public static void load() {
		properties = new Properties();
		Path config = Paths.get("config.properties");
		try (BufferedReader reader = Files.newBufferedReader(config, defaultCharset())) {
			properties.load(reader);
		} catch (IOException e) {
			Log.error("Unable to find config.properties file...", e);
		}
	}

	/**
	 * Get the properties present in configuration file.
	 * 
	 * @param property property to fetch
	 * @return property value
	 */
	public static String get(String property) {
		if (properties == null) {
			load();
		}
		return Optional.ofNullable(properties.getProperty(property)).orElse("");
	}

	/**
	 * Print all the properties fetched.
	 */
	public static void print() {
		properties.forEach((k, v) -> System.out.println(k + "=" + v));
	}

}
