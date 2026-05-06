package utilities;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
	 
	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook wb;
	public XSSFSheet ws;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;

	public ExcelUtility(String path)
	{
		this.path=path;
	}
	
	public int getRowCount(String sheetName) throws IOException
	{
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(sheetName);
        int rowcount=ws.getLastRowNum();

		wb.close();
		fi.close();
        return rowcount;
	}
	
	public int getCellCount(String sheetName,int rownum) throws IOException
	{
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(sheetName);
		row=ws.getRow(rownum);
		int cellcount=row.getLastCellNum();

		wb.close();
		fi.close();
		return cellcount;
	}
	
	public String getCellData(String sheetName,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(sheetName);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		
		DataFormatter formatter = new DataFormatter();
		String data;
		try
		 {
		   data = formatter.formatCellValue(cell);
		 }
		 catch(Exception e)
		  {
		     data="";
		  }
		
		wb.close();
		fi.close();
        return data;
	}
	
	public void setCellData(String sheetName,int rownum,int colnum,String data) throws IOException
	{	
	  	
		File xlfile=new File(path);   
		if(!xlfile.exists())                  // If file not exists then create new file
		{
			wb=new XSSFWorkbook();
			fo=new FileOutputStream(path);
			wb.write(fo);
		}
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		
		if(wb.getSheetIndex(sheetName)==-1)
			wb.createSheet(sheetName);
		ws=wb.getSheet(sheetName);
		
		if(ws.getRow(rownum)==null)
			ws.createRow(rownum);
	
		
		
		row=ws.getRow(rownum);
		cell=row.createCell(colnum);
		cell.setCellValue(data);

		fo=new FileOutputStream(path);
		wb.write(fo);

		wb.close();
		fi.close();
		fo.close();
		
      }
	
	public void fillRedColor(String sheetName, int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(sheetName);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		
		style=wb.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
	    fo = new FileOutputStream(path);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
		
	 }
	
	public List<Map<String, String>> getAllData(String sheetName, int rownum) throws IOException {

	    System.out.println("started excel"); 
	    // Just for debugging → confirms method execution started

	    List<Map<String, String>> dataList = new ArrayList<>();
	    // List → stores multiple rows (multiple users)
	    // Map → stores one row (key = column name, value = cell data)
	    // Benefit: supports multiple test cases dynamically

	    File file = new File(path);
	    // Creates File object pointing to Excel path
	    // Benefit: allows us to check file existence before reading

	    System.out.println(path); 
	    // Debug → prints file location

	    if (!file.exists()) {
	        throw new RuntimeException("Excel file not found at: " + path);
	    }
	    // Safety check → prevents runtime crash if file missing

	    fi = new FileInputStream(file);
	    // Opens file for reading
	    // Benefit: required to read Excel content

	    System.out.println(fi); 
	    // Debug → confirms file stream opened

	    wb = new XSSFWorkbook(fi);
	    // Creates Workbook object (entire Excel file)
	    // Benefit: gives access to all sheets in Excel

	    System.out.println(wb); 
	    // Debug → confirms workbook created

	    ws = wb.getSheet(sheetName);
	    // Gets specific sheet by name
	    // Benefit: allows selecting different test data sheets

	    System.out.println(ws); 
	    // Debug → confirms sheet loaded

	    if (ws == null) {
	        throw new RuntimeException("Sheet not found: " + sheetName);
	    }
	    // Safety → avoids null pointer if sheet name is wrong

	    Row header = ws.getRow(rownum);
	    // Reads header row (column names like email, password)
	    // Benefit: used as keys in Map

	    System.out.println(header); 
	    // Debug → shows header row object

	    if (header == null) {
	        throw new RuntimeException("Header row is missing at row: " + rownum);
	    }
	    // Safety → ensures column names exist

	    int colCount = header.getLastCellNum();
	    // Gets total number of columns
	    // Benefit: dynamic → works even if columns increase/decrease

	    System.out.println("ColCount" + colCount);

	    int rowCount = ws.getLastRowNum();
	    // Gets last row index (total rows)
	    // Benefit: helps loop through all test data rows

	    System.out.println("rowCount" + rowCount);

	    DataFormatter formatter = new DataFormatter();
	    // Converts any cell type (number/string/date) → String
	    // Benefit: avoids type issues while reading Excel

	    System.out.println(formatter);

	    // Loop through each data row (skip header row)
	    for (int i = rownum + 1; i < rowCount; i++) {
	    
	        Row row = ws.getRow(i);
	        // Gets current row

	        System.out.println(row);

	        if (row == null) continue;
	        // Skips empty rows → avoids errors

	        Map<String, String> rowData = new HashMap<>();
	        // New Map for each row
	        // Benefit: prevents data overwrite (each user separate)

	        System.out.println("2nd for loop");

	        // Loop through each column
	        for (int j = 0; j < colCount; j++) {

	            String key = formatter.formatCellValue(header.getCell(j));
	            // Reads column name (email, password, etc.)
	            // Used as key in Map

	            String value = formatter.formatCellValue(row.getCell(j));
	            // Reads actual cell value from row
	            // Converted to String safely

	            rowData.put(key, value);
	            // Stores data as key-value pair
	            // Example: "email" → "a@test.com"
	        }

	        dataList.add(rowData);
	        // Adds one row (Map) into List
	        // Benefit: collects all users' data
	    }

	    System.out.println("excel end1");

	    wb.close();
	    // Closes workbook → frees memory

	    fi.close();
	    // Closes file stream → avoids memory leak

	    return dataList;
	    // Returns complete data → List of Maps (all rows)
	}
	
	public List<Map<String, String>> getFlexibleData(
	        String sheetName,
	        int headerRow,
	        int startRow,
	        int endRow,
	        String filterColumn,
	        String filterValue) throws IOException {

	    // 🔥 FINAL OUTPUT: Each row = Map<ColumnName, Value>
	    List<Map<String, String>> dataList = new ArrayList<>();

	    // 📌 Step 1: Open Excel file
	    FileInputStream fi = new FileInputStream(path);

	    // 📌 Step 2: Load workbook (Excel file)
	    wb = new XSSFWorkbook(fi);

	    // 📌 Step 3: Get specific sheet (Login, Users, etc.)
	    ws = wb.getSheet(sheetName);

	    // 📌 Step 4: Get header row (used as KEY in Map)
	    Row header = ws.getRow(headerRow);

	    // 📌 Step 5: Get total number of columns
	    int colCount = header.getLastCellNum();

	    // 📌 Step 6: Get last row index
	    int rowCount = ws.getLastRowNum();

	    // 📌 Step 7: Used to format all cell types as String (important for Excel compatibility)
	    DataFormatter formatter = new DataFormatter();

	    // 🔥 If user did not pass endRow → take full sheet
	    if (endRow == -1 || endRow > rowCount) {
	        endRow = rowCount;
	    }

	    // ================== LOOP ROWS ==================
	    for (int i = startRow; i <= endRow; i++) {

	        // 📌 Get current row
	        Row row = ws.getRow(i);

	        // 📌 Skip empty rows
	        if (row == null) continue;

	        // 🔥 Each row becomes a Map (ColumnName → Value)
	        Map<String, String> rowData = new HashMap<>();

	        // ================== LOOP COLUMNS ==================
	        for (int j = 0; j < colCount; j++) {

	            // 📌 Column name from header row (KEY)
	            String key = formatter.formatCellValue(header.getCell(j));

	            // 📌 Cell value from current row (VALUE)
	            String value = formatter.formatCellValue(row.getCell(j));

	            // 📌 Store into map
	            rowData.put(key, value);
	        }

	        // ================== FILTER LOGIC ==================
	        if (!filterColumn.isEmpty()) {

	            // 🔥 Only add rows matching filter condition
	            if (filterValue.equalsIgnoreCase(rowData.get(filterColumn))) {
	                dataList.add(rowData);
	            }

	        } else {
	            // 🔥 No filter → add all rows
	            dataList.add(rowData);
	        }
	    }

	    // 📌 Step 8: Close resources (VERY IMPORTANT to avoid memory leak)
	    wb.close();
	    fi.close();

	    // 📌 Step 9: Return final structured data
	    return dataList;
	}

}