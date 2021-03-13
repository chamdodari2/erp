package erp.dbcon; // db.properties 파일 내용중에 =를 기준으로 왼쪽에 있는게 key 오른쪽은 벨류임
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class JdbcConn {

	public static Connection getConnection() {
		String propertiesPath = "db.properties";//
		Connection con = null;

		try (InputStream in = ClassLoader.getSystemResourceAsStream(propertiesPath)) {

			Properties prop = new Properties();
			prop.load(in); // db.properties 파일을 읽어와서 여기에 담을거다
			con = DriverManager.getConnection(prop.getProperty("url"), prop);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

}
