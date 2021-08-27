package gwang.allalcohol.web.service;

import gwang.allalcohol.web.dao.CountryDto;
import gwang.allalcohol.web.dao.PartTimeWorkerDto;
import gwang.common.basic.BasicListResponse;

public interface MemberMgmtService {

	public BasicListResponse joinPartTimeWorker(int tid, PartTimeWorkerDto worker);
	
}