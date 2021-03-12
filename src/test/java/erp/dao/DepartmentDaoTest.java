package erp.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import erp.dao.impl.DepartmentDaoImpl;
import erp.dto.Department;
import erp.dto.Employee;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentDaoTest {
	private static DepartmentDao dao= DepartmentDaoImpl.getInstance();
	@After
	public void tearDown() throws Exception {
		System.out.println();
	}
	
	@Test
	public void test04SelectDepartmentByAll() {
		System.out.printf("%s()%n","testSelectDepartmentByAll");
		List<Department> DepartmentList = dao.selectDepartmentByAll();
		Assert.assertNotNull(DepartmentList);
//		DepartmentList.stream().forEach(System.out::println); 이렇게 해도 된다.
		for(Department t : DepartmentList) { 
			System.out.println(t);
		}
	}

	@Test
	public void test05SelectDepartmentByNo() {
		System.out.printf("%s()%n","test05SelectDepartmentByNo");
		Department department = new Department(4);		
		Department searchdepartment = dao.selectDepartmentByNo(department);
		
		Assert.assertNotNull(searchdepartment);
//		titleList.stream().forEach(System.out::println); 이렇게 해도 된다.
		System.out.println(searchdepartment);
	}

	@Test
	public void test01IntsertDepartment() {
		System.out.printf("%s()%n","testInserteDepartment"); //제목찍어주기
		Department newDepartment= new Department(5,"인사",5);   //null 지우기
		int res = dao.intsertDepartment(newDepartment);
		Assert.assertEquals(1,res);
		System.out.println(dao.selectDepartmentByNo(newDepartment));
		
	}

	@Test
	public void test02UpdateDepartment() {
		System.out.printf("%s()%n","testUpdateDepartment"); //제목찍어주기
		Department updatedepartment = new Department(5,"열정",6);   //null 지우기
		int res =dao.updateDepartment(updatedepartment);
		Assert.assertEquals(1, res);
		System.out.println(dao.selectDepartmentByNo(updatedepartment));
	}

	@Test
	public void test03DeleteDepartment() {
		System.out.printf("%s()%n","testDeleteDepartment");  //제목찍어주기
		int res = dao.deleteDepartment(5);
		Assert.assertEquals(1, res);
		dao.selectDepartmentByAll();
	}
	@Test
	public void test06selectEmplist() {
		System.out.printf("%s()%n","test06selectEmplist");
		Department department = new Department(2);		
		List<Employee> searchdepartment = dao.selectEmplist(department);
		
		Assert.assertNotNull(searchdepartment);
//		titleList.stream().forEach(System.out::println); 이렇게 해도 된다.
		System.out.println(searchdepartment);
	}


}
