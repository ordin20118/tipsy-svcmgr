package gwang.allalcohol.web.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;

@Resource(name="PartTimeWorkerDao")
public interface PartTimeWorkerDao {
	
	public Integer insert(PartTimeWorkerDto worker);
	
	public PartTimeWorkerDto selectOne(@Param("workerId") Integer workerId);
	
	public PartTimeWorkerDto selectByNameAccount(PartTimeWorkerDto worker);
	
	public List<PartTimeWorkerDto> selectList();

}
