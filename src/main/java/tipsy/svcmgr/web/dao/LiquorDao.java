package tipsy.svcmgr.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tipsy.svcmgr.web.vo.LiquorContentInfoVo;


public interface LiquorDao {
	
	public int insert(LiquorDto liquor);
	
	public int updateByCode(LiquorDto liquor);
	
	public LiquorDto selectOne(@Param("liquorId")Integer liquorId);
	
	public List<LiquorDto> selectList(LiquorDto beer);
	
	public List<LiquorDto> searchName(@Param("keyword") String keyword);
	
	public LiquorContentInfoVo selLiquorView(@Param("liquorId")Integer liquorId);

}
