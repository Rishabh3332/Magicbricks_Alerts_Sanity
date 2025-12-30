package Alerts_Mailer;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Subject_Line_excel {

	WebDriver driver;
	WebDriverWait wait;

	// Constructor
	//
	public Subject_Line_excel(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		PageFactory.initElements(driver, this);
	}

	public String getCellValue( String sheetName, int rowIndex, int colIndex) {

		String value = null;
		//int Value1 = (Integer) null; 
		try {
			FileInputStream fis = new FileInputStream(new File("C:/Users/Rishabh.Singh1/Documents/TestData/Alerts_Subject_lines.xlsx"));
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet("Sheet1");
			Row row = sheet.getRow(rowIndex);

            if (row != null) { 
                Cell cell = row.getCell(colIndex);

                if (cell != null) {
                    CellType type = cell.getCellType();

                    if (type == CellType.STRING) {
                        value = cell.getStringCellValue().trim();
                    } else if (type == CellType.NUMERIC) {
                        // Get integer value without .0
                        value = String.valueOf((int) cell.getNumericCellValue());
                    } else if (type == CellType.BOOLEAN) {
                        value = String.valueOf(cell.getBooleanCellValue());
                    } else {
                        // fallback
                        value = cell.toString().trim();
                    }
                }
            }
			
			workbook.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
