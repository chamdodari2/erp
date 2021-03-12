package erp.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import erp.dao.impl.TitleDaoImpl;
import erp.dto.Title;



	//순서 맞춰서 하기
	 @FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public class TitleDaoTest {
	//테스트해보기 + 테스트 수행하기전에 확인하기 ( @비포클래스에  넣어줘도된당)	
		private static TitleDao dao= TitleDaoImpl.getInstance();
		
	// JDBCUtilTest에서 복사해와서 붙이기
		@After
		public void tearDown() throws Exception {
			System.out.println();
		}
		
	// 
		@Test
		public void test04SelectTitleByAll() {
			System.out.printf("%s()%n","testSelectTitleByAll");
			List<Title> titleList = dao.selectTitleByAll();
			Assert.assertNotNull(titleList);
//			titleList.stream().forEach(System.out::println); 이렇게 해도 된다.
			for(Title t : titleList) {
				System.out.println(t);
			}
		}
	////
		@Test
		public void test05SelectTitleByNo() {
			System.out.printf("%s()%n","testSelectTitleByNo");  //제목찍어주기
			Title title = new Title(3);  //3을  적었기때문에 Title [tNo=3, tName=과장] 출력된다.
			Title searchtitle = dao.selectTitleByNo(title);
			Assert.assertNotNull(searchtitle);
			System.out.println(searchtitle);  //Title [tNo=3, tName=과장] 출력된다.
		}
	///
		@Test
		public void test01InsertTitle() {
			System.out.printf("%s()%n","testinsertTitl");  //제목찍어주기
			Title newTitle = new Title (6,"인턴");
			int res = dao.insertTitle(newTitle);
			Assert.assertEquals(1, res);
			System.out.println(dao.selectTitleByNo(newTitle));
		}

		@Test
		public void test02UpdateTitle() {
			System.out.printf("%s()%n","testupdateTitle");  //제목찍어주기
			Title updateTitle= new Title(6, "계약직");
			int res = dao.updateTitle(updateTitle);
			Assert.assertEquals(1, res);
			System.out.println(dao.selectTitleByNo(updateTitle));
		}

		

		@Test
		public void test03DeleteTitle() {
			System.out.printf("%s()%n","testDeleteTitle");  //제목찍어주기
			int res = dao.deleteTitle(6);
			Assert.assertEquals(1, res);
			dao.selectTitleByAll();
		}

	}
