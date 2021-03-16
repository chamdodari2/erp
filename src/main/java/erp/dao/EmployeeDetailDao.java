package erp.dao;

import erp.dto.Employee;
import erp.dto.EmployeeDetail;

public interface EmployeeDetailDao {
	EmployeeDetail selectEmployeeDetailByNo(Employee employee); // employee에 대한것을 숫자로 검색하는 메소드 매개변수는 employee로 받는다.

	int insertEmployeeDetail(EmployeeDetail employee); // 반환타입 : int 매개변수를 받아서 검색한다

	int updateEmployeeDetail(EmployeeDetail employee);

	int deleteEmployeeDetail(EmployeeDetail employee);
}
