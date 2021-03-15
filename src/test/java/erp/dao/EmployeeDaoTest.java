package erp.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import erp.dao.impl.EmployeeDaoImpl;
import erp.dto.Department;
import erp.dto.Employee;
import erp.dto.Title;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
	private static EmployeeDao dao = EmployeeDaoImpl.getInstance();

	@After
	public void tearDown() throws Exception {
		System.out.println();
	}
	
	
	@Test
	public void test01SelectEmployeeByAll() {
		System.out.printf("%s()%n","testSelectEmployeeByAll");
		List<Employee> EmployeeList = dao.selectEmployeeByAll();
		Assert.assertNotNull(EmployeeList);
		
		for(Employee e : EmployeeList) {
			System.out.println(e);
		}
		 
		
	}

	@Test
	public void test02SelectEmployeeByNo() {
		System.out.printf("%s()%n","testSelectEmployeeByNo");
		Employee employee = new Employee(2106);
		
		Employee searchemployee = dao.selectEmployeeByNo(employee);
		Assert.assertNotNull(searchemployee);
		System.out.println(dao.selectEmployeeByNo(employee));
//		System.out.println(employee);
	}

	@Test
	public void test03InsertEmployee() {
		System.out.printf("%s()%n","testInsertEmployee");
		Employee newEmployee = new Employee(1004,"천사",new Title(5),new Employee(4377),2000000, new Department(4));
		int res = dao.insertEmployee(newEmployee);
		Assert.assertEquals(1, res);
		System.out.println(dao.selectEmployeeByNo(newEmployee));
	}

	@Test
	public void test04UpdateEmployee() {
		System.out.printf("%s()%n","testUpdateEmployee");
		Employee updateemployee = new Employee (1004,"천사2", new Title(4), new Employee(1003), 2500000, new Department(1));
		int res = dao.updateEmployee(updateemployee);
		Assert.assertEquals(1, res);
		System.out.println(dao.selectEmployeeByNo(updateemployee));
	}

	@Test
	public void test05DeleteEmployee() {
		System.out.printf("%s()%n","testDeleteEmployee");
		Employee newEmployee = new Employee(1004);
		int res = dao.deleteEmployee(newEmployee);
		Assert.assertEquals(1, res);
		dao.selectEmployeeByAll().stream().forEach(System.out::println);
	}
	@Test
	public void test06SelectEmployeeByTitle() {
		System.out.printf("%s()%n","test06SelectEmployeeByTitle");
		List<Employee> res  = dao.SelectEmployeeByTitle(new Title(3));
		Assert.assertEquals(1, res);//
		dao.selectEmployeeByAll().stream().forEach(System.out::println);
	}
	@Test
	public void test07SelectEmployeeByDepartment() {
		System.out.printf("%s()%n","test07SelectEmployeeByDepartment");
		List<Employee> res  = dao.SelectEmployeeByDepartment(new Department(3));
		Assert.assertEquals(1, res);//
		dao.selectEmployeeByAll().stream().forEach(System.out::println);
	}
	
}
