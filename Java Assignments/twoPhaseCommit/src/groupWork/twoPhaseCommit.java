package groupWork;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class twoPhaseCommit {
	
	static Connection connection1=null;
	static Connection connection2=null;
	static boolean flag1=false;
	static boolean flag2=false;
    static Scanner scanner = new Scanner(System.in);
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		twophaseCommit();
	}
	
	private static void twophaseCommit() {
		String Name,Category;
		int ID,quantity,unit_price;
		
		System.out.println("Enter Product ID: ");
		ID=scanner.nextInt();
		
		System.out.println("Enter Product Name: ");
		scanner.nextLine();
		Name=scanner.nextLine();
		
		System.out.println("Enter Category: ");
		Category=scanner.nextLine();
		
		System.out.println("Enter Quantity: ");
		quantity=scanner.nextInt();
		
		System.out.println("Enter Unit_price: ");
		unit_price=scanner.nextInt();
		
		openConnection();
		
		try {
			//Inserting values in Database 1
			String insertQuery1 = "INSERT INTO product (Name,ID,Category,quantity,unit_price) VALUES (?,?,?,?,?)";
        	PreparedStatement pstmt1 = connection1.prepareStatement(insertQuery1);
            // Set values for parameters
            pstmt1.setString(1, Name); 
            pstmt1.setInt(2, ID);
            pstmt1.setString(3, Category);
            pstmt1.setInt(4, quantity);
            pstmt1.setInt(5, unit_price);
            
            // Execute the insert statement
            pstmt1.executeUpdate();
            System.out.println("Inserted into Database 1");
            flag1=true;
		}catch (SQLException e) {
        	System.out.println("Error while inserting into DB1 ");
        	System.out.println(e.getMessage());
        }
		
		try {
            //Inserting values in Database 1
            String insertQuery2 = "INSERT INTO product (Name,ID,Category,quantity,unit_price) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt2 = connection2.prepareStatement(insertQuery2);
            // Set values for parameters
            pstmt2.setString(1, Name); 
            pstmt2.setInt(2, ID);
            pstmt2.setString(3, Category);
            pstmt2.setInt(4, quantity);
            pstmt2.setInt(5, unit_price);
            
            // Execute the insert statement
            pstmt2.executeUpdate();
            System.out.println("Inserted into Database 2\n");
            flag2=true;
            
		}catch (SQLException e) {
            // Rollback the transaction if there is an exception
        	System.out.println("Error while inserting into DB2");
        	System.out.println(e.getMessage());
        }    
            if(flag1 && flag2)
            {
            	try {
            		connection1.commit();
            		connection2.commit();
            		System.out.println("Successfully inserted in both database. So Committing in both DB");
            	} catch (SQLException e) {
            		// TODO Auto-generated catch block
            		System.out.println("Error while committing....");
            	}
            	finally {
        			closeConnection();
        		}
            	
            }
            else
            {
            	try {
            		connection1.rollback();
            		connection2.rollback();
            		System.out.println("Could not insert in both database. So rolling back from both DB");
            	} catch (SQLException e) {
            		// TODO Auto-generated catch block
            		System.out.println("Error while rolling back....");
            	}
            	finally {
        			closeConnection();
        		}
            	
            } 
	}
	
	
	private static void openConnection() {
		String connectionUrl1="jdbc:sqlserver://172.16.51.64;databaseName=231047012;encrypt=true;trustServerCertificate=true;";
		String connectionUrl2="jdbc:sqlserver://172.16.51.64;databaseName=231047014;encrypt=true;trustServerCertificate=true;";
		Properties properties = new Properties();
		try {
			FileInputStream input = new FileInputStream("C:\\Users\\MSIS\\eclipse-workspace\\twoPhaseCommit\\src\\db.properties");
	        properties.load(input);
	        String username1 = properties.getProperty("username1");
            String password1 = properties.getProperty("password1");
            connection1 = DriverManager.getConnection(connectionUrl1, username1, password1);
            connection1.setAutoCommit(false); // Disable auto-commit
            System.out.println("Successfully connected to DB1");
            
            String username2 = properties.getProperty("username2");
            String password2 = properties.getProperty("password2");
            connection2 = DriverManager.getConnection(connectionUrl2, username2, password2);
            connection2.setAutoCommit(false); // Disable auto-commit
            System.out.println("Successfully connected to DB2\n");
            
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
	}
	private static void closeConnection() {
		try {
			if(connection1!=null && connection2!=null) {
				connection1.close();
				System.out.println("\nConnection 1 closed");
				connection2.close();
				System.out.println("Connection 2 closed");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
