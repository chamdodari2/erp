package erp;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.EmployeeDetail;
import erp.dto.Title;
import erp.service.EmployeeDetailService;
import erp.service.EmployeeService;
import erp.ui.content.EmployeeDetailPanel;
import erp.ui.content.EmployeePanel;
import erp.ui.list.EmployeeTablePanel;

@SuppressWarnings("serial")
public class TestFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnAdd;
	private EmployeePanel pEmpItem;
	private JButton btnSet;
	private JButton btnCancel;
	private EmployeeTablePanel pList;
	private EmployeeDetailPanel panel;
	private JPanel panel_1;
	private JButton btnGet;
	private JButton btnSet2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TestFrame() {
		initialize();
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 577, 1024);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		EmployeeService service = new EmployeeService();
		pEmpItem = new EmployeePanel();
		pEmpItem.setService(service);
		
		contentPane.add(pEmpItem);
		
		JPanel pBtns = new JPanel();
		contentPane.add(pBtns);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);
		
		btnSet = new JButton("Set");
		btnSet.addActionListener(this);
		pBtns.add(btnSet);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtns.add(btnCancel);
		
		pList = new EmployeeTablePanel();
		pList.setService(service);
		pList.loadData();
		contentPane.add(pList);
		
		panel = new EmployeeDetailPanel();
		panel.setTfEmpno(new Employee(1003)); ///////////////해줘야 사원번호 들어간다. 아니면 공백에러뜬다!!    ---> 원래는 객체안넣어줘도됐음. 
		contentPane.add(panel);
		
		panel_1 = new JPanel();
		panel.setTfEmpno(new Employee(1003));
		contentPane.add(panel_1);
		
		btnGet = new JButton("가져오기");
		btnGet.addActionListener(this);
		panel_1.add(btnGet);
		
		btnSet2 = new JButton("불러오기");
		btnSet2.addActionListener(this);
		panel_1.add(btnSet2);
	}
//	public void setTfEmpno(int empNo) {
	//	tfEmpNo.setText(String.valueOf(empNo));/////////////////////////////////////이거 어디에있어야???
	//}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSet2) {
			actionPerformedBtnSet2(e);
		}
		if (e.getSource() == btnGet) {
			actionPerformedBtnGet(e);
		}
		try {
			if (e.getSource() == btnCancel) {
				actionPerformedBtnCancel(e);
			}
			if (e.getSource() == btnSet) {
				actionPerformedBtnSet(e);
			}
			if (e.getSource() == btnAdd) {
				actionPerformedBtnAdd(e);
			}
		}catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			e1.printStackTrace();
		}
	}
	protected void actionPerformedBtnAdd(ActionEvent e) {  //추가버튼을 누르면 
		Employee emp = pEmpItem.getItem();				   //(pEmpItem은 EmployeePanel모프) -> 
		String message = String.format(
				"empNo %d%n"
				+ "empName %s%n"
				+ "title(%d)%n"
				+ "dept(%d)%n"
				+ "manager(%s)%n"
				+ "salary(%s)"
				, emp.getEmpNo()
				, emp.getEmpName()
				, emp.getTitle().gettNo()
				, emp.getDept().getDeptNo()
				, emp.getManager().getEmpName()
				, emp.getSalary());
		JOptionPane.showMessageDialog(null, message);
	}
	
	protected void actionPerformedBtnSet(ActionEvent e) {
		Employee emp = new Employee(
				1003
				, "조민희"
				, new Title(3)
				, new Employee(4377)
				, 3000000
				, new Department(2));
		pEmpItem.setItem(emp);
	}
	protected void actionPerformedBtnCancel(ActionEvent e) {
		pEmpItem.clearTf();
	}
	
	
	protected void actionPerformedBtnGet(ActionEvent e) {  //가져오기 버튼 눌렀을때 작동
	EmployeeDetail employeeDetail = panel.getItem();
	JOptionPane.showMessageDialog(null,employeeDetail);
			
			//	panel.settfEmpno(1003); 이거 어디에 넣어야할지? 소문자 대문자?? 수정? 
		
	}
	protected void actionPerformedBtnSet2(ActionEvent e) {  //불러오기 누르면
		EmployeeDetailService service = new EmployeeDetailService();
		EmployeeDetail empDetail = service.selectEmployeeDetailByEmpNo (new Employee(1003));
		System.out.println(empDetail);
		panel.setItem(empDetail);
	}
}


