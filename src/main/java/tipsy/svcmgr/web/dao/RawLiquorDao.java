package tipsy.svcmgr.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

public interface RawLiquorDao {
	
	public int insert(RawLiquorDto liquor);
	
	public int updateByCode(RawLiquorDto liquor);
	
	public RawLiquorDto selectOne(@Param("liquorId")Integer liquorId);
	
	//public List<RawLiquorDto> selectList(RawLiquorDto liquor);
	
	public List<RawLiquorDto> selectListByIds(@Param("liquorIds")List<Integer> liquorIds);
	
	public List<RawLiquorDto> searchName(@Param("keyword") String keyword);

}
