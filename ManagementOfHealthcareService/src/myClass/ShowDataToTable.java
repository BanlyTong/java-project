package myClass;


import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShowDataToTable {
    
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String url = "jdbc:sqlserver://localhost:1433;databaseName=HealthCareService;user=sa;password=sathya123;";
    
    static DefaultTableModel model;

    public static void show(String query, JTable table, int numberOfCulumn) { 
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
