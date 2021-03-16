package erp.ui.content;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;
import erp.service.EmployeeService;
import erp.service.TitleService;
import erp.ui.exception.InvalidChechException;

@SuppressWarnings("serial")
public class EmployeePanel extends AbstractContentPanel<Employee> implements ItemListener{  //제네릭을 안쓰면 스트링이 들어가서 직책 콤보박스 선택시 목록이 나오는데, 해당 클래스를 생성해서 출력해야하기때문에 제네릭을 쓰는게 좋다.
	private JTextField tfNo;								//사원번호 텍스트필드
	private JTextField tfName;								//사원명 텍스트필드
	private JComboBox<Title> cmbTitle;						//콤보박스에 직책테이블을 가져올거임
	private JComboBox<Employee> cmbManager;					//콤보박스에 사원테이블을 가져올거임.
	private JSpinner spinSalary;							//급여 넣어줄거임(아직 디비연동 아님)
	private JComboBox<Department> cmbDept;					//콤보박스에 부서테이블을 List로 가져올거임  아마 투스트링에서 지정한형식으로
	
	private EmployeeService service;  //얘에 대한 세터 가 필요하다       --> EmployeeService에서 정의해놓은  deptList메소드로 부서테이블을 가져올거임(부서테이블)
	private TitleService service2;           //-> (TitleService에서 정의해놓은 titleList메소드로  titleList를 가져올거임(직책테이블)
	////
	public EmployeePanel() { 								//실행시키면 띄울거
		
		initialize();
	}

	public EmployeeService getService() {					//위에서 선언한  getService 기본생성자?. EmployeeService에 있는것들 이용함. 리턴해주는건 service
		return service;
	}

	public void setService(EmployeeService service) {        //EmployeeService 타입의 생성자.   --> 매개변수는  service로 받는다
		this.service = service;								 //매개변수로 받은거를 service에 저장
		List<Department> deptList = service.showDeptList();		//Department만 담을수있는 deptList에  EmployeeService에 있는 메소드인 showDeptList()호출해서 담기 -> 부서테이블 내용 싹 담기는 메소드임
		DefaultComboBoxModel<Department> deptModel = new DefaultComboBoxModel<>(new Vector<>(deptList));  //디폴트콤보박스 모델 객체생성하고  Department만 담기로 하고 , 변수 deptModel에, 위의 부서테이블목록을 백터로 담고, 그걸 다시 deptModel에 담는다. 
		cmbDept.setModel(deptModel);  //벡터로 변환한 deptModel을 콤보박스인 cmbDept에 setModel메소드(원래 콤보박스라면 쓸수있는 메소드임.)로 담는다.
		
		//////////////////////콤보박스인 cmbDept에는 이제 부서목록 다 담았당!!!
		
		
		
		//// 여기에 넣어준다 직책
		List<Title> titleList = service.showTitleList();												//1.Title만 넣을수있는 TitleList에 EmployeeService에 있는 메소드인 showTitleList()호출해서 직책테이블  내용 싹 담기
		DefaultComboBoxModel<Title> titleModel = new DefaultComboBoxModel<>(new Vector<>(titleList));   //2.Title테이블 담을수있는 디폴트콤보박스모델 객체생성하고,  위에서 담은 titleList를 벡터로 변환해서 titleModel변수에 담는다 
		cmbTitle.setModel(titleModel);																    //3. 위에서 벡터로 변환한 titleList를 담은 titleModel을  매개변수로 해서 setModel()메소드를 이용해서 cmbTitle콤보박스에 담아준다
		
		cmbDept.setSelectedIndex(-1);
		cmbTitle.setSelectedIndex(-1);
	}

