package setup.listeners;

import org.apache.commons.io.FileUtils;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import setup.logger.Log;
import setup.utilities.Screenshot;

import java.io.File;
import java.io.IOException;

/**
 * Listener class to log the execution details of every test.
 *
 */
public final class TestRunListener implements ITestListener, ISuiteListener {

	@Override
	public void onTestStart(ITestResult result) {
		Log.info("Execution of the test [" + result.getName() + "] has been started.");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
	}

	@Override
	public void onTestFailure(ITestResult result) {
		result.setAttribute("failureScreenshot", Screenshot.takeScreenShot("Failure_" + result.getName()));
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Log.error("Test [" + result.getName() + "] failed within success percentage", result.getThrowable());
	}

	@Override
	public void onStart(ITestContext context) {
		//Delete Existing Report & logs
		String PATH = System.getProperty("user.dir") + "/test-output/";
		try {
			FileUtils.deleteDirectory(new File(PATH));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		Log.info("About to begin executing Test [" + context.getName() + "]");
	}

	@Override
	public void onFinish(ITestContext context) {
		Log.info("About to end executing Test [" + context.getName() + "]");
	}

}
