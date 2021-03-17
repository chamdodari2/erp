package erp.ui;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import erp.dto.Department;
import erp.dto.Employee;
import erp.service.DepartmentService;
import erp.ui.content.AbstractContentPanel;
import erp.ui.content.DeptPanel;
import erp.ui.list.AbstractCustomTablePanel;
import erp.ui.list.DepartmentTablePanel;

public class DepartmentManagerUI extends AbstractManagerUI<Department> {
private DepartmentService service;  //복붙쓰
	
	
	public DepartmentManagerUI() {
		empListByTitleItem.setText(AbstractManagerUI.DEPT_MENU);
	}
	
	
	
	
	@Override
	protected void setService() {
	service = new DepartmentService();  //서비스에 있는 메소드 쓸거얌
			
	}

	@Override
	protected void tableLoadData() {
		((DepartmentTablePanel)pList).setService(service);  //DepartmentTablePanel에 생성해놓은 메서드임. department에 있는거 가져오기
		pList.loadData();// 		
		
	}

	@Override
	protected AbstractContentPanel<Department> createContentPanel() {
		// TODO Auto-generated method stub
		return new DeptPanel();
	}

	@Override
	protected AbstractCustomTablePanel<Department> createTablePanel() {
		
		return new DepartmentTablePanel();
	}

	@Override
	protected void actionPerformedMenuGubun() {
	    Department selDept = pList.getItem();
	      List<Employee> list = service.showEmployeeGroupByDepartment(selDept);
	      if (list == null) {
	         JOptionPane.showMessageDialog(null, "해당 부서 사원 없음");
	      } else {
	         JOptionPane.showMessageDialog(null, list);
	      }

		
	}

	@Override
	protected void actionPerformedMenuUpdate() {	
			Department updateDepartment = pList.getItem();
			pContent.setItem(updateDepartment);
			btnAdd.setText("수정");
		
	}

	@Override
	protected void actionPerformedMenuDelete() {
			Department delDepartment = pList.getItem();
			service.removeDepartment(delDepartment);
			pList.loadData();
			JOptionPane.showMessageDialog(null, delDepartment + "삭제 되었습니다.");
			
	}

	@Override
	protected void actionperformedBtnUpdate(ActionEvent e) {
		Department uptadeDepartment = pContent.getItem();
		service.modifyDepartment(uptadeDepartment);
		pList.loadData();
		pContent.clearTf();
		btnAdd.setText("추가");
		JOptionPane.showMessageDialog(null, uptadeDepartment.getDeptName() + "수정되었습니다.");
	}

	@Override
	protected void actionPerformedBtnAdd(ActionEvent e) {
		Department department = pContent.getItem();
		service.addDepartment(department);
		pList.loadData();
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, department + " 추가했습니다.");
		
	}

}
