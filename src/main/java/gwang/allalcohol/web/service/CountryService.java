package gwang.allalcohol.web.service;

import gwang.allalcohol.web.dao.CountryDto;
import gwang.common.basic.BasicListResponse;

public interface CountryService {

	public BasicListResponse getCountryList(int tid);
	
}