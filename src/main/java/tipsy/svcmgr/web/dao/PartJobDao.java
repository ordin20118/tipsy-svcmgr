package tipsy.svcmgr.web.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;

import tipsy.svcmgr.web.controller.param.PagingParam;
import tipsy.svcmgr.web.vo.PartJobRewardVo;

@Resource(name="PartJobDao")
public interface PartJobDao {
	
	public Integer insert(PartJobDto job);
	
	public List<PartJobDto> selectList(PagingParam param);
	
	public List<PartJobDto> selectListByWorker(PartTimeWorkerDto worker);
	
	public PartJobDto selectPartJobReward(@Param("workerId")Integer workerId, @Param("type")Integer type);
	
	public Integer selectCount();	

}
