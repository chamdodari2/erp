package erp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
		// TODO Auto-generated method stub
		return null;
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
	public int updateEmployeeDetail(EmployeeDetail employee) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEmployeeDetail(EmployeeDetail employee) {
		// TODO Auto-generated method stub
		return 0;
	}

}
