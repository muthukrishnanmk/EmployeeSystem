package jdbc;

public class Employee {
	private int empId;
	private String empName;
	private double empSalary;
	private Boolean empResigned;

	public int getEmpId() {
		return empId;
	}


	public void setEmpId(int empId) {
		this.empId = empId;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public double getEmpSalary() {
		return empSalary;
	}


	public void setEmpSalary(double empSalary) {
		this.empSalary = empSalary;
	}


	public Boolean getEmpResigned() {
		return empResigned;
	}


	public void setEmpResigned(Boolean empResigned) {
		this.empResigned = empResigned;
	}


	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empSalary=" + empSalary
				+ ", empResigned=" + empResigned + "]";
	}


	public Employee(String empName, double empSalary, Boolean empResigned) {
		this.empName = empName;
		this.empSalary = empSalary;
		this.empResigned = empResigned;
	}

}
