package erp.dbcon;  // db.properties 파일 내용중에 =를 기준으로 왼쪽에 있는게 key 오른쪽은 벨류임
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class JdbcConn{
	
	//반환타입 Connection의 getConnection 메소드 선언 
	public static Connection getConnection() { // 경로를 정해주면 파일 읽어오기 가능!!
		//String 변수 선언하여 읽을 파일명 넣기
		String propertiesPath = "db.properties";// resouces폴더의 db.properties를 읽어오려면  이방법 써야한당!!!
		
		// Connection 타입의 변수 con 선언 후 null로 초기화
		Connection con = null;
		
			//InputStream타입을  이용해서   선언한 변수 in에 ,  ClassLoader.getSystemResourceAsStream()메소드 사용, 입력변수로 propertiespath 넣기.
		try (InputStream in = ClassLoader.getSystemResourceAsStream(propertiesPath)) { // resouces폴더의 db.properties를 읽어오려면  이 이름  동일해야한다.이름이 조금이라도 틀리면 못읽어온다.
			
			//properties타입을 이용하여 선언한  변수 prop을 객체화한다.
			Properties prop = new Properties();
			
			// properties클래스에 있는 load 메소드를 이용하여 변수 in(에 담겨있는 propertiesPath에 담겨있는 "db.properties")을 읽는다.
			prop.load(in); // db.properties 파일을 읽어와서 여기에 담을거다
			
			//변수 con 에 DriverManager. getConnection 메소드 호출( prop.getProperty)
			con = DriverManager.getConnection(prop.getProperty("url"), prop);
			
			
			
			/*
			System.out.println("실습1.prop > " + prop); // 읽어온걸 찍어보기
			
			//방법2
			for(Entry<Object, Object> e :prop.entrySet()) {     //key =values 에 각각 <Object> <Object>로 ?? //entry를 쌍으로 출력하는것
				System.out.printf("실습2.%s -> %s%n", e.getKey(),e.getValue());
			}   
			//방법3
			for(Object key : prop.keySet()) {  //key만 받는거
				System.out.print("실습3"+key + " - >");
				System.out.println(prop.get(key));  // 키만 받는건디 왜 벨류가 출력되나?? 해시테이블의 특징임 --> 키값을 가지고 메모리 jvm에 올라와있는 주소를 함수를 통해서 주소값을 전해주면  ㅇㅇ??? 암튼 주소를 계산해서 바로 저장된 값을 가져올수 있는거임
			}*/
		} catch (IOException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

}
