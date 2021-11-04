package tipsy.svcmgr.web.service;

import tipsy.common.basic.BasicListResponse;
import tipsy.svcmgr.web.dao.CountryDto;
import tipsy.svcmgr.web.dao.PartTimeWorkerDto;

public interface MemberMgmtService {

	public BasicListResponse joinPartTimeWorker(int tid, PartTimeWorkerDto worker);
	
}