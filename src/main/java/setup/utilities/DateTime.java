package setup.utilities;

import setup.logger.Log;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;

public class DateTime {

    private static final ZoneId PHT_ZONE = ZoneId.of("Asia/Manila");
    private static final ZoneId PST_ZONE = ZoneId.of("America/Los_Angeles");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");

    /**
     * Compares a given date in PHT to the current date and time in PST.
     *
     * @param phtDateStr the date string in the format "MM-dd-yyyy hh:mm a"
     * @return "PAST", "FUTURE", or "TODAY" based on the comparison
     */
    public static String ComparePHTDateTimeToCurrentPST(String phtDateStr) {
        try {
            // Parse the date and time in PHT
            ZonedDateTime phtDateTime = ZonedDateTime.parse(phtDateStr, FORMATTER.withZone(PHT_ZONE));

            // Convert the PHT date and time to PST
            ZonedDateTime pstDateTime = phtDateTime.withZoneSameInstant(PST_ZONE);

            // Get the current date and time in PST
            ZonedDateTime nowPST = ZonedDateTime.now(PST_ZONE);

            // Determine if the date is past, future, or today
            if (pstDateTime.isBefore(nowPST)) {
                return "PAST";
            } else if (pstDateTime.isAfter(nowPST)) {
                return "FUTURE";
            } else {
                return "TODAY";
            }
        } catch (DateTimeParseException e) {
            // Handle the case where the date string is improperly formatted
            return "Invalid date format. Please use MM-dd-yyyy hh:mm a";
        }
    }

    /**
     * Get the Today's date from local machine
     * @return MM-dd-yyyy format
     */
    public static String getTodayDate(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = today.format(formatter);
        Log.info("Today's date is " + formattedDate);
        return formattedDate;
    }

    /**
     * Get the Today's date using PST Timezone
     * @return MM-dd-yyyy format
     */
    public static String getTodayDatePST() {
        ZoneId pstZoneId = ZoneId.of("America/Los_Angeles");
        ZonedDateTime todayPST = ZonedDateTime.now(pstZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = todayPST.format(formatter);

        Log.info("Today's date in PST is " + formattedDate);
        return formattedDate;
    }


    /**
     * Add specified number of days  in current PST date
     * @param days
     * @return
     */
    public static String AddDaysOnCurrentDatePST(int days) {
        ZoneId pstZoneId = ZoneId.of("America/Los_Angeles");
        ZonedDateTime result = ZonedDateTime.now(pstZoneId);
        int addedDays = 0;

        // Add days, skipping weekends
        while (addedDays < days) {
            result = result.plusDays(1);
            ++addedDays;
        }

        // Format the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String newDate = result.format(formatter);

        Log.info("Current date plus " + days + " days in PST is " + newDate);
        return newDate;
    }

    /**
     * Returns the end of the current month's date in the format "MM-dd-yyyy".
     *
     * @return A string representing the last day of the current month.
     */
    public static String GetEndOfMonthDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Find the last day of the current month
        LocalDate endOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());

        // Define the format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String endOfMonthDate = endOfMonth.format(formatter);

        // Format and return the end of month date
        Log.info("Current End of Month in PST is " + endOfMonthDate);
        return endOfMonthDate;
    }

    /**
     * Returns the date of the fourth Friday of the current month.
     *
     * @return A string representing the fourth Friday of the current month formatted as "MM-dd-yyyy".
     */
    public static String GetFourthFridayOfCurrentMonth() {
        // Get the first day of the current month
        LocalDate firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());

        // Find the first Friday of the month (or the first day if it is a Friday)
        LocalDate firstFriday = firstDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

        // Calculate the fourth Friday by adding 21 days to the first Friday
        LocalDate fourthFriday = firstFriday.plusDays(21);

        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String fourthFridayDate = fourthFriday.format(formatter);

        // Format and return the date of the fourth Friday
        Log.info("Current 4th Friday of Month in PST is " + fourthFridayDate);
        return fourthFriday.format(formatter);
    }

}