	private void initialize() {
		setBorder(new TitledBorder(null, "사원 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JPanel pItem = new JPanel();
		add(pItem);
		pItem.setLayout(new GridLayout(0, 2, 10, 0));
		
		JLabel lblNo = new JLabel("사원번호");
		lblNo.setHorizontalAlignment(SwingConstants.RIGHT);
		pItem.add(lblNo);
		
		tfNo = new JTextField();
		tfNo.setColumns(10);
		pItem.add(tfNo);
		
		JLabel lblName = new JLabel("사원명");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		pItem.add(lblName);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		pItem.add(tfName);
		

		JLabel lblDept = new JLabel("부서");
		lblDept.setHorizontalAlignment(SwingConstants.RIGHT);
		pItem.add(lblDept);
		
		cmbDept = new JComboBox<>();
		
		//아이템리스너 달기 
		cmbDept.addItemListener(this);
		//
	
		pItem.add(cmbDept);
		JLabel lblManager = new JLabel("직속상사");
		lblManager.setHorizontalAlignment(SwingConstants.RIGHT);
		pItem.add(lblManager);
		
		cmbManager = new JComboBox<>();
		pItem.add(cmbManager);
		JLabel lblTitle = new JLabel("직책");
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		pItem.add(lblTitle);
		
		cmbTitle = new JComboBox<>();
		pItem.add(cmbTitle);
		////////////안보이게하기
		cmbDept.setSelectedIndex(-1);
		cmbTitle.setSelectedIndex(-1);
		
		/////////////
		JLabel lblSalary = new JLabel("급여");
		lblSalary.setHorizontalAlignment(SwingConstants.RIGHT);
		pItem.add(lblSalary);
		
		spinSalary = new JSpinner();
		spinSalary.setModel(new SpinnerNumberModel(2000000, 1500000, 5000000, 100000));
		pItem.add(spinSalary);
		
	}



	//Employee(int empNo, String empName, 
	//Title title, Employee manager, 
	//int salary, Department dept)
		
	
	

	public void clearTf() {
		tfNo.setText("");
		tfName.setText("");
		cmbTitle.setSelectedIndex(-1);
		cmbDept.setSelectedIndex(-1);
		cmbManager.setSelectedIndex(-1);
		spinSalary.setValue(2000000);
		
	}
	
	public void itemStateChanged(ItemEvent e ) {
		if (e.getSource() == cmbDept) {
			itemStateChangedCmbDept(e);
		}
		
	}
	protected void itemStateChangedCmbDept(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED) {
			Department dept = (Department) cmbDept.getSelectedItem();
			List<Employee> empList =  service.SelectEmployeeByDepartment(dept);
			//짇속상사가 없는 경우 추가
			if(empList == null) {
				empList = new ArrayList<>();
				
			}	
			DefaultComboBoxModel<Employee> model = new DefaultComboBoxModel<> (new Vector<>(empList));
			cmbManager.setModel(model);
			cmbManager.setSelectedIndex(-1);

		
		}
		
		
	}

	@Override
	public void setItem(Employee item) {
		tfNo.setText(item.getEmpNo() + "");
		tfName.setText(item.getEmpName());
		cmbDept.setSelectedItem(item.getDept());
		cmbManager.setSelectedItem(item.getManager());
		cmbTitle.setSelectedItem(item.getTitle());  //이퀄즈필요!
		spinSalary.setValue(item.getSalary());
	}
	@Override
	public Employee getItem() {
		int empNo = Integer.parseInt(tfNo.getText().trim());
		String empName = tfName.getText().trim();
		Title title = (Title) cmbTitle.getSelectedItem();  // item은 title객체
		Employee manager = (Employee) cmbManager.getSelectedItem();
		int salary = (int) spinSalary.getValue();
		Department dept = (Department) cmbDept.getSelectedItem();
		return new Employee(empNo, empName, title, manager, salary, dept);
	}

	@Override
	public void validCheck() {
		if (tfNo.getText().contentEquals("") || tfName.getText().equals("") 
				|| cmbTitle.getSelectedIndex() == -1 
				|| cmbDept.getSelectedIndex() == -1
				|| cmbManager.getSelectedIndex() == -1) {
			throw new InvalidChechException();
		}
		
	}
}
