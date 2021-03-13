package erp.ui.list;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import erp.dto.Title;
import erp.service.TitleService;

@SuppressWarnings("serial")
public class TitleTablePanel extends AbstractCustomTablePanel<Title> {//부모클래스를 Title 타입으로 상속받아서
	public TitleTablePanel() {  //기본생성자
	}
	//////////////츄가 1!!!!!!!  0312
	private TitleService service = new TitleService();   //객체생성. Titleservice 
	
	
	/////////////////////
	@Override
	protected void setAlignAndWidth() {
		// 컬럼내용 정렬
//		setTableCellAlign(SwingConstants.CENTER, 0, 1);
		// 컬럼별 너비 조정
		setTableCellWidth(100, 250);

		ConditionTableCellRenderer ctcr = new ConditionTableCellRenderer();
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setCellRenderer(ctcr);
		tcm.getColumn(1).setCellRenderer(ctcr);
		
	}

	@Override
	public Object[] toArray(Title t) {
		return new Object[] { t.gettNo(), t.gettName() };
	}

	@Override
	public String[] getColumnNames() {
		return new String[] { "직책번호", "직책명" };
	}

	private class ConditionTableCellRenderer extends JLabel implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value==null?"":value.toString());
			setOpaque(true);
			String tname =  table.getValueAt(row, 1).toString();
			if (tname.equals("사장")) {
				setBackground(Color.ORANGE);
			}else {
				setBackground(Color.WHITE);
			}
			if (isSelected) {
				setBackground(Color.PINK);
			}
			setHorizontalAlignment(SwingConstants.CENTER);
			return this;
		}
		
	}
 
	@Override ///////추가2
	public void initList() {
		list = service.showTitles();  //리스트에 타이틀 목록이 들어오게한다
		
	}
	public void setService(TitleService service) {
		this.service = service;
	}
	

	
}