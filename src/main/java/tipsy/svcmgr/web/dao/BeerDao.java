package tipsy.svcmgr.web.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;

@Resource(name="BeerDao")
public interface BeerDao {
	
	public int insert(BeerDto beer);
	
	public int updateByCode(BeerDto beer);
	
	public BeerDto selectOne(@Param("beerId")Integer beerId);
	
	public List<BeerDto> selectList(BeerDto beer);
	
	public List<BeerDto> searchName(@Param("keyword") String keyword);

}
