package erp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import erp.dto.Employee;
import erp.dto.EmployeeDetail;
import erp.service.EmployeeDetailService;
import erp.ui.content.EmployeeDetailPanel;

@SuppressWarnings("serial")
public class EmployeeDetailUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel pBtns;
	private EmployeeDetailPanel pItem;
	private JButton btnAdd;
	private EmployeeDetailService service;
	private JButton btnCancel;
	private JButton btnDel;
	private JButton btnUpdate;
	private boolean inBtns;

	public EmployeeDetailUI(boolean isBtns, EmployeeDetailService service) {
		this.service = service;
		initialize(isBtns);
	}

	private void initialize(boolean isBtns) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		pItem = new EmployeeDetailPanel();
		contentPane.add(pItem, BorderLayout.CENTER);

	//	if (isBtns) { // isBtns가 true라면 보여지는것이다
			pBtns = new JPanel();//////////////
			contentPane.add(pBtns, BorderLayout.SOUTH);

			btnAdd = new JButton();  //수정글자 지움
			btnAdd.addActionListener(this);
			pBtns.add(btnAdd);

			btnCancel = new JButton();   //삭제버튼? 지움
			btnCancel.addActionListener(this);
			pBtns.add(btnCancel);
			
			
		pBtns = new JPanel();////////////////
		if(inBtns) {/////////////////////////여기뭐 빠트렸는디
			btnAdd.setText("추가");
			btnCancel.setText("취소");
		}
		////////////////////////////////////////
		if(inBtns) {/////////////////////////여기두 빠트렸는디
			btnAdd.setText("추가");
			btnCancel.setText("취소");
		//}
		} else {
		//	pBtns = new JPanel();////////////////
			contentPane.add(pBtns, BorderLayout.SOUTH);
			
			btnUpdate = new JButton("수정");
			btnUpdate.addActionListener(this);
			pBtns.add(btnUpdate);
			
			btnDel = new JButton("삭제");
			btnDel.addActionListener(this);
			pBtns.add(btnDel);
		}
	}

	public void setEmpNo(Employee empNo) { // 1003넣기
		pItem.setTfEmpno(empNo);
	}

	public void setDetailItem(EmployeeDetail empDetail) { // 하게되면 수정임 . 버튼글자 바꿔주기
	//	btnAdd.setText("수정");
		pItem.setItem(empDetail);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
////////////////////////////////////////////////////////////

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().contentEquals("삭제")) {
			actionPerformedBtnDel(e);
		}
		if (e.getActionCommand().contentEquals("취소")) {
			actionPerformedBtnCancel(e);
		}
		if (e.getActionCommand().contentEquals("추가")) { // 겟소스로 하지말고
			actionPerformedBtnAdd(e);
		}
		if (e.getActionCommand().contentEquals("수정")) { // 겟소스로 하지말고
			actionPerformedBtnUpdate(e); // 수정버튼으로 바뀌었다면, 업데이트 메소드 호출
		}
	}

	private void actionPerformedBtnUpdate(ActionEvent e) { // 업데이트기능(수정버튼일때)
		EmployeeDetail updateEmpDetail = pItem.getItem();
		service.modifyEmployeeDetail(updateEmpDetail);
		pItem.clearTf();
		JOptionPane.showMessageDialog(null, "수정완료");

	}

	protected void actionPerformedBtnAdd(ActionEvent e) { // 추가버튼 누르면 디비에 저장쓰
		// 입력된 empdetail 가져오기
		// service에 적용
		EmployeeDetail newEmpDetail = pItem.getItem();
		service.addEmployeeDetail(newEmpDetail);
		pItem.clearTf();
		JOptionPane.showMessageDialog(null, "추가완료");

	}

	protected void actionPerformedBtnCancel(ActionEvent e) { // 취소버튼
		pItem.clearTf();
		if (btnAdd.getText().contentEquals("수정")) { // 수정버튼일때 취소누르면 추가로 변경해줌
			btnAdd.setText("추가");
		}
	}

	protected void actionPerformedBtnDel(ActionEvent e) { // 삭제버튼
		EmployeeDetail empDetail = pItem.getItem(); // getItem에 대해서도 다 알아야하고 service도 다 알아야 가능하다
		service.removeEmployeeDetail(new Employee(empDetail.getEmpNo()));
		pItem.clearTf();
		JOptionPane.showMessageDialog(null, "삭제완료");
	}
}
