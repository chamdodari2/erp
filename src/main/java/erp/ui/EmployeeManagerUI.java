package erp.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import erp.dto.Employee;
import erp.dto.EmployeeDetail;
import erp.service.EmployeeDetailService;
import erp.service.EmployeeService;
import erp.ui.content.AbstractContentPanel;
import erp.ui.content.EmployeeDetailPanel;
import erp.ui.content.EmployeePanel;
import erp.ui.list.AbstractCustomTablePanel;
import erp.ui.list.EmployeeTablePanel;

	@SuppressWarnings("serial")
	public class EmployeeManagerUI extends AbstractManagerUI<Employee> {  //AbstractManagerUI<Employee> 상속받음
	private EmployeeService service;									  //EmployeeService 객체생성
	private EmployeeDetailService detailService;						  //EmployeeDatailService 객체생성
	public EmployeeManagerUI() {										  //EmployeeManagerUI 생성자
			empListByTitleItem.setText(AbstractManagerUI.EMP_MENU);				// 부모UI에서 정의된 메소드 사용 -> empListByTitleItem.settext(EMP_MENU사용)
		}
		
	
	//메소드 오버라이드 1   -------->    	1. setService (사용할 Service 객체화 )
	@Override		//
	protected void setService() {  //////////위에서 선언하면 여기서 객체생성 해줘야한다.
	service = new EmployeeService();
	detailService = new EmployeeDetailService(); 
		
	}
	
	
	//메소드 오버라이드 2				--------> 2.tableLoadData (사용할 tableLoadData 설정)  사원리스트 출력하기
	@Override
	protected void tableLoadData() {   ///// 읽어올 데이터 설정
		((EmployeeTablePanel) pList).setService(service);       //EmployeeTablePanel로 형변환한 pList(얘는 또 custum)에 service를 매개변수로 넣음                                    //TitleTablePanel로 감싸주었음 안감싸주면 안먹는다
		pList.loadData();  //EmployeeService
	}
	//메소드 오버라이드3				----------->
	@Override					  
	protected AbstractContentPanel<Employee> createContentPanel()   {  
		EmployeePanel empPanel = new EmployeePanel();					//empPanel 패널에 EmployeePanel 모프
		empPanel.setService(service);									//empPanel에 EmployeeService
		return empPanel;
	}
	//메소드 오버라이드4
	@Override  
	protected AbstractCustomTablePanel<Employee> createTablePanel() {
		// TODO Auto-generated method stub
		return new EmployeeTablePanel();
	}
	
	//메소드 오버라이드5
	@Override   //메뉴구분
	protected void actionPerformedMenuGubun() {
	Employee emp = pList.getItem();
	//System.out.println(emp);
	EmployeeDetail empDetail = detailService.selectEmployeeDetailByEmpNo(emp);
	System.out.println(empDetail);
	
	//나중에 처리
	EmployeeDetailUI frame;
	if(empDetail ==null) {
		frame = new EmployeeDetailUI(true, detailService); // 유무에 따라서 추가하거나 빼거나하는 버튼 보이게,안보이게
	}else {
		frame = new EmployeeDetailUI(false, detailService); // 유무에 따라서 추가하거나 빼거나하는 버튼 보이게,안보이게
		frame.setDetailItem(empDetail); 
	//	JOptionPane.showMessageDialog(null,"세부정보 없음");
//		return;
	}
	//EmployeeDetailService service = new EmployeeDetailService();  이제 필요없다  걍서비스에서 디테일서비스로 바꿔줘서
//	 frame = new EmployeeDetailUI(false, detailService); // 유무에 따라서 추가하거나 빼거나하는 버튼 보이게,안보이게
	
//	frame.setDetailItem(empDetail);  //이거도 추가쓰
	//frame.setEmpNo(new Employee(2106)); // 이친구 정보를 기본으로 넣어놓기 이거대신 이제 위에한줄
	frame.setEmpNo(emp);
	frame.setVisible(true);
	
	/*JFrame subFrame = new JFrame("사원 세부 정보");
	subFrame.setBounds(this.getWidth(), this.getHeight(),450,500);
	EmployeeDetailPanel subDetailPanel = new EmployeeDetailPanel();
	subDetailPanel.setItem(empDetail);
	
	subFrame.add(subDetailPanel,BorderLayout.CENTER); 
	
	subFrame.setVisible(true);
	//throw new UnsupportedOperationException("제공되지 않음");
	*/	
	}
	
	
	//메소드 오버라이드 6
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
