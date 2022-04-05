package tipsy.svcmgr.web.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.bouncycastle.util.encoders.Hex;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import tipsy.common.basic.ObjectMapperInstance;
import tipsy.common.configuration.LoggerName;
import tipsy.es.ESManagerForBatch;
import tipsy.es.ESManagerForLiquorSearch;
import tipsy.es.EsRawLiquorVo;
import tipsy.svcmgr.helper.CategInstance;
import tipsy.svcmgr.helper.CountryInstance;
import tipsy.svcmgr.web.dao.CountryDao;
import tipsy.svcmgr.web.dao.CountryDto;
import tipsy.svcmgr.web.dao.LiquorDao;
import tipsy.svcmgr.web.dao.RawCategDao;
import tipsy.svcmgr.web.dao.RawCategDto;
import tipsy.svcmgr.web.dao.RawDataManualDao;
import tipsy.svcmgr.web.dao.RawLiquorDao;
import tipsy.svcmgr.web.dao.RawLiquorDto;

@Service
public class BuildSearchIndexService {

	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	private RawDataManualDao rawDataManualDao;
	
	@Autowired
	private RawLiquorDao rawLiquorDao;
	
	@Autowired
	private RawCategDao rawCategDao;
	
	@Autowired
	private CountryDao countryDao;
	
	
	
	public synchronized void test(int id) {
		ESManagerForBatch.getInstance().requestTest();
	}

	
	public synchronized void buildRawLiquorInfo(int tid, List<Integer> liquorIds) {
	
		
		try {
			SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
			ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
			
			byte[] ranBytes = new byte[8];
			(new Random()).nextBytes(ranBytes);
			String procCode = new String(Hex.encode(ranBytes));
						
			List<RawCategDto>   categList   = CategInstance.getInstance().getAllCateg(rawCategDao);
			List<CountryDto> 	countryList = CountryInstance.getInstance().getAllCountry(countryDao);
			
			Map<Integer, RawCategDto> categMap  = categList.stream().collect(Collectors.toMap(RawCategDto::getId, Function.identity()));
			Map<Integer, CountryDto>  countryMap = countryList.stream().collect(Collectors.toMap(CountryDto::getCountryId, Function.identity()));
			
			List<Object> list = new ArrayList<Object>();
			HashMap<String, Object> param = new HashMap<>();			
			if(liquorIds != null && liquorIds.size() > 0) {
				param.put("liquorIds", liquorIds);
			}
			
			int[] rowCount = {0};
			rawDataManualDao.runSelectStatement("tipsy.svcmgr.web.dao.RawLiquorDao.selectList", param, new ResultHandler() {
				
				@Override
				public void handleResult(ResultContext context) {
					rowCount[0]++;
					
					try {
						
						RawLiquorDto liquorInfo = (RawLiquorDto)context.getResultObject();						
						
						if(true) {	// null data check
							
							EsRawLiquorVo esLiquorInfo = new EsRawLiquorVo();
							
							esLiquorInfo.setCategory1Id(liquorInfo.getCategory1Id());
							esLiquorInfo.setCategory2Id(liquorInfo.getCategory2Id());							
							esLiquorInfo.setCategory1Name(liquorInfo.getCategory1Name());
							esLiquorInfo.setCategory2Name(liquorInfo.getCategory2Name());
							
							if(liquorInfo.getCategory3Id() != null) {
								esLiquorInfo.setCategory3Id(liquorInfo.getCategory3Id());
								esLiquorInfo.setCategory3Name(liquorInfo.getCategory3Name());
							} else if(liquorInfo.getCategory4Id() != null) {
								esLiquorInfo.setCategory4Id(liquorInfo.getCategory4Id());
								esLiquorInfo.setCategory4Name(liquorInfo.getCategory4Name());
							}
							
							esLiquorInfo.setEsUpdateDate(new Date());
							
							esLiquorInfo.setProcCode(procCode);
							esLiquorInfo.setRegDate(liquorInfo.getRegDate());	
							
							esLiquorInfo.setCountryName(liquorInfo.getCountryName());
							esLiquorInfo.setCountryId(liquorInfo.getCountryId());
								
							list.add(esLiquorInfo);
						}
						
						
						if(list.size() >= 200) {
							ESManagerForBatch.getInstance().createOrUpdateBulk(ESManagerForLiquorSearch.INDEX_RAW_LIQUOR_INFO, list, null);	
							list.clear();
							log.debug("["+tid+"] update raw_liquor info ["+rowCount[0]+"]");							
						}
						
						
					} catch (Exception e) {
						log.error("["+tid+"] handleResult error " + e);
					}
				}
			});
			
			if(list.size() > 0) {
								
				ESManagerForBatch.getInstance().createOrUpdateBulk(ESManagerForLiquorSearch.INDEX_RAW_LIQUOR_INFO, list, null);
				list.clear();
				log.info("["+tid+"] update raw_liquor info ["+rowCount[0]+"]");
			}
			
			log.info("["+tid+"] update raw_liquor DONE ["+rowCount[0]+"]");			
			
			
			if(liquorIds == null || liquorIds.size() == 0) {
				//BulkByScrollResponse delRes = ESManagerForBatch.getInstance().deleteByProcCode(ESManagerForLiquorSearch.INDEX_RAW_LIQUOR_INFO, procCode);
				//log.info("["+tid+"] delete Old procCode["+procCode+"] ["+delRes+"]");					
			}
			
		} catch (Exception e) {
			log.info("["+tid+"] buildRawLiquorInfo " + e, e);
		}
	}
	
	
}
