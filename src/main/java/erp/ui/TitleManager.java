package erp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import erp.dto.Employee;
import erp.dto.Title;
import erp.service.TitleService;
import erp.ui.content.AbstractContentPanel;
import erp.ui.content.TitlePanel;
import erp.ui.exception.InvalidChechException;
import erp.ui.exception.NotSelectedException;
import erp.ui.exception.SqlConstraintException;
import erp.ui.list.AbstractCustomTablePanel;
import erp.ui.list.TitleTablePanel;

@SuppressWarnings("serial")
public class TitleManager extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnAdd;
	
	private AbstractContentPanel<Title> pContent;  /////AbstractContentPanel<Title> 로 해줭햐ㅏㅁ
	private AbstractCustomTablePanel<Title> pList;
	
	private TitleService service;
	
	public TitleManager() {
		setService();  //서비스 연결기능 만듬
		initialize();    //	service = new TitleService();  // 위에꺼 한줄로로 변경
		tableLoadData();    //  loadDate기능       -  셋서비스,로드데이터 두줄을 하나의 메소드로 묶음
		
		
	}

	public void tableLoadData() {
		((TitleTablePanel) pList).setService(service);   //TitleTablePanel로 감싸주었음 안감싸주면 안먹는다
		pList.loadData();
	}
	
	private void setService() {
		service = new TitleService();
		
	}

	private void initialize() {
		setTitle("직책 관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		pContent = createContentPanel();  //////블럭지정해서  메소드로 뺀거임 원래는 new 붙어잇었음
		contentPane.add(pContent);
		
		JPanel pBtns = new JPanel();
		contentPane.add(pBtns);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);
		
		JButton btnClear = new JButton("취소");
		pBtns.add(btnClear);
		
		pList = createTablePanel();   //블럭지정해서  메소드로 뺀거임 원래는 new 붙어잇었음
		
		contentPane.add(pList);
		
		JPopupMenu popupMenu = createPopupMenu();
		pList.setPopupMenu(popupMenu);
	}

	public TitleTablePanel createTablePanel() {
		return new TitleTablePanel();
	}

	public TitlePanel createContentPanel() {
		return new TitlePanel();
	}

	private JPopupMenu createPopupMenu() {
		JPopupMenu popMenu = new JPopupMenu();

		JMenuItem updateItem = new JMenuItem("수정");
		updateItem.addActionListener(popupMenuListner);
		popMenu.add(updateItem);

		JMenuItem deleteItem = new JMenuItem("삭제");
		deleteItem.addActionListener(popupMenuListner);
		popMenu.add(deleteItem);
		
		JMenuItem empListByTitleItem = new JMenuItem("동일 직책 사원 보기");
		empListByTitleItem.addActionListener(popupMenuListner);
		popMenu.add(empListByTitleItem);
		
		return popMenu;
	}
	
	ActionListener popupMenuListner = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (e.getActionCommand().equals("삭제")) {
					Title delTitle = pList.getItem();
					service.removeTitle(delTitle);
					pList.loadData();
					JOptionPane.showMessageDialog(null, delTitle + "삭제 되었습니다.");
				}
				
				if (e.getActionCommand().equals("수정")) {
					Title updateTitle = pList.getItem();
					pContent.setItem(updateTitle);
					btnAdd.setText("수정");
				}
				
				if (e.getActionCommand().contentEquals("동일 직책 사원 보기")) {
					/*
					 * 1. EmployeeDao -> selectEmployeeByTitle() 추가
					 * 2. EmployeeDaoImpl -> selectEmployeeByTitle() 구현
					 * 3. EmployeeDaoTest -> Test하기
					 * 4. TitleService -> EmployeeDaoImpl field 추가 및 메서드 추가
					 * 5. 아래 기능 추가
					 * 6. 예외찾아서 추가하기 (신규 직책 추가 시 NullPointException)
					 */
					Title title = pList.getItem();
					List<Employee> list = service.showEmployeeGroupByTitle(title);
					
					if (list == null) {
						JOptionPane.showMessageDialog(null, "해당 사원이 없음", "동일 직책 사원", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					List<String> strList = list
							.parallelStream()
							.map( s->{ return String.format("%s(%d)", s.getEmpName(), s.getEmpNo()); })
							.collect(Collectors.toList());
					
					JOptionPane.showMessageDialog(null, strList, "동일 직책 사원", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}catch (NotSelectedException | SqlConstraintException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	};
	
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnAdd) {
				if (e.getActionCommand().contentEquals("추가")) {
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
		//pContent에서 수정된 title 가져오기
		//update 수행
		//pList 갱신
		//pContent clearTf()호출하여 초기화
		//btnAdd 텍스트 변경 수정->추가
		
		Title updateTitle = pContent.getItem();
		service.modifyTitle(updateTitle);
		pList.loadData();
		pContent.clearTf();
		btnAdd.setText("추가");
		JOptionPane.showMessageDialog(null, updateTitle.gettName() + "정보가 수정되었습니다.");
	}

	protected void actionPerformedBtnAdd(ActionEvent e) {
		Title title = pContent.getItem();
		service.addTitle(title);
		pList.loadData();
		pContent.clearTf();
		JOptionPane.showMessageDialog(null, title + " 추가했습니다.");
	}
}