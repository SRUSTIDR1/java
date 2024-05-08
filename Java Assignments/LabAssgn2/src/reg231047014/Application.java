package reg231047014;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Application {
	static Scanner read = new Scanner(System.in);
	static Connection connect=null;
	public static void main(String[] args) {
		int choice;
		
		System.out.println("SQL Injection and Prepared Statement Demonstration\n");
		transaction();
			
	}
	private static void transaction() {
		openConnection();
		try {
			
			System.out.println("Enter Employee name:");
			//read.nextLine();
			String name1 = read.nextLine();
			PreparedStatement ps = connect.prepareStatement("Select * from Employees where Employee=?;");
			ps.setString(1,name1);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
					System.out.println(rs.getInt("SSN")+ "\t" + rs.getString("Employee_Name")+ "\t"  + rs.getInt("Salary"));
			}
			else
				System.out.println("No result found");
				connect.close();
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println(e.getMessage());
		}
		finally {
			closeConnection();
		}
		
	    
	}
	
	private static void openConnection() {
		String connectionUrl="jdbc:sqlserver://172.16.51.64;databaseName=231047014;encrypt=true;"
				+ "trustServerCertificate=true;user=SHRINIDHI;password=shrinidhi@1";
		try {
			connect = DriverManager.getConnection(connectionUrl);
			System.out.println("Connection Successful");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
	}
	private static void closeConnection() {
		try {
			if(connect!=null) {
				connect.close();
				System.out.println("Connection closed");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
