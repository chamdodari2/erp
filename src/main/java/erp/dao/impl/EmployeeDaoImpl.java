package erp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import erp.dao.EmployeeDao;
import erp.dbcon.JdbcConn;
import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;
import erp.ui.exception.SqlConstraintException;

public class EmployeeDaoImpl implements EmployeeDao {// EmployeeDao를 구현하는  EmployeeDaoImpl 클래스 선언

	private static EmployeeDaoImpl instance = new EmployeeDaoImpl();  //상수 선언. EmployeeDaoImpl 객체생성.  타입의   참조변수 instance 선언

	public static EmployeeDaoImpl getInstance() {	// 반환타입 EmployeeDaoImpl인  getInstance메소드 선언. 결과값 : 참조변수인 instance
		if (instance == null) {
			instance = new EmployeeDaoImpl();
		}
		return instance;      //싱글턴패턴은 이게 다다.

	}

	//구현
	@Override
	public  List<Employee> selectEmployeeByAll() { // selectEmployeeByAll() 구현 : 조건없이 select empno,
													// empname,title,manager,salary,dept from employee;
		
		//디비버에서 입력하던 명령문 입력하려면 문장이라서 String 변수에 저장해서 써야한다.
// 내가한거.	String sql = "select empno, emp_name, title_no, title_name, manager, mgr_name, salary, dept_no, dept_name, floor from vw_employee";
//		String sql = "select empno, emp_name, title, manager, salary, dept from employee";
		
		
		
		String sql = "select  empno,empname,title_no,title_name,manager_no, "  //교수님이하신거
				+ "manager_name,salary,deptno,deptname,floor" + 
				 " from vw_full_employee";   // " from vw_full_employee";
		// Connection 타입의 참조변수 con 선언,  
		try (Connection con = JdbcConn.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				List<Employee> list = new ArrayList<>();
				do {
					list.add(getEmployee(rs));

				} while (rs.next());
				return list;
			}
			//예외처리
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private Employee getEmployee(ResultSet rs) throws SQLException {// getEmployee(ResultSet rs)
//		empno,emp_name,title_no,title_name,manager,mgr_name,salary,dept_no,dept_name,floor
		int empno = rs.getInt("empno");
		String empname = rs.getString("empname");
		
		Title title = null;
		Employee manager = null;
		int salary = 0;
		Department dept = null;
		
		try {//1
		title = new Title(rs.getInt("title_no"));
		manager = new Employee(rs.getInt("manager_no"));
		salary = rs.getInt("salary");		
		 dept = new Department(rs.getInt("deptno"));
		//존재할 경우 찍어주기. if끼리 따로 빼기
		}catch (SQLException e) {	}
			try {
			title.settName(rs.getNString("title_name"));
		}catch(SQLException e) {}		
			try {
				manager.setEmpName(rs.getNString("manager_name"));
			}catch (SQLException e ) {}
			try {
				dept.setDeptName(rs.getString("deptname"));//////////////////////이름 동일한지 확인하기!
			}catch(SQLException e) {}
			try {
				 dept.setFloor(rs.getInt("floor"));
			} catch(SQLException e ) {}
			
			
		/*if 문 다시 지움
		 * /if(rs.getString("title_name") !=null) {
		 *	 title.settName(rs.getNString("title_name"));
		 * if (rs.getString("manager_name") != null) {
			manager.setEmpName(rs.getNString("manager_name"));
		}			
	
		if(rs.getNString("deptname") !=null && rs.getInt("floor") != 0) {
			dept.setDeptName(rs.getString("deptname"));
			dept.setFloor(rs.getInt("floor"));*/
			
		/* 이거는 if로 빼주었다가 그래도 에러떠서 다시 지우고 최종적으로 try-cath로 변경되었음
		 * title.settName(rs.getString("title_name"));
		 * manager.setEmpName(rs.getString("mgr_name"));
		 * dept.setDeptName(rs.getString("dept_name"));
		 * dept.setFloor(rs.getInt("floor"));
		 */
		
		return new Employee(empno, empname, title, manager, salary, dept);

	}

	@Override
	public Employee selectEmployeeByNo(Employee Employee) { // 조건 검색 : where empno = 1003
		/* 내가한거
		 * String sql ="select empno, emp_name, title_no, title_name, manager, " +
		 * "mgr_name, salary, dept_no, dept_name, floor from vw_employee where empno = ?"
		 * ;
		 */
		//교수님이 하신거
		String sql = "select  empno, empname , title as title_no, "
				+ " manager as manager_no, salary, dept as deptno "
				+ " from employee" +
				" where empno = ?";
		try (Connection con = JdbcConn.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, Employee.getEmpNo());  //첫번쨰 매개변수에 employee에 있는 getempno를 넣어줘야한다.
			try (ResultSet rs = pstmt.executeQuery()) {  //
				
				if (rs.next()) {  
					return getEmployee(rs);
				}

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int insertEmployee(Employee employee) { // 입력
		String sql = "insert  into employee values (?,?,?,?,?,?)";
		try (Connection con = JdbcConn.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, employee.getEmpNo());
			pstmt.setString(2, employee.getEmpName());
			pstmt.setInt(3, employee.getTitle().gettNo()); /////////////////////// 형변환 안해도된다!!!
			pstmt.setInt(4, employee.getManager().getEmpNo());//////////////////
			pstmt.setInt(5, employee.getSalary());
			pstmt.setInt(6, employee.getDept().getDeptNo());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new SqlConstraintException(e.getMessage(),e);
			
		}

	}

	@Override
	public int updateEmployee(Employee employee) { // 수정되었음.
		String sql = "update employee set empname = ?, title = ?, manager = ?, salary = ?, dept = ?"
				+ " where empno = ?";  
		try (Connection con = JdbcConn.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
		
			pstmt.setString(1, employee.getEmpName());
			pstmt.setInt(2, employee.getTitle().gettNo()); 
			pstmt.setInt(3, employee.getManager().getEmpNo());
			pstmt.setInt(4, employee.getSalary());
			pstmt.setInt(5, employee.getDept().getDeptNo());
			pstmt.setInt(6, employee.getEmpNo());
			System.out.println(pstmt);  //중요!!! 에러뜰때 활용해서 디비버에서 수행해보면 뭐가문젠지 알수있다!!!!!!!!!!!
			return pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteEmployee(Employee employee) { // 삭제
		String sql = "delete from employee where empno = ?";
		try (Connection con = JdbcConn.getConnection(); 
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, employee.getEmpNo());
			return pstmt.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public List<Employee> SelectEmployeeByTitle(Title title) {
		String sql = "select  empname, empno "
				+ "from employee e  "
				+ "join title t "
				+ "on e.title = t.tno   where tno = ?";
		try (Connection con = JdbcConn.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, title.gettNo());  //첫번쨰 매개변수에 
			
			try (ResultSet rs = pstmt.executeQuery()) {  //			
				if (rs.next()) {				
					List<Employee> list = new ArrayList<>();
					do {					
						list.add(getEmployee(rs));
					}while (rs.next());					
					return  list; 
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> SelectEmployeeByDepartment(Department department) {			
		String sql = "select empno, empname from employee e " + 
				"join department d  " + 
				"	on e.dept  =d.deptNo " + 
				"	where deptNo = ?";
		try (Connection con = JdbcConn.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, department.getDeptNo());  //첫번쨰 매개변수에 
			
			try (ResultSet rs = pstmt.executeQuery()) {  //			
				if (rs.next()) {				
					List<Employee> list = new ArrayList<>();
					do {
						list.add(getEmployee(rs));
					}while (rs.next());					
					return  list; 
				}

			}
		} catch (SQLException e) {
 
			e.printStackTrace();
		}
		return null;
	}


}
