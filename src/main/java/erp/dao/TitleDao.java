package erp.dao;

import java.util.List;

import erp.dto.Title;

/**
 * C(insert) 나중엔 필요한거만 만들어놓고 나중에 추가하면 된당 R(select , select where) U(update)
 * D(delete)
 */
public interface TitleDao { // 정의
	List<Title> selectTitleByAll();
	Title selectTitleByNo(Title title);
	
	int insertTitle(Title title);
	int updateTitle(Title title);
	int deleteTitle(int titleNo);

}
//구현클래스
