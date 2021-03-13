package erp.service;

import java.util.List;

import erp.dao.DepartmentDao;
import erp.dao.EmployeeDao;
import erp.dao.TitleDao;
import erp.dao.impl.DepartmentDaoImpl;
import erp.dao.impl.EmployeeDaoImpl;
import erp.dao.impl.TitleDaoImpl;
import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;

public class DepartmentService {
	private TitleDao titleDao = TitleDaoImpl.getInstance();  //여기에 있는 메소드를 이용해서 위임하는것 ( TitleDao 에 있는 메소드들을 TitleImpl에서 오버라이딩 했고, 그것들을 위임받겠다
	private EmployeeDao empDao = EmployeeDaoImpl.getInstance();// 여기는 EmployeeImpl에서 오버라이딩한 메소드들을 위임받는다
	//department도 추가해주기!!!
	private DepartmentDao deptDao = DepartmentDaoImpl.getInstance();  //여기에 있는 메소드를 이용해서 위임하는것
	
	
	
	public List<Department> showDepartment(){  //
		return deptDao.selectDepartmentByAll();  //dao가 수정되면 service만 수정하면된다! 유지보수 용이!!!
	}
	public void addDepartment(Department department) {
		deptDao.intsertDepartment(department);  //원래는 1이 아니면 예외 던져줘야한다.
	}
	
	public void removeDepartment(Department department) {
		deptDao.deleteDepartment(department.getDeptNo());
	}
	public void modifyDepartment(Department department) {
		deptDao.updateDepartment(department);
	}
	
	public List<Employee> showEmployeeGroupByDepartment(Department department){  //동일부서 사람 보기
		return empDao.SelectEmployeeByDepartment(department);
	}
	
}
