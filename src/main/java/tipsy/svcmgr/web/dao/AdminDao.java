package tipsy.svcmgr.web.dao;

import org.apache.ibatis.annotations.Param;


public interface AdminDao {

	public AdminDto selectOne(@Param("adminId")Integer adminId);
	
	public AdminDto selectByName(@Param("name")String name);

}
