package erp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import erp.dao.EmployeeDetailDao;
import erp.dbcon.JdbcConn;
import erp.dto.Employee;
import erp.dto.EmployeeDetail;
import erp.ui.exception.SqlConstraintException;

public class EmployeeDetailDaoImpl implements EmployeeDetailDao {
	private static EmployeeDetailDaoImpl instance = new EmployeeDetailDaoImpl();

	public static EmployeeDetailDaoImpl getInstance() {	
	if (instance == null) {
			instance = new EmployeeDetailDaoImpl();
	}
		return instance;
	}
	private EmployeeDetailDaoImpl() {}
	
	@Override
	public EmployeeDetail selectEmployeeDetailByNo(Employee employee) {
		String sql = "select empno, pic, gender, hiredate "  // pass는 가져오면 안된다. 보여주면안된당!!
				+ "from emp_detail  "
				+ "where empno =?";
		try(Connection con = JdbcConn.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, employee.getEmpNo());
			
			try(ResultSet rs = pstmt.executeQuery()){
				if (rs.next()) {
					return getEmployeeDetail(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private EmployeeDetail getEmployeeDetail(ResultSet rs) throws SQLException {
		//select empno, pic, gender, hiredate, pass(얘는 가져오면안된다)
		
		int empNo = rs.getInt("empno");
		boolean gender = rs.getBoolean("gender");
		Date hireDate = rs.getTimestamp("hiredate");
		//String pass = rs.getNString("pass");
		byte[] pic = rs.getBytes("pic");	
		return new EmployeeDetail(empNo,gender,hireDate, pic);
	}
	@Override
	public int insertEmployeeDetail(EmployeeDetail employeeDetail) {
		String sql = "insert into emp_detail(empno, pic , gender, hiredate, pass) values(?,?,?,?, password(?))";
		try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, employeeDetail.getEmpNo());
			pstmt.setBytes(2, employeeDetail.getPic());
			pstmt.setBoolean(3, employeeDetail.isGender()); /////////////////////// 형변환 안해도된다!!!
			pstmt.setTimestamp(4, new Timestamp(employeeDetail.getHireDate().getTime()));//////////////////
			pstmt.setString(5, employeeDetail.getPass());
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new SqlConstraintException(e.getMessage(), e);

		}

	}

	@Override
	public int updateEmployeeDetail(EmployeeDetail employeeDetail) {
		String sql = "update  emp_detail "
				+ "set pic = ?, gender = ?, hiredate = ?, pass = password(?)"
				+ "where empno = ?";
		try (Connection con = JdbcConn.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setBytes(1, employeeDetail.getPic());
            pstmt.setBoolean(2, employeeDetail.isGender());
            pstmt.setTimestamp(3, new Timestamp(employeeDetail.getHireDate().getTime()));
            pstmt.setString(4, employeeDetail.getPass());
            pstmt.setInt(5, employeeDetail.getEmpNo());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		return 0;
	}

	@Override
	public int deleteEmployeeDetail(Employee employee) {
		String sql = "delete "
				+ "from emp_detail "
				+ "where empno = ?";
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
	public int deleteEmployeeDetail(EmployeeDetail employee) {
		// TODO Auto-generated method stub
		return 0;
	}


}
