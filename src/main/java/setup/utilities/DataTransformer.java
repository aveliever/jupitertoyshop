package setup.utilities;

import setup.logger.Log;

import java.math.BigDecimal;

public class DataTransformer {

    /**
     * To remove trailing zeros from a decimal number and present the minimal form of that number without unnecessary decimal places
     *
     * @param numberStr
     * @return
     */
    public static String RemoveTrailingZeroes(String numberStr) {
        // Convert string to BigDecimal
        BigDecimal formattedNumber = new BigDecimal(numberStr).stripTrailingZeros();
        // Convert the BigDecimal to plain string to avoid scientific notation
        String result = formattedNumber.toPlainString();

        Log.info("Removed trailing zeroes of " + numberStr + " = " + result);
        return result;
    }
}
