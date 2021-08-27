package gwang.allalcohol.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gwang.allalcohol.web.dao.CountryDao;
import gwang.allalcohol.web.dao.CountryDto;
import gwang.common.basic.BasicListResponse;
import gwang.common.configuration.LoggerName;

@Service
public class CountryServiceImpl implements CountryService {

	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	private CountryDao countryDao;

	@Override
	public BasicListResponse getCountryList(int tid) {
		
		BasicListResponse res = new BasicListResponse();
		
		try {
			
			List<CountryDto> list = countryDao.selectList();
			
			res.setList(list);
			res.setState(BasicListResponse.STATE_SUCCESS);
			
		} catch(Exception e) {
			log.error("[getCountryList]Error:"+e.getMessage(), e);
		}
		
		return res;
	}
	
}