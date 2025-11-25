package com.evry.bdd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    public static ArrayList<String> getRequestDetails(String apiName) throws IOException {
        String filePath = "C:\\Users\\EI06754\\Downloads\\Assignment\\bdd-framework\\src\\test\\resources\\APIData.xlsx";
      System.out.println("Excel File Path: " + filePath); // Debugging line to check the file path
        FileInputStream excelFile = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheet("APIData");
        if(sheet == null) {
            workbook.close();
            throw new RuntimeException("Sheet 'APIData' not found in Excel at path: " + filePath);
        }

        ArrayList<String> apiDetails = new ArrayList<>();
        for (Row row : sheet) {
            if(row.getCell(0) != null && row.getCell(0).getStringCellValue().equalsIgnoreCase(apiName)) {
                apiDetails.add(row.getCell(1).getStringCellValue()); // Method
                apiDetails.add(row.getCell(2).getStringCellValue()); // Endpoint
                apiDetails.add(row.getCell(3).getStringCellValue()); // Body
                apiDetails.add(row.getCell(4).getStringCellValue()); // Headers JSON
                break;
            }
        }
        workbook.close();
        System.out.println("Tests");
        System.out.println("Tests");
        return apiDetails;
    }
}
