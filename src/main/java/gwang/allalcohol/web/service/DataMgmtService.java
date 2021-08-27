package gwang.allalcohol.web.service;

import gwang.allalcohol.web.controller.param.PagingParam;
import gwang.allalcohol.web.dao.BeerDto;
import gwang.allalcohol.web.dao.ImageDto;
import gwang.allalcohol.web.dao.PartJobDto;
import gwang.allalcohol.web.dao.PartTimeWorkerDto;
import gwang.allalcohol.web.vo.PartJobRewardVo;
import gwang.common.basic.BasicListResponse;

public interface DataMgmtService {

	public BasicListResponse insertBeerInfo(int tid, BeerDto beer, PartTimeWorkerDto worker, PartJobDto job) throws Exception;
	
	public BasicListResponse uploadImage(int tid, ImageDto imageParam) throws Exception;
	
	public BasicListResponse checkInPartTimeWorker(int tid, PartTimeWorkerDto worker);
	
	public BasicListResponse getPartJobList(int tid, PagingParam param);
	
	public BasicListResponse addCategory(int tid);
	
	public BasicListResponse getCategoryChild(int tid, String categId);
	
	public BasicListResponse searchBeer(int tid, String keyword);
	
	public BasicListResponse getPartJobList(int tid, PartTimeWorkerDto worker);
	
	public PartJobRewardVo getPartTimeWorkerReward(PartTimeWorkerDto worker);
	
}