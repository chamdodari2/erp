package erp.service;

import java.util.List;

import erp.dao.DepartmentDao;
import erp.dao.impl.DepartmentDaoImpl;
import erp.dto.Department;

public class DepartmentService {

	private DepartmentDao dao = DepartmentDaoImpl.getInstance();  //여기에 있는 메소드를 이용해서 위임하는것
	
	public List<Department> showDepartment(){  //Titles로 해야하나??
		return dao.selectDepartmentByAll();  //dao가 수정되면 service만 수정하면된다! 유지보수 용이!!!
	}
}
