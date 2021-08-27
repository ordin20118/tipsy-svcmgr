package gwang.allalcohol.web.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;

@Resource(name="CountryDao")
public interface CountryDao {
	
	public List<CountryDto> selectList();

}
