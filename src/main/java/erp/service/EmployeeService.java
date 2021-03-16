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

public class EmployeeService {
	private DepartmentDao deptDao =  DepartmentDaoImpl.getInstance();
	private EmployeeDao empDao = EmployeeDaoImpl.getInstance();
	
	//부서목록을 가져오기
	public  List<Department> showDeptList(){
		return deptDao.selectDepartmentByAll();   //deptDao에 있는 메소드  selectDepartmentByAll 호출 ----> 부서테이블의 모든것 출력
	}
	 ///1 직책목록 가져오기
	private TitleDao titleDao =  TitleDaoImpl.getInstance();
	public List<Title> showTitleList(){ 
		return  titleDao.selectTitleByAll();
	}
	
	public List<Employee> SelectEmployeeByDepartment(Department department) {
		return empDao.SelectEmployeeByDepartment(department);
		}
		
		/// 사원번호~부서 
	
	public List<Employee> showEmployee() {////////////////////////////////////////////////////
		
		return empDao.selectEmployeeByAll();
	}
	
	public void removeEmployee(Employee employee) {
		empDao.deleteEmployee(employee);
	}
	public void modifyEmployee(Employee employee) {
		empDao.updateEmployee(employee);
	}
	public void addEmployee(Employee employee) {
		empDao.insertEmployee(employee);
	}
	
	
	
}
