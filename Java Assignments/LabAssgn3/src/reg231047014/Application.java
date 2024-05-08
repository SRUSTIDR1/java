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
	static Connection connect2=null;
	public static void main(String[] args) {
		transaction();
			
	}
	private static void transaction() {
		
		openConnection();
		try {
			
			System.out.println("Enter Product ID:");
			int ID = read.nextInt();
			
			//Reading Unit_price through connection 1 before transaction
			PreparedStatement p = connect.prepareStatement("Select * from product where ID=?;");
			p.setInt(1,ID);
			ResultSet rs1 = p.executeQuery();
			System.out.println("\nUnit_Price through connection 1 before transaction");
			System.out.println("Product ID 	Unit_Price");
			if(rs1.next()) {
				System.out.println(rs1.getInt("ID")+" 		"+rs1.getInt("unit_price"));
			}
			else
				System.out.println("No result found");
			
			//Reading Unit_price through connection 2 before transaction
			PreparedStatement p1 = connect2.prepareStatement("Select * from product where ID=?;");
			p1.setInt(1,ID);
			ResultSet r = p1.executeQuery();
			System.out.println("\nUnit_Price through connection 2 before transaction");
			System.out.println("Product ID 	Unit_Price");
			if(r.next()) {
				System.out.println(r.getInt("ID")+" 		"+r.getInt("unit_price"));
			}
			else
				System.out.println("No result found");
			connect2.setTransactionIsolation(connect2.TRANSACTION_READ_UNCOMMITTED);
			//Updating unit_price ( if unit_price < 0 rollback else commit)
			connect.setAutoCommit(false);
			
			//connect.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			System.out.println("\nEnter Product Unit_Price:");
			int unit = read.nextInt();
			PreparedStatement ps = connect.prepareStatement("Update product set unit_price=? where ID=?;");
			ps.setInt(1,unit);
			ps.setInt(2,ID);
			ps.executeUpdate();
			
			PreparedStatement ps1 = connect.prepareStatement("Select * from product where ID=?;");
			ps1.setInt(1,ID);
			ResultSet rs = ps1.executeQuery();
			
			
			//Reading Unit_price through connection 1 during transaction
			PreparedStatement a = connect.prepareStatement("Select * from product where ID=?;");
			a.setInt(1,ID);
			ResultSet s = a.executeQuery();
			System.out.println("\nUnit_Price through connection 1 during transaction");
			System.out.println("Product ID 	Unit_Price");
			if(s.next()) {
				System.out.println(s.getInt("ID")+" 		"+s.getInt("unit_price"));
			}
			else
				System.out.println("No result found");
			
			
			//Reading Unit_price through connection 2 during transaction
			PreparedStatement p11 = connect2.prepareStatement("Select * from product where ID=?;");
			p11.setInt(1,ID);
			System.out.println("Hello");
			ResultSet r1 = p11.executeQuery();
			System.out.println("\nUnit_Price through connection 2 during transaction");
			System.out.println("Product ID 	Unit_Price");
			if(r1.next()) {
				System.out.println(r1.getInt("ID")+" 		"+r1.getInt("unit_price"));
			}
			else
				System.out.println("No result found");
			
			
			if(rs.next()){
					//System.out.println(rs.getInt("SSN")+ "\t" + rs.getString("Employee_Name")+ "\t"  + rs.getInt("Salary"));
					int u_price = rs.getInt("unit_price");
					if(u_price<0) {
						connect.rollback();
						System.out.println("\nUnit price less than zero Rolling back transaction");
					}
					else {
						connect.commit();
						System.out.println("\nTransaction committed");
					}
			}
			else
				System.out.println("No result found");
			
		} catch (Exception e) {
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
			connect2 = DriverManager.getConnection(connectionUrl);
			System.out.println("Connection Successful");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
	}
	private static void closeConnection() {
		try {
			if(connect!=null && connect2!=null) {
				connect.close();
				connect2.close();
				System.out.println("Connection closed");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
