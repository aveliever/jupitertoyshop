package setup.base;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.testng.annotations.*;
import properties.Environment;
import setup.listeners.TestReporter;
import setup.listeners.TestRunListener;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import static java.lang.System.getProperty;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static setup.config.DriverFactory.*;
import static setup.logger.Log.error;

/**
 * To extend every test class created.
 *
 */
@Listeners({ TestRunListener.class, TestReporter.class })
public abstract class BaseTest extends BasePage{

	//Set default Environment
	public static Environment environment = Environment.TEST;
	public static String PROJECT_DIRECTORY = System.getProperty("user.dir");
	public static String DATA_DIRECTORY = PROJECT_DIRECTORY + File.separator + "src/test/resources/testData/";
	public static String FOR_CHECKING = DATA_DIRECTORY + "forChecking/";
	public static String FOR_UPLOADING = DATA_DIRECTORY + "forUploading/";
	public static String DOWNLOADS_DIRECTORY = System.getProperty("user.home") + "/Downloads/";
	/**
	 * Method to execute at the start of the suite execution.
	 */
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		instantiateDriverObject();
	}

	/**
	 * Method to execute at the end of each test method execution.
	 */
	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		clearCookies();
	}

	/**
	 * Method to execute at the end of the suite execution
	 */
	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		closeDriverObjects();
	}

	/**
	 * Data Provider method to get data from Excel file.
	 * 
	 * @param method test method executed
	 * @return excel data
	 */
	@DataProvider(name = "ExcelDataProvider")
	public Iterator<Object[]> provideData(Method method, String pathName) {
		List<Object[]> excelData = new ArrayList<Object[]>();
		Connection con = null;
		Recordset record = null;
		try {
			Fillo fillo = new Fillo();
			con = fillo.getConnection(pathName);
			record = con.executeQuery("Select * from TestData where TestCase = '"
					+ method.getDeclaringClass().getSimpleName() + "." + method.getName() + "'");
			while (record.next()) {
				Map<String, String> data = new HashMap<String, String>();
				for (String field : record.getFieldNames()) {
					if (!record.getField(field).isEmpty()) {
						data.put(field, record.getField(field));
					}
				}
				excelData.add(new Object[] { data });
			}
		} catch (FilloException e) {
			error("Unable to get data from Excel", e);
			throw new RuntimeException("Could not read " + pathName + " file.\n" + e.getStackTrace().toString());
		} finally {
			con.close();
			record.close();
		}
		return excelData.iterator();
	}

	/**
	 * Data Provider method to get data from CSV file.
	 * 
	 * @param method test method executed
	 * @return CSV data
	 */
	@DataProvider(name = "CsvDataProvider")
	public Iterator<Object[]> getCsvData(Method method, String pathName) {
		List<Object[]> csvData = new ArrayList<Object[]>();
		String csvRegex = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
		try {
			String[] keys = lines(get(pathName)).findFirst().orElseThrow(IOException::new).split(csvRegex);
			List<String[]> dataLines = lines(get(pathName)).filter(
					line -> line.startsWith(method.getDeclaringClass().getSimpleName() + "." + method.getName()))
					.map(line -> line.split(csvRegex)).collect(toList());
			for (String[] values : dataLines) {
				Map<String, String> data = new HashMap<String, String>();
				for (int i = 1; i < keys.length; i++) {
					if (!values[i].isEmpty()) {
						data.put(keys[i], values[i]);
					}
				}
				csvData.add(new Object[] { data });
			}
		} catch (IOException e) {
			error("Unable to get data from Csv", e);
			throw new RuntimeException("Could not read " + pathName + " file.\n" + e.getStackTrace().toString());
		}
		return csvData.iterator();
	}

	/**
	 * Get the user name from the command line.
	 * 
	 * @return user name
	 */
	protected String getUsername() {
		return ofNullable(getProperty("username"))
				.orElseThrow(() -> new NullPointerException("Username was not provided"));
	}

	/**
	 * Get the password from the command line.
	 * 
	 * @return password
	 */
	protected String getPassword() {
		return ofNullable(getProperty("password"))
				.orElseThrow(() -> new NullPointerException("Password was not provided"));
	}

}
