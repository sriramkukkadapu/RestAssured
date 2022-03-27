package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelUtil {

	public ArrayList<String> getData(String rowName, String sheetName) throws IOException{
		FileInputStream fis = new FileInputStream("excelData/Data.xlsx");
		ArrayList<String> data = new ArrayList<String>();
		
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		
		//int totalSheets = workBook.getNumberOfSheets();
		XSSFSheet sheet =  workBook.getSheet(sheetName);
		
		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

	    //Create a loop over all the rows of excel file to read it

	    for (int i = 0; i < rowCount+1; i++) {

	        Row row = sheet.getRow(i);

	        //Create a loop to print cell values in a row

	        if(row.getCell(0).getStringCellValue().equals(rowName)) {
	        	
	        	 for (int j = 0; j < row.getLastCellNum(); j++) {

	 	            //Print Excel data in console
	        		if(row.getCell(j).getCellType() == CellType.STRING) 
	 	            data.add(row.getCell(j).getStringCellValue());
	        		
	        		else {
	        			data.add(NumberToTextConverter.toText( row.getCell(j).getNumericCellValue() ));
	        			//data.add(row.getCell(j).getNumericCellValue() + "");
	        		}

	 	        }
	        	
	        }
	        
	    } 
	
		
	
		workBook.close();
		
		return data;
	}
}
