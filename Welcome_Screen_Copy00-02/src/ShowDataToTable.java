
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sathya
 */
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
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            
            Object[] o = new Object[numberOfCulumn];
            
            while (rs.next()) {
                for (int i = 0; i < numberOfCulumn; i++) {
                    o[i] = rs.getString(i+1);
                }
//                model1.addRow(o);
                model.addRow(o);
            }            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
