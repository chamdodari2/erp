package erp.ui;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import erp.dto.Employee;
import erp.service.EmployeeService;
import erp.ui.content.AbstractContentPanel;
import erp.ui.content.EmployeePanel;
import erp.ui.list.AbstractCustomTablePanel;
import erp.ui.list.EmployeeTablePanel;

public class EmployeeManagerUI extends AbstractManagerUI<Employee> {
	public EmployeeManagerUI() {
	}
	private EmployeeService service;
	@Override
	protected void setService() {
		service = new EmployeeService();
		
	}

	@Override
	protected void tableLoadData() {
		((EmployeeTablePanel) pList).setService(service);   //TitleTablePanel로 감싸주었음 안감싸주면 안먹는다
		pList.loadData();
	}

	@Override
	protected AbstractContentPanel<Employee> createContentPanel() {
		EmployeePanel empPanel = new EmployeePanel();
		empPanel.setService(service);	
		return empPanel;
	}

	@Override  //테이블
	protected AbstractCustomTablePanel<Employee> createTablePanel() {
		// TODO Auto-generated method stub
		return new EmployeeTablePanel();
	}

	@Override   //메뉴구분
	protected void actionPerformedMenuGubun() {
	throw new UnsupportedOperationException("제공되지 않음");
		
	}

	@Override  //메뉴업데이트
	protected void actionPerformedMenuUpdate() {
		Employee updateEmp = pList.getItem();
		pContent.setItem(updateEmp);
		btnAdd.setText("수정");
		
	}

	@Override  //메뉴딜리트
	protected void actionPerformedMenuDelete() {
		Employee delEmp = pList.getItem();
		service.removeEmployee(delEmp);
		pList.loadData();
		JOptionPane.showInternalMessageDialog(null, delEmp.getEmpName());
		//j옵션, 널, 딜emp + 삭제되었습니다
		
	}

	@Override////////////////////////////////
	protected void actionperformedBtnUpdate(ActionEvent e) {
		Employee updateEmp = pList.getItem();
		service.modifyEmployee(updateEmp);
		pList.loadData();
		pContent.clearTf();
		btnAdd.setText("추가");

		
		JOptionPane.showInternalMessageDialog(null, updateEmp.getEmpName());
	}

	@Override/////////////////////////
	protected void actionPerformedBtnAdd(ActionEvent e) {
		Employee empl= pContent.getItem();
		service.addEmployee(empl);
		pList.loadData();
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, empl + " 추가했습니다.");
		
		
		
	}

}
