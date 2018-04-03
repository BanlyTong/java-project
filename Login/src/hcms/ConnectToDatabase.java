/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcms;

import java.beans.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Harry
 */
public class ConnectToDatabase {
    public static String JDBC_Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String JDBC_Url = "jdbc:sqlserver://localhost:1433;"+"Database=HealthCareService;user=what;password=what;"; 
    public ConnectToDatabase()
    {
        
    }
}
