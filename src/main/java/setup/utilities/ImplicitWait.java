package setup.utilities;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static setup.config.DriverFactory.getDriver;
import static setup.config.WebDriverThread.INTERVAL_WAIT;
import static setup.config.WebDriverThread.MAX_WAIT;

public class ImplicitWait {
    public static By loadingBy = By.xpath("//div[contains(text(),'Loading...')]");
    public static By loadingImgBy = By.xpath("//div/img[contains(@src,'loading.gif')]");
    public static By processingBy = By.xpath("//div[contains(text(),'Processing...')]");

    public static final ExpectedCondition<Boolean> EXPECT_DOC_READY_STATE = driver -> {
        String script = "if (typeof window != 'undefined' && window.document) { return window.document.readyState; } else { return 'notready'; }";
        boolean result;
        try {
            result = ((JavascriptExecutor) driver).executeScript(script).equals("complete");
        } catch (Exception ex) {
            result = Boolean.FALSE;
        }
        return result;
    };

    public static void waitForPageLoad() {
        FluentWait wait =
                new FluentWait<>(getDriver()).withTimeout(MAX_WAIT).ignoring(StaleElementReferenceException.class)
                        .pollingEvery(INTERVAL_WAIT);
        wait.until(EXPECT_DOC_READY_STATE);
    }

    public static void waitForInvisibilityElement(By by) {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), MAX_WAIT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static void waitForInvisibilityElement(By by, Duration timeout) {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static void waitForVisibilityElement(By by) {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), MAX_WAIT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void waitForVisibilityElement(By by, Duration timeout) {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static boolean isElementPresent(By by) {
        List<WebElement> list = getDriver().findElements(by);
        if (list.size() == 0) {
            return false;
        } else {
            try {
                return list.get(0).isDisplayed();
            } catch (StaleElementReferenceException e) {
                return false;
            }
        }
    }

    public static void waitForLoadingInvisibility() {
        if (isElementPresent(loadingBy)) {
            waitForInvisibilityElement(loadingBy);
        }
        waitForPageLoad();
        //Log.info("Page loading has been completed");
    }

    public static void waitForLoadingInvisibility(Duration timeout) {
        if (isElementPresent(loadingBy)) {
            waitForInvisibilityElement(loadingBy, timeout);
        }
        waitForPageLoad();
        //Log.info("Page loading has been completed");
    }

    public static void sleep(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
            //Log.info("Page sleep for " + sec + " seconds has been completed");
        } catch (InterruptedException ignored) {
        }
    }


    public static void waitIsFileDownloaded(final File path) {
        new FluentWait(getDriver()).withTimeout(MAX_WAIT).pollingEvery(INTERVAL_WAIT).until((Function) driver -> path.exists());
    }
}