package tipsy.svcmgr.web.service;

import tipsy.common.basic.BasicListResponse;
import tipsy.svcmgr.web.controller.param.PagingParam;
import tipsy.svcmgr.web.dao.BeerDto;
import tipsy.svcmgr.web.dao.ImageDto;
import tipsy.svcmgr.web.dao.LiquorDto;
import tipsy.svcmgr.web.dao.PartJobDto;
import tipsy.svcmgr.web.dao.PartTimeWorkerDto;
import tipsy.svcmgr.web.vo.PartJobRewardVo;

public interface DataMgmtService {

	public BasicListResponse insertBeerInfo(int tid, BeerDto beer, PartTimeWorkerDto worker, PartJobDto job) throws Exception;
	
	public BasicListResponse insertSpiritsInfo(int tid, LiquorDto liquor) throws Exception;
	
	public BasicListResponse uploadImage(int tid, ImageDto imageParam) throws Exception;
	
	public BasicListResponse checkInPartTimeWorker(int tid, PartTimeWorkerDto worker);
	
	public BasicListResponse getPartJobList(int tid, PagingParam param);
	
	public BasicListResponse addCategory(int tid);
	
	public BasicListResponse getCategoryChild(int tid, String categId);
	
	public BasicListResponse searchBeer(int tid, String keyword);
	
	public BasicListResponse getPartJobList(int tid, PartTimeWorkerDto worker);
	
	public PartJobRewardVo getPartTimeWorkerReward(PartTimeWorkerDto worker);
	
}