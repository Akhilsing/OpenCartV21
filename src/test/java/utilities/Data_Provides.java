package utilities;
// Package name → helps organize code (framework structure)

import org.testng.annotations.DataProvider;
// Import TestNG DataProvider → used to supply data to test methods

import java.util.*;
// Imports List, Map, ArrayList, HashMap → used to handle dynamic data

public class Data_Provides {

    @DataProvider(name = "excelData")
    // Marks this method as DataProvider
    // "excelData" → name used in @Test annotation
    // Benefit: connects test method with this data source

    public Object[][] getExcelData() throws Exception {
        // Must return Object[][]
        // TestNG requirement → each row = one test execution

        System.out.println("started data provider");
        // Debug → confirms DataProvider execution started

        String path = ".\\testData\\OpenCart_LoginData.xlsx";
        // Path of Excel file
        // Benefit: external data → no hardcoding in test

        System.out.println("Excel Path: " + path);

        ExcelUtility utility = new ExcelUtility(path);
        // Creating ExcelUtility object
        // Benefit: separates Excel logic from test logic (clean design)

        System.out.println("Excel utility: " + utility);

        List<Map<String, String>> data = utility.getAllData("Sheet1", 0);
        // Calls method to read Excel
        // Returns:
        // List → multiple rows
        // Map → one row (column name → value)
        // Example:
        // [
        //   {email=a@test.com, password=123, res=valid},
        //   {email=b@test.com, password=456, res=invalid}
        // ]

        System.out.println("Data size: " + data.size());
        // Prints number of rows (test cases)

        Object[][] result = new Object[data.size()][1];
        // Create 2D array required by TestNG
        // rows = number of test cases
        // columns = 1 (because test method takes only ONE parameter → Map)
        // Benefit: matches method signature:
        // public void test(Map<String,String> data)

        // LOOP → convert List<Map> → Object[][]
        for (int i = 0; i < data.size(); i++) {

            result[i][0] = data.get(i);
            // Put each Map into Object[][]
            // i = row index (test case number)
            // 0 = first column (only one parameter)
        }

        System.out.println("end data provider");

        return result;
        // Returns data to TestNG
        // TestNG will execute test method once per row
    }
}
