package tipsy.svcmgr.web.dao;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;

@Resource(name="AdminDao")
public interface AdminDao {

	public AdminDto selectOne(@Param("adminId")Integer adminId);
	
	public AdminDto selectByName(@Param("name")String name);

}
