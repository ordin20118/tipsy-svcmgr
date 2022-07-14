package tipsy.svcmgr.web.service;

import tipsy.common.basic.BasicListResponse;
import tipsy.svcmgr.web.dao.CountryDto;
import tipsy.svcmgr.web.dao.PartTimeWorkerDto;

public interface LiquorService {

	public BasicListResponse getLiquorInfo(int tid, PartTimeWorkerDto worker);
	
}