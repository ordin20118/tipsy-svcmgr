package tipsy.svcmgr.web.search;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.controller.param.WebSearchParam;
import tipsy.svcmgr.web.dao.CountryDao;
import tipsy.svcmgr.web.dao.RawCategDao;
import tipsy.svcmgr.web.dao.RawLiquorDao;
import tipsy.svcmgr.web.dao.RawLiquorDto;

@Service
public class SearchService {

	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	private RawLiquorDao rawLiquorDao;
	
	@Autowired
	private RawCategDao rawCategDao;
	
	@Autowired
	private CountryDao countryDao;
	
	@Data
	public class SearchResultVo {
		
		public static final String LIQUOR           = "liquor";
		
		private List<RawLiquorDto>   liquorList;
	
		private List<String> explains = new ArrayList<>();		
		public void addExplain(String explain) {
			explains.add(explain);
		}		 
		
		SearchResult searchRes;
	}
	

	public SearchResultVo searchRawLiquor(int tid, WebSearchParam sParam) throws Exception {
				
		SearchResultVo res = new SearchResultVo();
		
		// 검색어 필터
		// 1. 특수문자 제거
		sParam.setOrgKeyword(sParam.getKeyword());
		sParam.setKeyword(SearchParam.keywordFiter(sParam.getKeyword()));		
		
		// 2. 공백 확인		
		if(sParam.getKeyword() == null || sParam.getKeyword().length() == 0) {
			res.setLiquorList(new ArrayList<>());
			return res;
		} else {
			LiquorSearcher liquorSearcher = new LiquorSearcher(rawCategDao);
			
			SearchResult searchRes = liquorSearcher.searchRawLiquor(tid, sParam);
			res.setSearchRes(searchRes);
			
			// 검색된 liquor id list를 가지고 DB 조회
			List<RawLiquorDto> rawLiquorList = new ArrayList<>();
			if(searchRes.getHitIds().size() > 0) {				
				rawLiquorList = rawLiquorDao.selectListByIds(searchRes.getHitIds());		
			}
			
			for(int i=0; i<rawLiquorList.size(); i++) {
				System.out.println("["+rawLiquorList.get(i).getNameKr()+"/"+rawLiquorList.get(i).getNameEn()+"]");
			}
						
			res.setLiquorList(rawLiquorList);			
			return res;
		}		
		
	}
	
}
