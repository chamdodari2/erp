package erp.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import erp.dto.Department;
import erp.dto.Employee;
import erp.service.DepartmentService;
import erp.ui.content.DeptPanel;
import erp.ui.content.AbstractContentPanel;
import erp.ui.exception.InvalidChechException;
import erp.ui.exception.NotSelectedException;
import erp.ui.exception.SqlConstraintException;
import erp.ui.list.DepartmentTablePanel;

@SuppressWarnings("serial")
public class DepartmentManager extends JFrame implements ActionListener{

	private JPanel contentPane;
	private AbstractContentPanel<Department> pContent;  //////////7. 이렇게 수정
	private JPanel pBtns;
	private DepartmentTablePanel pList;
	private JButton btnAdd;
	private JButton btnClear;
	private DepartmentService service;

	public DepartmentManager() {
		service = new DepartmentService();  //서비스에 있는 메소드 쓸거얌
		initialize();
	}
	private void initialize() {
		setTitle("부서관리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		pContent = new DeptPanel();
		contentPane.add((DeptPanel)pContent);
//		( pContent).setLayout(new GridLayout(0, 2, 0, 0));///////////
		
		pBtns = new JPanel();
		contentPane.add(pBtns);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this); //신경론!! 추가버튼을 누르면 읽어온당
		pBtns.add(btnAdd);
		
		btnClear = new JButton("취소");
		pBtns.add(btnClear);
		
		pList = new DepartmentTablePanel();  //테이블에 표시할거(부서번호,부서명,위치)
		pList.setService(service);  //DepartmentTablePanel에 생성해놓은 메서드임. department에 있는거 가져오기
		pList.loadData();// 
		
		contentPane.add(pList);
		
		JPopupMenu popupMenu = createPopupMenu();
		pList.setPopupMenu(popupMenu);
	}
	private JPopupMenu createPopupMenu() {
		JPopupMenu popMenu = new JPopupMenu();

		JMenuItem updateItem = new JMenuItem("수정");
	
		updateItem.addActionListener(popupMenuListner );
		popMenu.add(updateItem);

		JMenuItem deleteItem = new JMenuItem("삭제");
		deleteItem.addActionListener(popupMenuListner);
		popMenu.add(deleteItem);

		JMenuItem empListByDepartmentItem = new JMenuItem("동일 부서 사원 보기");
		empListByDepartmentItem.addActionListener(popupMenuListner);
		popMenu.add(empListByDepartmentItem);

		return popMenu;
	}
	ActionListener popupMenuListner = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
try {
				
				
				if (e.getActionCommand().equals("삭제")) { 
					Department delDepartment = pList.getItem();
					service.removeDepartment(delDepartment);
					pList.loadData();
					JOptionPane.showMessageDialog(null, delDepartment + "삭제 되었습니다.");
					
				}
				if(e.getActionCommand().equals("수정")) { 
					Department updateDepartment = pList.getItem();
					pContent.setItem(updateDepartment);
					btnAdd.setText("수정");
				//	service.removeTitle(delTitle);
				//	pList.loadData();
				}
				if(e.getActionCommand().contentEquals("동일 부서 사원 보기")) { 
					/*
					 * 1. EmployeeDao -> selectEmployeeByTitle() 추가
					 * 2. EmployeeDaoImpl -> selectEmployeeByTitle() 구현
					 * 3. EmployeeDaoTest -> Test하기
					 * 4. TitleService -> EmployeeDaoImpl field 추가 및 메서드 추가
					 * 5. 아래 기능 추가
					 * 6. 예외찾아서 추가하기 (신규 직책 추가 시 NullPointException)
					 */
					
					Department department = pList.getItem();
					List<Employee> list = service.showEmployeeGroupByDepartment(department);
				System.out.println(list);
					if(list == null) {
						JOptionPane.showMessageDialog(null, "해당 사원이 없음", "동일 부서 사원",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					List<String> strList = list		
							.parallelStream()
							.map( s -> {return String.format("%s(%d)",s.getEmpName(),s.getEmpNo());})
							.collect(Collectors.toList());
					JOptionPane.showMessageDialog(null, strList," 동일 부서 사원",JOptionPane.INFORMATION_MESSAGE);
					
			
	//				JList<String> JList = new JList<String>(listData);
					
					
				//	service.removeTitle(delTitle);
				//	pList.loadData(); 
					
					}		
			}catch (NotSelectedException | SqlConstraintException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	};
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnAdd) {
				if(btnAdd.getText().equals("추가")) {
					actionPerformedBtnAdd(e);
				}else {
					actionPerformedBtnUpdate(e);
				}
				
			}
		}catch (InvalidChechException | SqlConstraintException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
//			pContent.clearTf();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	private void actionPerformedBtnUpdate(ActionEvent e) {
		Department department = pContent.getItem();
		service.modifyDepartment(department);
		pList.loadData();
		
	}
	private void actionPerformedBtnAdd(ActionEvent e) {
		Department department = pContent.getItem();
		service.addDepartment(department);
		pList.loadData();
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, department + " 추가했습니다.");
		
	}

}
