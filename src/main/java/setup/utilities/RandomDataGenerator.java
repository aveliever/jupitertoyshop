package setup.utilities;

import setup.logger.Log;

import java.text.DecimalFormat;
import java.util.Random;

public class RandomDataGenerator {

    /**
     * Generates a random alphanumeric string
     * @param length
     * @return
     */
    public static String GenerateRandomString(int length) {
        // Define the characters that the string can consist of
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        // Randomly select characters from the characters string and append them to the result
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length()); // generate a random index
            result.append(characters.charAt(index)); // append the character at the random index
        }

        Log.info("Generated Random String is: " + result);
        return result.toString();
    }

    /**
     * Generates a random number between min and max with optional decimal places,
     * then returns it as a formatted string.
     * @param min The lower bound of the range (inclusive).
     * @param max The upper bound of the range (exclusive).
     * @param decimalPlaces The number of decimal places in the output. If 0, no decimals are included.
     * @return A string representing a random number between min and max, formatted to the specified decimal places.
     */
    public static String GenerateRandomNumber(int min, int max, int decimalPlaces) {
        Random random = new Random();
        double randomNumber = min + (max - min) * random.nextDouble();
        String result;

        if (decimalPlaces > 0) {
            StringBuilder pattern = new StringBuilder("#.");
            for (int i = 0; i < decimalPlaces; i++) {
                pattern.append("0");
            }
            DecimalFormat decimalFormat = new DecimalFormat(pattern.toString());
            result = decimalFormat.format(randomNumber);
        } else {
            result = String.valueOf((int) randomNumber);
        }

        Log.info("Generated Random Number is: " + result);
        return result;
    }
}

