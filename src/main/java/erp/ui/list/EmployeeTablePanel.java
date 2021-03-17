package erp.ui.list;

import javax.swing.SwingConstants;

import erp.dto.Employee;
import erp.dto.Title;
import erp.service.EmployeeService;
import erp.ui.exception.NotSelectedException;

@SuppressWarnings("serial")
public class EmployeeTablePanel extends AbstractCustomTablePanel<Employee> {
	private EmployeeService  service;/////
	
	
	
	@Override
	public void initList() {
		list = service.showEmployee();  //리스트에 타이틀 목록이 들어오게한다
	}

	@Override
	protected void setAlignAndWidth() {
		// 컬럼내용 정렬
		setTableCellAlign(SwingConstants.CENTER, 0, 1,2,3,5);
		setTableCellAlign(SwingConstants.RIGHT, 4);
		// 컬럼별 너비 조정
		setTableCellWidth(100, 250,100,100,100,100);

		//ConditionTableCellRenderer ctcr = new ConditionTableCellRenderer();
		//TableColumnModel tcm = table.getColumnModel();
		//tcm.getColumn(0).setCellRenderer(ctcr);
		//tcm.getColumn(1).setCellRenderer(ctcr);
		
	}

	@Override
	public Object[] toArray(Employee t) {
		System.out.println(t.getDept().getDeptName());
		return new  Object[] { 
				t.getEmpNo(),
				t.getEmpName(),
				String.format("%s(%d)",t.getTitle().gettName(),t.getTitle().gettNo()),//////////// t. 한개만 들어가있어서 오류떴어음
				t.getManager()==null?"":String.format("%s(%d)",t.getManager().getEmpName(),t.getManager().getEmpNo()),
				t.getSalary(),
				String.format("%s(%d)",t.getDept().getDeptName(),t.getDept().getDeptNo()) 
				
		};
	}

	@Override
	public String[] getColumnNames() {

		return new String[] { "사원번호", "사원명","직책","직속상사","급여","부서" };
	}


	public void setService(EmployeeService service) {
		this.service = service;
	}

	@Override
	public Employee getItem() {
		int row = table.getSelectedRow();
		int empNo = (int)table.getValueAt(row, 0); //테이블의 제일 앞에있는거 가져오기	
		
		if(row == -1 ) {
			throw new NotSelectedException();
		}
		
		return list.get(list.indexOf(new Employee(empNo))); //이거에 일치하는 인덱스번호를 넘겨준다
	}


}
