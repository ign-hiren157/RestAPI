package utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {


	public static Object[][] getExcelData(String filePath, String sheetName) throws IOException, InvalidFormatException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rowCount - 1][columnCount]; 

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < columnCount; j++) {
            	 Cell cell = row.getCell(j);
            	 if (cell != null) {
                     data[i - 1][j] = cell.toString();
                 } else {
                     data[i - 1][j] = "";  // Assign an empty string if cell is null
                 }
            }
        }

        workbook.close();
        fis.close();
        return data;
    	}
	}
