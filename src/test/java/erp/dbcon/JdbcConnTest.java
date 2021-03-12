package erp.dbcon;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

public class JdbcConnTest {


	@Test
	public void testGetConnection() {//3
		System.out.printf("%s()%n","testGetConnection");
		Connection con = JdbcConn.getConnection();
		System.out.println("con >" + con);
		Assert.assertNull(con);
	}

}
