package erp.ui.content;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import erp.dto.Title;
import erp.ui.exception.InvalidChechException;

@SuppressWarnings("serial")
public class TitlePanel extends AbstractContentPanel<Title> { // 6
	private JTextField tfNo;
	private JTextField tfName;

	public TitlePanel() {

		initialize();
	}

	public void initialize() {
		setBorder(new TitledBorder(null, "직책정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblNo = new JLabel("직책번호");
		lblNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNo);

		tfNo = new JTextField();
		tfNo.setColumns(10);
		add(tfNo);

		JLabel lblName = new JLabel("직책명");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblName);

		tfName = new JTextField();
		tfName.setColumns(10);
		add(tfName);

		
		
		
	}


	@Override
	public void clearTf() {
		tfNo.setText("");
		tfName.setText("");

	}

	@Override
	public void setItem(Title item) {
		tfNo.setText(item.gettNo() + "");
		tfName.setText(item.gettName() + "");
	}

	@Override
	public Title getItem() {
		validCheck();
		////////
		int titleNo = Integer.parseInt(tfNo.getText().trim());
		String titleName = tfName.getText().trim();
		return new Title(titleNo, titleName);
	}

	@Override

	public void validCheck() {
		// 두개의 값이 입력되어야만 가능하게 ㅎ가ㅣ 공백과 같다면
		if (tfNo.getText().contentEquals("") || tfName.getText().equals("")) {
			throw new InvalidChechException();
		}

	}
}
