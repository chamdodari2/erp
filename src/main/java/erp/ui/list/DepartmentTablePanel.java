package erp.ui.list;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import erp.dto.Department;
import erp.service.DepartmentService;
import erp.service.TitleService;

	@SuppressWarnings("serial")
public class DepartmentTablePanel extends AbstractCustomTablePanel<Department> {
		public DepartmentTablePanel() {
		}
		
		private DepartmentService service = new DepartmentService();
///////

		@Override
		public void initList() {
			list = service.showDepartment();  //리스트에 타이틀 목록이 들어오게한다
		}

		public void setService(DepartmentService service) {
			this.service = service;
		}
		
		@Override
		protected void setAlignAndWidth() {
			// 컬럼내용 정렬
		setTableCellAlign(SwingConstants.CENTER, 0, 1,2);
			// 컬럼별 너비 조정
			setTableCellWidth(100, 250);

			
		}

		@Override
		public Object[] toArray(Department t) {
			return new Object[] { t.getDeptNo(),t.getDeptName(),t.getFloor() };
			
		}

		@Override
		public String[] getColumnNames() {
			return new String[] { "부서번호", "부서명","위치" };
		}
	}
