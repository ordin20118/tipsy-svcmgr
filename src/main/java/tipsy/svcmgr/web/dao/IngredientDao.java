package tipsy.svcmgr.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IngredientDao {
	
	public int insert(IngredientDto ingredient);
	
	public int updateByCode(IngredientDto ingredient);
	
	public IngredientDto selectOne(@Param("ingdId")Integer ingdId);

	public List<IngredientDto> selectListByIds(@Param("ingdIds")List<Integer> ingdIds);
	
	public List<IngredientDto> searchName(@Param("keyword") String keyword);

}
