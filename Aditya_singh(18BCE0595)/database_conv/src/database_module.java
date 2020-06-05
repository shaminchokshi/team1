import java.io.*;
import java.sql.*;
 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

 
/**
 * A simple Java program that exports data from database to Excel file.
 
 */
public class database_module {
 
    public static void main(String[] args) {
        new database_module().export();
    }
     
    public void export() {
        String jdbcURL = "jdbc:mysql://localhost:3306/alpha1?autoReconnect=true&useSSL=false";
        String username = "root";
        String password = "Godofwar2";
 
        String excelFilePath = "Z:\\Reviews-export.xlsx";
 
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String sql = "SELECT * FROM review where Status='No'";
 
            Statement statement = connection.createStatement();
 
            ResultSet result = statement.executeQuery(sql);
 
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Reviews");
 
            writeHeaderLine(sheet);
 
            writeDataLines(result, workbook, sheet);
 
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
           /* workbook.close();
 */
            statement.close();
 
        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }
 
    private void writeHeaderLine(XSSFSheet sheet) {
 
        Row headerRow = sheet.createRow(0);
 
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("R_ID");
 
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Name");
 
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Type");
 
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Amount");
 
        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Status");
    }
 
    private void writeDataLines(ResultSet result, XSSFWorkbook workbook,
            XSSFSheet sheet) throws SQLException {
        int rowCount = 1;
 
        while (result.next()) {
        	int R_ID =result.getInt("R_ID");
            String Name = result.getString("Name");
            String Type = result.getString("Type");
            int Amount =result.getInt("Amount");
            String Status = result.getString("Status");
            
 
            Row row = sheet.createRow(rowCount++);
 
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(R_ID);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(Name);
 
            
            cell = row.createCell(columnCount++);
            cell.setCellValue(Type);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(Amount);
 
            cell = row.createCell(columnCount);
            cell.setCellValue(Status);
 
        }
    }
 
}




