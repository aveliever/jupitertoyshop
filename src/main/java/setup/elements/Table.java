package setup.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import setup.logger.Log;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * This Class is used to handle <b>Table</b>.
 *
 */
public final class Table extends Element {

	/**
	 * This constructor is used to create an object to access a <b>Table</b>.
	 * 
	 * @param description description of the table
	 * @param locator     locator of the table
	 */
	public Table(String description, By locator) {
		super(description, locator);
	}

	/**
	 * Get all the table headers.
	 * 
	 * @return list of table headers
	 */
	public List<String> getTableHeaders() {
		Log.info("Get all the table headers from the [" + description + "] table");
		WebElement table = wait.until(elementToBeClickable(locator));
		List<String> headers = table.findElements(tagName("th")).stream().map(WebElement::getText).collect(toList());
		return headers;
	}

	/**
	 * Get all the table data.
	 *
	 * @return list of table data
	 */
	public List<String> getTableData() {
		Log.info("Get all the table data from the [" + description + "] table");
		WebElement table = wait.until(elementToBeClickable(locator));
		List<String> data = table.findElements(tagName("td")).stream().map(WebElement::getText).collect(toList());

		Log.info("Table Data {" + String.join(", ", data) + "}");
		Log.info("Total Data: " + data.size());
		return data;
	}

	/**
	 * Get all the table data.
	 *
	 * @return list of table data
	 */
	public List<String> getTableDataByTextContent() {
		Log.info("Get all the table data from the [" + description + "] table");
		WebElement table = wait.until(elementToBeClickable(locator));
		List<String> data = table.findElements(By.tagName("td"))
				.stream()
				.map(element -> element.getAttribute("textContent"))
				.collect(Collectors.toList());

		Log.info("Table Data {" + String.join(", ", data) + "}");
		Log.info("Total Data: " + data.size());
		return data;
	}

}
