package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataP {
	
	
	@DataProvider(name="LoginData")
	public String[][] GetData() throws IOException{
		
		String path=".\\testData\\OpenCart_LoginData.xlsx";
		
		ExcelUtility utility=new ExcelUtility(path);
		
		int row=utility.getRowCount("Sheet1");
		int col=utility.getCellCount("Sheet1", 1);
		String logindata[][]=new String[row][col];
		for(int i=1; i<=row; i++) {
			
			for(int j=0; j<col; j++) {
				
				logindata[i-1][j]= utility.getCellData("Sheet1", i, j);
				
			}
		}
		return logindata;
	}

}
