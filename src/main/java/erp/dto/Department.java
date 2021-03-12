package erp.dto;

import java.util.List;

public class Department {
	private int deptNo;
	private String deptName;
	private int floor;
	//과제때문에 추가한거
	private  List<Employee> emplist;
	
	


	public Department(int deptNo) {
		this.deptNo = deptNo;
	}


	public Department(int deptNo, String deptName, int floor) { // 2.필드로 생성
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.floor = floor;
		
	}

	public int getDeptNo() {// 3.get set로 생성
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	@Override
	public String toString() {
		return String.format("Department [deptNo=%s, deptName=%s, floor=%s]", deptNo, deptName, floor);
	}


	public List<Employee> getEmplist() {
		return emplist;
	}


	
}
