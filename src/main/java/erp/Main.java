package erp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import erp.ui.DepartmentManager;
import erp.ui.EmployeeManagerUI;
import erp.ui.TitleManager;
import erp.ui.TitleManagerUI;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnTitle;
	private JButton btnDepartment;
	private JButton btnEmployee;
	private TitleManagerUI titleFrame;								//TitleManagerUI 모프
	private DepartmentManager deptFrame;							//DepartmentUI 모프
	private EmployeeManagerUI empFrame;								//EmployeeManagerUI 모프

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		createFrame();

		initialize();
	}

	public void createFrame() {
		titleFrame = new TitleManagerUI();
		titleFrame.setTitle("직책관리");//

		deptFrame = new DepartmentManager();
		deptFrame.setTitle("부서관리");//

		empFrame = new EmployeeManagerUI();
		empFrame.setTitle("사원관리");//
	}

	private void initialize() {
		setTitle("Erp Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 107);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));

		btnTitle = new JButton("직책 관리");
		btnTitle.addActionListener(this);
		contentPane.add(btnTitle);

		btnDepartment = new JButton("부서 관리");
		btnDepartment.addActionListener(this);
		contentPane.add(btnDepartment);

		btnEmployee = new JButton("사원 관리");     // 액션퍼폼드 안에 있던거 다 일로 옮겨주고 필드변수로 선언함
		btnEmployee.addActionListener(this);
		contentPane.add(btnEmployee);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEmployee) {
			actionPerformedBtnEmployee(e);
		}
		if (e.getSource() == btnDepartment) {
			actionPerformedBtnDepartment(e);
		}
		if (e.getSource() == btnTitle) {
			actionPerformedBtnTitle(e);
		}
	}

	protected void actionPerformedBtnTitle(ActionEvent e) {  				//직책관리 버튼

		titleFrame.setVisible(true);
	}

	protected void actionPerformedBtnDepartment(ActionEvent e) {			//부서관리 버튼

		deptFrame.setVisible(true);
	}

	protected void actionPerformedBtnEmployee(ActionEvent e) {				//사원관리 버튼
		//
		empFrame.setVisible(true);
	}
}
