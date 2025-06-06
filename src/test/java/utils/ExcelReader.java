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
	        DataFormatter formatter = new DataFormatter();  // ✅ Use this for safe string conversion

	        int rowCount = sheet.getPhysicalNumberOfRows();
	        int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();

	        Object[][] data = new Object[rowCount - 1][columnCount];

	        for (int i = 1; i < rowCount; i++) {
	            Row row = sheet.getRow(i);
	            if (row == null) continue;

	            for (int j = 0; j < columnCount; j++) {
	                Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	                String cellValue = formatter.formatCellValue(cell);  // ✅ Safe, handles all types
	                data[i - 1][j] = cellValue.trim();
	            }
	        }

	        workbook.close();
	        fis.close();
	        return data;
	    }
	
	}
