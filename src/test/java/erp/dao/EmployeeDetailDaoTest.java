package erp.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import erp.dao.impl.EmployeeDetailDaoImpl;
import erp.dto.Employee;
import erp.dto.EmployeeDetail;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDetailDaoTest {
	private EmployeeDetailDao dao = EmployeeDetailDaoImpl.getInstance();

//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}

	@After
	public void tearDown() throws Exception {
		System.out.println();
	}

	@Test
	public void test02SelectEmployeeDetailByNo() {
		System.out.printf("%s()%n", "test02SelectEmployeeDetailByNo");	
		EmployeeDetail employeeDetail = dao.selectEmployeeDetailByNo(new Employee(1003));
		Assert.assertNotNull(employeeDetail);
		
		System.out.println(employeeDetail);

	}

	@Test
	public void test01InsertEmployeeDetail() {
		System.out.printf("%s()%n", "test01InsertEmployeeDetail");
		EmployeeDetail empDetail = new EmployeeDetail(1003, true, new Date(), "1234", getImage("노이미지.jpg"));
		int res = dao.insertEmployeeDetail(empDetail);

		Assert.assertEquals(1, res);
	}

	private byte[] getImage(String imgName) {
		byte[] pic = null;
		// 이미지를 파일로 생성해서
		File file = new File(System.getProperty("user.dir") + File.separator + File.separator + "images", imgName);
		try (InputStream is = new FileInputStream(file)) {
			pic = new byte[is.available()]; // file로부터 읽은 이미지의 바이트길이로 배열생성--이미지 전체의 길이만큼이 is.ava
			is.read(pic);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pic;
	}

	@Test
	public void test03UpdateEmployeeDetail() {
		System.out.printf("%s()%n", "test03UpdateEmployeeDetail");
		
		Calendar cal = GregorianCalendar.getInstance();
		cal.getTime();
		
		EmployeeDetail employeeDetail = new EmployeeDetail(1003, false, cal.getTime(), "1234", getImage("노이미지.jpg"));
		int res = dao.updateEmployeeDetail(employeeDetail);
		
		Assert.assertEquals(1, res);
		
		System.out.println(dao.selectEmployeeDetailByNo(new Employee(1003)));
	
	}

	@Test
	public void test04DeleteEmployeeDetail() {
		System.out.printf("%s()%n","test04DeleteEmployeeDetail");
		Employee employee = new Employee(1003);
		int res = dao.deleteEmployeeDetail(employee);
		
		Assert.assertEquals(1, res);
		EmployeeDetail employeeDetail = dao.selectEmployeeDetailByNo(new Employee(1003));
		Assert.assertNull(employeeDetail);
	
	}

}
