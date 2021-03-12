package erp.dao.impl; //하청업체!!!!!!!

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import erp.dao.DepartmentDao;
import erp.dbcon.JdbcConn;
import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;

public class DepartmentDaoImpl implements DepartmentDao {
		
	private static final DepartmentDaoImpl instance = new DepartmentDaoImpl();
	
	public static DepartmentDaoImpl getInstance() {  //컨쉬s
		return instance;
		
	}
	///상속받은 인터페이스의  메소드 오버라이드

	@Override
	public List<Department> selectDepartmentByAll() {
		String sql = "select deptNo,deptName,floor from department"; 
		try(Connection con = JdbcConn.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			if(rs.next()) {
				List<Department> list = new ArrayList<>();
				do {list.add(getDepartment(rs));   //get 메서드가 인터페이스에 없으니까  추가로 만들어주기
					
				}while(rs.next());
				System.out.println(list.size());
				return list;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Department getDepartment(ResultSet rs) throws SQLException {
		//deptNo,deptName,floor from department
		int deptNo = rs.getInt("deptNo");
		String deptName = rs.getNString("deptname");
		int floor = rs.getInt("floor");
		return new Department (deptNo,deptName,floor);//null 지우기
	}

	@Override
	public Department selectDepartmentByNo(Department department) {
		String sql = "select deptNo,deptName,floor from department where deptNo =?";
		try(Connection con = JdbcConn.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, department.getDeptNo());
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return getDepartment(rs);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}

	@Override
	public int intsertDepartment(Department department) {
		String sql = "insert into department values(?,?,?)";
		try (Connection con = JdbcConn.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, department.getDeptNo());
			pstmt.setString(2, department.getDeptName());
			pstmt.setInt(3, department.getFloor());
			
			return pstmt.executeUpdate();			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
						
	
		return 0;
	}

	@Override
	public int updateDepartment(Department department) {	
	String sql = "update department set deptname=?, floor = ?  where deptno =?";
	try (Connection con = JdbcConn.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
		pstmt.setString(1, department.getDeptName());
		pstmt.setInt(2, department.getFloor());
		pstmt.setInt(3, department.getDeptNo());
		return pstmt.executeUpdate();			
	} catch (SQLException e) {	
		e.printStackTrace();
return 0;
	}
}

	@Override
	public int deleteDepartment(int deptNo) {
		String sql = "delete from department where deptno = ?";
		try (Connection con = JdbcConn.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, deptNo);
			return pstmt.executeUpdate();			
		} catch (SQLException e) {
		
			e.printStackTrace();
		return 0;
	}

	}
	@Override
	public List<Employee> selectEmplist(Department dept) {
		String sql = "select empno, empname, title, manager, salary, dept " + 
				"from employee " + 
				"where dept=(select deptno from department where deptno = ?)";
		
		try(Connection con = JdbcConn.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, dept.getDeptNo());
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					List<Employee> empList = new ArrayList<>();
					do {
						int empno = rs.getInt("empno");
						String empname = rs.getString("empname");
						Title title = new Title(rs.getInt("title"));
						Employee manager = new Employee(rs.getInt("manager"));
						int salary = rs.getInt("salary");		
						Department dept2 = new Department(rs.getInt("dept"));
						empList.add(new Employee(empno, empname, title, manager, salary, dept2));
//						empList.add(new Employee(rs.getInt("empno"), rs.getString("empname"), new Title(rs.getInt("title")), 
//								new Employee(rs.getInt("manager")), rs.getInt("salary"), new Department(rs.getInt("dept"))));
					} while(rs.next());
						
					return empList;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}
}
	
	

/*
 * @Override public int emplistDepartment(int deptNo) {
 * 
 * /////////////////// return 0; }
 */