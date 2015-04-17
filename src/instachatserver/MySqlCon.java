/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instachatserver;

import java.rmi.RMISecurityManager;

/**
 *
 * @author Saurin
 */
import java.sql.*;

public class MySqlCon {
	public Connection con=null;
	public static int siteUser;
    public MySqlCon()
    {
    	
    	try
        {
            try
            {
                // SQL driver loading
                Class.forName("com.mysql.jdbc.Driver");
            }
            catch(ClassNotFoundException e)
            {
                System.err.println("JdbcOdbc Bridge Driver not found!");
            }
            String url = "jdbc:mysql://23.255.242.157:3306/final_java";
            try
            {
                con = DriverManager.getConnection(url,"scott","tiger");
                //System.out.println("Driver found");
            }
            catch(SQLException e6)
            {
                System.out.println(e6.getMessage());
                e6.printStackTrace();
            }
    	}
	    catch(Exception e)
	    {
                System.out.println(e.getMessage());
        }
    }	// END of constructor 
    
    public Connection getConnection()
    {
        return con;
    }
} // END of class MySqlCon
