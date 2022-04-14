package tipsy.svcmgr.web.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;

@Resource(name="CategoryDao")
public interface CategoryDao {
	
	public Integer insert(CategoryDto category);
	
	public Integer deleteAll();
	
	public CategoryDto selectMaxSeq(@Param("categId") String categId, @Param("level") Integer level);
	
	public CategoryDto selectOne(@Param("categId") String categId);
	
	public CategoryDto selectByRawId(@Param("rawCategId") Integer rawCategId);
	
	public List<CategoryDto> selectChild(@Param("categId") String categId, @Param("level") Integer level);
	
	public List<CategoryDto> selectList();
	
	public List<CategoryDto> selectAll();
	
	public List<RawCategDto> selectRawCateg();
	

}
