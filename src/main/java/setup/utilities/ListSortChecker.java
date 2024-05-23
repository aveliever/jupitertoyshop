package setup.utilities;

import org.testng.Assert;
import setup.logger.Log;

import java.util.List;

public class ListSortChecker {

    /**
     * Checks if a list of strings is sorted in descending order.
     *
     * @param columnValues the list of strings to check
     * @return true if the list is sorted in descending order, false otherwise
     */
    public static boolean IsSortedDescending(List<String> columnValues) {
        Assert.assertTrue(columnValues.size() !=0 , "List to check is empty"); //To ensure there's data to compare
        for (int i = 0; i < columnValues.size() - 1; i++) {
            if (columnValues.get(i).compareTo(columnValues.get(i + 1)) < 0) {
                Log.info(columnValues.get(i) + " compared to " + columnValues.get(i + 1) + " is in ascending order");
                return false;
            }
        }
        Log.info("List is in descending order");
        return true;
    }

    /**
     * Checks if a list of strings is sorted in ascending order.
     *
     * @param columnValues the list of strings to check
     * @return true if the list is sorted in ascending order, false otherwise
     */
    public static boolean IsSortedAscending(List<String> columnValues) {
        Assert.assertTrue(columnValues.size() !=0 , "List to check is empty"); //To ensure there's data to compare
        for (int i = 0; i < columnValues.size() - 1; i++) {
            if (columnValues.get(i).compareTo(columnValues.get(i + 1)) > 0) {
                Log.info(columnValues.get(i) + " compared to " + columnValues.get(i + 1) + " is in descending order");
                return false;
            }
        }
        Log.info("List is in ascending order");
        return true;
    }
}
