package setup.utilities;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import setup.logger.Log;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CSVUtils {

    /**
     * Read all Data from CSV including Headers
     * @param path Path to the CSV file.
     * @return  List of Data from CSV
     */
    public static List<String[]> ReadAllCsvData(Path path) {
        List<String[]> allData = new ArrayList<>();

        CSVFormat format = CSVFormat.DEFAULT;

        // Try-with-resources to ensure that resources are closed after the program is finished
        try (Reader reader = Files.newBufferedReader(path);
             CSVParser csvParser = new CSVParser(reader, format)) {

            // Iterate over CSV records
            for (CSVRecord record : csvParser) {
                String[] currentRow = new String[record.size()];
                for (int i = 0; i < record.size(); i++) {
                    currentRow[i] = record.get(i);
                }
                allData.add(currentRow);
            }
        } catch (IOException e) {
            Log.error("Failed to read or parse the CSV file: ", e);
        }

        //Log Data
        for (String[] row : allData) {
            Log.info("CSV Data {" + String.join(", ", row) + "}");
        }

        return allData;
    }

    /**
     * Filters CSV data based on a specific column value and returns data from another column.
     *
     * @param filePath          Path to the CSV file.
     * @param columnToGetData   The name of the column from which to retrieve data.
     * @param columnToFilter    The name of the column to apply the filter on.
     * @param columnFilterValue The value to filter by in the columnToFilter.
     * @return                  List of values from columnToGetData where columnToFilter matches columnFilterValue.
     */
    public static List<String> FilterCSVDataByColumnValue(String filePath, String columnToGetData, String columnToFilter, String columnFilterValue) {
        List<String> filteredData = new ArrayList<>();

        try (Reader reader = new FileReader(filePath)) {
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase());

            // Iterate over CSV records and filter based on the specified column and value
            for (CSVRecord record : parser) {
                if (record.get(columnToFilter).equalsIgnoreCase(columnFilterValue)) {
                    filteredData.add(record.get(columnToGetData));
                }
            }
        } catch (IOException e) {
            Log.error("Failed to read or parse the CSV file: ", e);
        }

        Log.info("Filtered CSV Data (Total: " + filteredData.size() + ") {" + String.join(", ", filteredData) + "}");
        return filteredData;
    }

    /**
     * Writes a CSV file from a list of records with headers.
     *
     * @param path    the file path where the CSV is to be saved
     * @param headers an array of strings representing the headers of the CSV; if null, no headers are written
     * @param csvData the data for the CSV, where each inner list represents one row
     * @throws IOException if an I/O error occurs writing to or creating the file
     */
    public static void CreateCSVFile(Path path, String[] headers, List<List<String>> csvData) throws IOException {
        // Determine the CSV format based on whether headers are provided
        CSVFormat format = headers == null ? CSVFormat.DEFAULT : CSVFormat.DEFAULT.withHeader(headers);

        // Use FileWriter to open the file and CSVPrinter to format the CSV output
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(path.toFile()), format)) {
            // Iterate through each record in the data list
            for (List<String> record : csvData) {
                // Print each record as a new row in the file
                printer.printRecord(record);
            }
        }
        // CSVPrinter and FileWriter are automatically closed by try-with-resources
    }

    /**
     * Updates specified values in a CSV file based on row and column indices, preserving the header.
     *
     * @param path the path to the CSV file
     * @param updates a list of updates, where each update is an array with three elements:
     *                int row, String columnName, String newValue
     * @throws IOException if an error occurs reading or writing the file
     */
    public static void UpdateCsvValues(Path path, List<Object[]> updates) throws IOException {
        // Configure CSVFormat to keep the header information
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();

        // Read all records including headers
        List<CSVRecord> records;
        List<String> headers;
        try (Reader in = new FileReader(path.toFile())) {
            CSVParser parser = format.parse(in);
            records = new ArrayList<>(parser.getRecords());
            headers = parser.getHeaderNames();  // Obtain headers directly from the parser
        }

        // Convert CSVRecords to a mutable structure
        List<List<String>> updatedRecords = new ArrayList<>();
        for (CSVRecord record : records) {
            List<String> values = new ArrayList<>();
            for (String header : headers) {
                values.add(record.get(header));
            }
            updatedRecords.add(values);
        }

        // Apply updates based on the provided list
        for (Object[] update : updates) {
            int row = (int) update[0];
            String columnName = (String) update[1];
            String newValue = (String) update[2];
            int columnIndex = headers.indexOf(columnName);
            if (columnIndex != -1 && row < updatedRecords.size()) {
                updatedRecords.get(row).set(columnIndex, newValue);
            }
        }

        // Write the updated records back to the file, preserving the headers
        try
                (BufferedWriter writer = Files.newBufferedWriter(path);
                 CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers.toArray(new String[0])))) {
            for (List<String> record : updatedRecords) {
                printer.printRecord(record);
            }
        }
    }

    /**
     * Reads a specific row from a CSV file.
     *
     * @param path the file path
     * @param rowIndex the index of the row to read
     * @return a list of values in the row
     * @throws IOException if an error occurs reading the file
     */
    private static List<String> ReadRowFromCsv(Path path, int rowIndex) throws IOException {
        try (Reader in = new FileReader(path.toFile())) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            int currentRow = 0;
            for (CSVRecord record : records) {
                if (currentRow == rowIndex) {
                    List<String> values = new ArrayList<>();
                    record.forEach(values::add);
                    return values;
                }
                currentRow++;
            }
        }
        return new ArrayList<>();  // Return empty list if row index is not found
    }

    /**
     * Reads a CSV file and returns the row data as a list based on the provided row header value.
     *
     * @param path the path to the CSV file
     * @param header the column header to search
     * @param value the value to match in the specified header column
     * @return a List of strings representing the row data; empty if no match found
     * @throws IOException if an error occurs during file reading
     */
    public static List<String> ReadRowFromCsvByHeaderValue(Path path, String header, String value) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim();
        try (Reader in = new FileReader(path.toFile())) {
            CSVParser parser = format.parse(in);
            for (CSVRecord record : parser) {
                if (record.isMapped(header) && record.get(header).equals(value)) {
                    List<String> rowData = new ArrayList<>();
                    record.forEach(rowData::add);
                    Log.info("CSV Row Data: {" + String.join(", ", rowData) + "}");
                    return rowData;
                }
            }
        }
        List<String> data = Collections.emptyList();  // Return empty list if no matching row is found
        Log.warn("Cannot find CSV data from specified header: " + header + " and value: " + value);
        return data;
    }

    /**
     * Reads a specific cell from a CSV file based on the provided row header value and the target column header.
     *
     * @param path the path to the CSV file
     * @param searchHeader the column header to search for the value
     * @param searchValue the value to match in the specified search header column
     * @param targetHeader the column header from which to extract the cell value
     * @return the cell value if found; an empty string if not found
     * @throws IOException if an error occurs during file reading
     */
    public static String ReadCellFromCsvByHeaderValue(Path path, String searchHeader, String searchValue, String targetHeader) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim();
        try (Reader in = new FileReader(path.toFile())) {
            CSVParser parser = format.parse(in);
            for (CSVRecord record : parser) {
                if (record.isMapped(searchHeader) && record.get(searchHeader).equals(searchValue)) {
                    if (record.isMapped(targetHeader)) {
                        String cellData = record.get(targetHeader);
                        Log.info("CSV Cell data: " + cellData);
                        return cellData;
                    } else {
                        Log.warn("Target header '" + targetHeader + "' is not found in the CSV.");
                        return "";
                    }
                }
            }
        }
        Log.warn("No matching row found for header '" + searchHeader + "' with value '" + searchValue + "'");
        return "";
    }
}