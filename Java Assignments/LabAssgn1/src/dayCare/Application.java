package dayCare;

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
			/*System.out.println("Enter Employee Name:");
			String name = read.nextLine();
			Statement s = connect.createStatement();
			ResultSet rs1 = s.executeQuery("Select * from Employees where Employee_Name="+name);
			while(rs1.next()){
					System.out.println(rs1.getInt("SSN")+ "\t" + rs1.getString("Employee_Name")+ "\t"  + rs1.getInt("Salary"));
			}
			
			System.out.println("After SQL Injection");
			ResultSet rs2 = s.executeQuery("Select * from Employees");
			while(rs2.next()) {
				System.out.println(rs2.getInt("SSN")+ "\t" + rs2.getString("Employee_Name")+ "\t"  + rs2.getInt("Salary"));
			}*/
			//System.out.println("Prepared Statement");
			//System.out.println("Enter Employee_ID:");
			//int emp_id = read.nextInt();
			System.out.println("Enter Employee name:");
			//read.nextLine();
			String name1 = read.nextLine();
			PreparedStatement ps = connect.prepareStatement("Select * from Employees where Employee_Name=?;");
			ps.setString(1,name1);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
					System.out.println(rs.getInt("SSN")+ "\t" + rs.getString("Employee_Name")+ "\t"  + rs.getInt("Salary"));
			}
			else
				System.out.println("No result found");
			connect.close();
		} catch (Exception e) {
			System.out.println("error");
			System.out.println(e.getMessage());
		}
		closeConnection();
	    
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
