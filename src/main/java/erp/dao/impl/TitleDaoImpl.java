///TitleDao 인터페이스를 구현하는곳. 테스트는TitleDaoTest에서
package erp.dao.impl; //계속쓰기때문에 싱클턴패턴으로 구현한다.

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import erp.dao.TitleDao;
import erp.dbcon.JdbcConn;
import erp.dto.Title;
import erp.ui.exception.SqlConstraintException;

public class TitleDaoImpl implements TitleDao {
	private static final TitleDaoImpl instance = new TitleDaoImpl();
	

	public static TitleDaoImpl getInstance() {
		return instance;
		
	}

	@Override
	public List<Title> selectTitleByAll() {
	// test파일에 있는 select tno, tname from title; 하면 출력되는 테이블 결과랑 똑같이 나오게 하기		
		String sql = "select tno, tname from title";
		try(Connection con = JdbcConn.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);  //java.sql에 있는 PreparedStatement로 임포트 후 트라이캐치!!
				ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					List<Title> list = new ArrayList<>();
					do {
						list.add(getTitle(rs));  //반복문 다 끝나면 리스트에 다 담겨져있게 된다
						
					}while(rs.next());
					System.out.println(list.size());
					return list;  //존재한다면 리턴리스트
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
						
						
		return null;
	}

	private Title getTitle(ResultSet rs) throws SQLException {//throws 해주기. 위에 캐치에서  처리하게!!
		int tNo = rs.getInt("tno");
		String tName = rs.getNString("tname");
		return new Title (tNo,tName);
	}
///////
	@Override
	public Title selectTitleByNo(Title title) {
		String sql = "select tno, tname from title where tno = ?";  // ?는 1을 의미한다 . 2로 바꿔적으면 에러뜬다. 쿼리스트링의 위치를 의미하기때문. 1에 1
		try(Connection con = JdbcConn.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
				pstmt.setInt(1, title.gettNo()); //1에 이걸 집어넣으라고 하는거?
//				System.out.println(pstmt);
				try(ResultSet rs = pstmt.executeQuery()){
					if(rs.next()) {  //요소가 존재하면 그다음으로 이동해서 getTitle
						return getTitle(rs);
					}
				}
				
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return null;
	}
////
	@Override
	public int insertTitle(Title title) {
		String sql = "insert into title values(?,?)";
		try (Connection con = JdbcConn.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, title.gettNo());
			pstmt.setString(2, title.gettName());
			return pstmt.executeUpdate();			
		} catch (SQLException e) {		
			throw new SqlConstraintException(e);
		}
						
	}

	@Override
	public int updateTitle(Title title) {	
			String sql = "update title set tname=? where tno =?";
			try (Connection con = JdbcConn.getConnection();
						PreparedStatement pstmt = con.prepareStatement(sql)){
				pstmt.setString(1, title.gettName());
				pstmt.setInt(2, title.gettNo());
				return pstmt.executeUpdate();			
			} catch (SQLException e) {
			
				e.printStackTrace();
		return 0;
			}
	}
///////////
	@Override
	public int deleteTitle(int titleNo) {
		String sql = "delete from title where tno = ?";
		try (Connection con = JdbcConn.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, titleNo);
			return pstmt.executeUpdate();			
		} catch (SQLException e) {
			throw new SqlConstraintException(e);
//			System.out.println("errorCode "+ e.getErrorCode());
//		
//			e.printStackTrace();
//		return 0;
	}

	}
	}
