
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShowDataToTable {
    
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url = "jdbc:sqlserver://localhost:1433;databaseName=HealthCareService;user=sa;password=sathya123;";
    
    DefaultTableModel model;

    public ShowDataToTable(String query, JTable table, int numberOfCulumn) { 
        model = (DefaultTableModel) table.getModel();

        SubTable.removeAllRows(table, model);
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(query);
            
            
            Object[] o = new Object[numberOfCulumn];
            
            while (rs.next()) {
                for (int i = 0; i < numberOfCulumn; i++) {
                    o[i] = rs.getString(i+1);
                }
                
                model.addRow(o);
            }            
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
