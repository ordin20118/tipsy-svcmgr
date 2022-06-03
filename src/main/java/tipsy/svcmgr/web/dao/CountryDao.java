package tipsy.svcmgr.web.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;


public interface CountryDao {
	
	public List<CountryDto> selectList();

}
