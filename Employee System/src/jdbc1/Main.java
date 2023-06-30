package jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import jdbc.Employee;

public class Main {
	static int empId = 0;
	static String empName = null;
	static double empSalary = 0.0;
	static boolean empResigned = false;
	static boolean valid = false;
	static Connection con;
	static PreparedStatement ps;
	static ResultSet rs;
	static Statement st;
	static Map<Integer, Employee> map = new HashMap<Integer, Employee>();
	static Scanner scan = new Scanner(System.in);

	public static void main(String args[]) throws SQLException {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "root");
		st = con.createStatement();
		do {
			display();
			System.out.println("enter a number from 1 to 7");
			String input = scan.next();
			switch (input) {
			case "1":
				addEmployee();
				break;
			case "2":
				showEmployee();
				break;
			case "3":
				updateEmp();
				break;
			case "4":
				deleteEmp();
				break;
			case "5":
				displayEmp();
				break;
			case "6":
				displayResigned();
				break;
			case "7":
				System.out.println("Thank you");
				System.exit(0);
			default:
				System.out.println("Invalid input Enter 1 to 7");
				break;
			}
			
		} while (true);
		}

	public static void display() {
		System.out.println("CUI menu\r\n" + "1.Add employee \r\n" + "2.View employee\r\n" + "3.Update a record\r\n"
				+ "4.delete a record\r\n" + "5.show employee in Hashmap.\r\n"
				+ "6.Remove resigned employees from employeeMap\r\n" + "7.exit");
	}

	public static void addEmployee() throws SQLException {
		do {
			System.out.println("Enter employee name");
			empName = scan.next();
			valid = empName.matches("[A-Za-z]*");
			if (!valid) {
				System.out.println("Input invalid type.");
			}
		} while (!valid);

		do {
			System.out.println("Enter employee salary");
			if (scan.hasNextDouble()) {
				empSalary = scan.nextDouble();
				valid = true;
			} else {
				System.out.println("Enter valid Salary");
				valid = false;
				scan.next();
			}
		} while (!valid);

		do {
			System.out.println("Enter employee resigned True or False?");
			if (scan.hasNextBoolean()) {
				empResigned = scan.nextBoolean();
				valid = true;
			} else {
				System.out.println("enter valid BOOlean value");
				valid = false;
				scan.next();
			}
		} while (!valid);

		String query = "insert into employee(emp_name,emp_salary,emp_resigned) values(?,?,?)";
		ps = con.prepareStatement(query);
		ps.setString(1, empName);
		ps.setDouble(2, empSalary);
		ps.setBoolean(3, empResigned);
		int a = ps.executeUpdate();
		if (a != 0) {
			System.out.println("Employee inserted successfully.");
		}
		System.out.println("Employee added Successfully.");
		//ps.close();
	}

	public static void showEmployee() throws SQLException {
		String query1 = "select * from employee";
		rs = st.executeQuery(query1);

		System.out.printf("%5s %15s %15s %15s", "ID", "Name", "Salary", "Resigned");
		while (rs.next()) {

			System.out.println();
			System.out.printf("%5s %15s %15s %15s", rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getBoolean(4));
			System.out.println();
		}
//		rs.close();

	}

	public static void updateEmp() throws SQLException {
		switch1: {
			do {
				System.out.println("Enter employee Id");
				if (scan.hasNextInt()) {
					empId = scan.nextInt();
					valid = true;
				} else {
					System.out.println("enter valid id");
					scan.next();
				}

			} while (!valid);
			ps = con.prepareStatement("select emp_id from employee where emp_id=?");
			ps.setInt(1, empId);
			rs = ps.executeQuery();
			if (!rs.next()) {
				System.out.println("Id not found");
				break switch1;
			}

			do {
				System.out.println("Enter employee salary");
				if (scan.hasNextDouble()) {
					empSalary = scan.nextDouble();
					valid = true;
				} else {
					System.out.println("Enter valid Salary");
					valid = false;
					scan.next();
				}
			} while (!valid);

			String query2 = ("update employee set emp_salary=? where emp_id=?");

			ps = con.prepareStatement(query2);
			ps.setInt(2, empId);
			ps.setDouble(1, empSalary);
			int b = ps.executeUpdate();
			if (b != 0) {
				System.out.println("Employee updated successfully.");
			}
		//	ps.close();
			//rs.close();
		}
	}

	public static void deleteEmp() throws SQLException {
		switch1: {

			do {
				System.out.println("Enter employee Id");
				if (scan.hasNextInt()) {
					empId = scan.nextInt();
					valid = true;
				} else {
					System.out.println("enter valid id");
					scan.next();
				}
			} while (!valid);
			ps = con.prepareStatement("select emp_id from employee where emp_id=?");
			ps.setInt(1, empId);
			rs = ps.executeQuery();
			if (!rs.next()) {
				System.out.println("Id not found");
				break switch1;
			}

			String query3 = "delete from employee where emp_id=?;";
			ps = con.prepareStatement(query3);
			ps.setInt(1, empId);
			int c = ps.executeUpdate();
			if (c != 0) {
				System.out.println("Employee deleted successfully.");
			}
		//	ps.close();
			//rs.close();
		}
	}

	public static void displayEmp() throws SQLException {
		String query4 = "select * from employee";
		st = con.createStatement();
		rs = st.executeQuery(query4);

		while (rs.next()) {
			int empId = rs.getInt("emp_id");
			String empName = rs.getString("emp_name");
			Double empSalary = rs.getDouble("emp_salary");
			boolean empResigned = rs.getBoolean("emp_resigned");

			Employee emp = new Employee(empName, empSalary, empResigned);
			map.put(empId, emp);
		}
		System.out.printf("%5s %15s %15s %15s", "ID", "Name", "Salary", "Resigned");
		System.out.println();
		for (Map.Entry<Integer, Employee> m : map.entrySet()) {
			int empId = m.getKey();
			Employee emp = m.getValue();
			String empName = emp.getEmpName();
			Double empSalary = emp.getEmpSalary();
			boolean empResigned = emp.getEmpResigned();
			System.out.printf("%5s %15s %15s %15s", empId, empName, empSalary, empResigned);
			System.out.println();
		}
		//ps.close();
		//rs.close();
	}

	public static void displayResigned() {
		System.out.printf("%5s %15s %15s %15s", "ID", "Name", "Salary", "Resigned");
		System.out.println();
		for (Map.Entry<Integer, Employee> m : map.entrySet()) {
			int empId = m.getKey();
			Employee emp = m.getValue();
			if (!emp.getEmpResigned()) {
				String empName = emp.getEmpName();
				Double empSalary = emp.getEmpSalary();
				boolean empResigned = emp.getEmpResigned();
				System.out.printf("%5s %15s %15s %15s", empId, empName, empSalary, empResigned);
				System.out.println();
			}
		}

		
	}
	
}
