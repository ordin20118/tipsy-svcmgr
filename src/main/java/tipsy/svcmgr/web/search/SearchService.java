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
import tipsy.svcmgr.web.dao.EquipmentDto;
import tipsy.svcmgr.web.dao.IngredientDao;
import tipsy.svcmgr.web.dao.IngredientDto;
import tipsy.svcmgr.web.dao.RawCategDao;
import tipsy.svcmgr.web.dao.RawLiquorDao;
import tipsy.svcmgr.web.dao.RawLiquorDto;

@Service
public class SearchService {

	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	private RawLiquorDao rawLiquorDao;
	
	@Autowired
	private IngredientDao ingredientDao;
	
	@Autowired
	private RawCategDao rawCategDao;
	
	@Autowired
	private CountryDao countryDao;
	
	@Data
	public class SearchResultVo {
		
		public static final String LIQUOR           = "liquor";
		public static final String INGREDIENT       = "ingredient";
		public static final String EQUIPMENT        = "equipment";
		public static final String COCKTAIL        	= "coctail";
		
		private List<RawLiquorDto>   liquorList;
		private List<IngredientDto>  ingredeintList;
		private List<EquipmentDto>   equipmentList;
	
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
		if(sParam.getKeyword() != null) {
			sParam.setOrgKeyword(sParam.getKeyword());
			sParam.setKeyword(SearchParam.keywordFiter(sParam.getKeyword()));	
		}	
		
		Searcher liquorSearcher = new Searcher(rawCategDao);
		
		SearchResult searchRes = liquorSearcher.searchRawLiquor(tid, sParam);
		res.setSearchRes(searchRes);
		
		// 검색된 liquor id list를 가지고 DB 조회
		List<RawLiquorDto> rawLiquorList = new ArrayList<>();
		if(searchRes.getHitIds().size() > 0) {				
			rawLiquorList = rawLiquorDao.selectListByIds(searchRes.getHitIds());		
		}
		
		for(int i=0; i<rawLiquorList.size(); i++) {
			log.debug("["+rawLiquorList.get(i).getNameKr()+"/"+rawLiquorList.get(i).getNameEn()+"]");
		}
					
		res.setLiquorList(rawLiquorList);			
		return res;
	}
	
	public SearchResultVo searchIngredient(int tid, WebSearchParam sParam) throws Exception {
		
		SearchResultVo res = new SearchResultVo();
		
		// 검색어 필터
		// 1. 특수문자 제거
		if(sParam.getKeyword() != null) {
			sParam.setOrgKeyword(sParam.getKeyword());
			sParam.setKeyword(SearchParam.keywordFiter(sParam.getKeyword()));	
		}				
		
		Searcher liquorSearcher = new Searcher(rawCategDao);
		
		SearchResult searchRes = liquorSearcher.searchIngredient(tid, sParam);
		res.setSearchRes(searchRes);
		
		// 검색된 liquor id list를 가지고 DB 조회
		List<IngredientDto> ingredientList = new ArrayList<>();
		if(searchRes.getHitIds().size() > 0) {				
			
			log.debug("[HItsIds]:"+searchRes.getHitIds());
							
			ingredientList = ingredientDao.selectListByIds(searchRes.getHitIds());		
		}
		
		for(int i=0; i<ingredientList.size(); i++) {
			log.debug("["+ingredientList.get(i).getNameKr()+"/"+ingredientList.get(i).getNameEn()+"]");
		}
					
		res.setIngredeintList(ingredientList);			
		return res;
	}
	
}
