package setup.utilities;

import org.apache.poi.ss.usermodel.*;
import setup.logger.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelUtils {

    /**
     * Compare content of the specified Excel files
     * @param path1
     * @param path2
     */
    public static boolean CompareExcelFiles(String path1, String path2) {
        boolean areIdentical = true;
        try (FileInputStream file1 = new FileInputStream(path1);
             FileInputStream file2 = new FileInputStream(path2)) {

            Workbook workbook1 = WorkbookFactory.create(file1);
            Workbook workbook2 = WorkbookFactory.create(file2);

            if (workbook1.getNumberOfSheets() != workbook2.getNumberOfSheets()) {
                return false; // Different number of sheets
            }

            // Assuming both workbooks have the same structure
            for (int i = 0; i < workbook1.getNumberOfSheets(); i++) {
                Sheet sheet1 = workbook1.getSheetAt(i);
                Sheet sheet2 = workbook2.getSheetAt(i);
                if (!CompareSheets(sheet1, sheet2)) {
                    areIdentical = false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
        return areIdentical;
    }

    private static boolean CompareSheets(Sheet sheet1, Sheet sheet2) {
        Iterator<Row> rowIterator1 = sheet1.iterator();
        Iterator<Row> rowIterator2 = sheet2.iterator();
        boolean identical = true;

        while (rowIterator1.hasNext() && rowIterator2.hasNext()) {
            Row row1 = rowIterator1.next();
            Row row2 = rowIterator2.next();
            if (!CompareRows(row1, row2)) {
                identical = false;
            }
        }

        if (rowIterator1.hasNext() || rowIterator2.hasNext()) {
            return false; // One sheet has more rows than the other
        }

        return identical;
    }

    private static boolean CompareRows(Row row1, Row row2) {
        int lastCellNum = Math.max(row1.getLastCellNum(), row2.getLastCellNum());
        boolean identical = true;

        for (int i = 0; i < lastCellNum; i++) {
            Cell cell1 = row1.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Cell cell2 = row2.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

            if (!CompareCells(cell1, cell2)) {
                identical = false;
            }
        }

        return identical;
    }

    private static boolean CompareCells(Cell cell1, Cell cell2) {
        String cellValue1 = GetCellValue(cell1);
        String cellValue2 = GetCellValue(cell2);
        Log.info("Cell value 1: [" + cellValue1 + "] | Cell Value 2: [" + cellValue2 + "]");

        return cellValue1.equals(cellValue2);
    }

    private static String GetCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "Unsupported type";
        }
    }
}
