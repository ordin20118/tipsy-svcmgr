package tipsy.svcmgr.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PartTimeWorkerDao {
	
	public Integer insert(PartTimeWorkerDto worker);
	
	public PartTimeWorkerDto selectOne(@Param("workerId") Integer workerId);
	
	public PartTimeWorkerDto selectByNameAccount(PartTimeWorkerDto worker);
	
	public List<PartTimeWorkerDto> selectList();

}
