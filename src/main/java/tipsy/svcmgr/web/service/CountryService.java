package tipsy.svcmgr.web.service;

import tipsy.common.basic.BasicListResponse;
import tipsy.svcmgr.web.dao.CountryDto;

public interface CountryService {

	public BasicListResponse getCountryList(int tid);
	
}