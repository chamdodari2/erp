package erp.service;

import java.util.List;

import erp.dao.EmployeeDao;
import erp.dao.TitleDao;
import erp.dao.impl.EmployeeDaoImpl;
import erp.dao.impl.TitleDaoImpl;
import erp.dto.Employee;
import erp.dto.Title;

public class TitleService {

	private TitleDao dao = TitleDaoImpl.getInstance();  //여기에 있는 메소드를 이용해서 위임하는것
	private EmployeeDao empDao = EmployeeDaoImpl.getInstance();//
	//department도 추가해주기!!!
	
	public List<Title> showTitles(){
		return dao.selectTitleByAll();  //dao가 수정되면 service만 수정하면된다! 유지보수 용이!!!
	}
	public void addTitle(Title title) {
		dao.insertTitle(title);  //원래는 1이 아니면 예외 던져줘야한다.
	}
	
	public void removeTitle(Title title) {
		dao.deleteTitle(title.gettNo());
	}
	public void modifyTitle(Title title) {
		dao.updateTitle(title);
	}
	
	public List<Employee> showEmployeeGroupByTitle(Title title){
		return empDao.SelectEmployeeByTitle(title);
	}
	
	
	
}
