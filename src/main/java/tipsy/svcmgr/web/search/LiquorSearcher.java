package tipsy.svcmgr.web.search;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipsy.common.configuration.LoggerName;
import tipsy.es.EsClientManager;
import tipsy.svcmgr.helper.CategInstance;
import tipsy.svcmgr.web.controller.param.WebSearchParam;
import tipsy.svcmgr.web.dao.CategoryDao;
import tipsy.svcmgr.web.dao.RawCategDao;
import tipsy.svcmgr.web.dao.RawCategDto;

public class LiquorSearcher {
	
	public static final String INDEX_RAW_LIQUOR = "raw_liquor_info";
	
	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	private RawCategDao rawCategDao;
	
	public LiquorSearcher() {};
	public LiquorSearcher(RawCategDao rawCategDao) {
		this.rawCategDao 	= 	rawCategDao;
	}
	
	public SearchResult searchRawLiquor(int tid, WebSearchParam sParam) throws Exception {
		
		try {
			
			CategInstance categIns = CategInstance.getInstance();
			List<RawCategDto> categData = categIns.getAllCateg(rawCategDao);
	
			SearchParam param = new SearchParam();
			param.setOrgKeyword(sParam.getOrgKeyword());
			param.setKeyword(sParam.getKeyword());
			int from = (sParam.getPaging().getNowPage() -1) * sParam.getPaging().getPerPage();
			param.setFrom(from);
			param.setSize(sParam.getPaging().getPerPage());
			param.setOrgParam(sParam);
			
			long startTime = System.currentTimeMillis();
			
			SearchResponse esRes = null;			
			SearchResult res = new SearchResult();
			
			try {
				
				log.debug("["+tid+"] Search Prod Param:["+param+"]");
				
				Map<Integer, RawCategDto> categMap   = new HashMap<Integer, RawCategDto>();
				
				for(int i=0; i<categData.size(); i++) {
					RawCategDto categ = categData.get(i);					
					categMap.put(categ.getId(), categ);
				}
				
				esRes = esSearchLiquorV1(tid, param, categData);
				res.setParam(param);
				
				// 후처리
				if(esRes != null) {
				
					SearchHit[] sHit = esRes.getHits().getHits();
					if(sHit.length > 0) {
						List<HitData> hitList = new ArrayList<>();
						
						for(int i=0; i<sHit.length; i++) {						
							Map<String, Object> esMap = sHit[i].getSourceAsMap();
													
							HitData data = new HitData();
							data.setDataId(Integer.parseInt(esMap.get("liquor_id").toString()));
							data.setScore(sHit[i].getScore());		
							
							hitList.add(data);
						}
						
						res.setHits(hitList);
					}
				}				
				
				long execTime = System.currentTimeMillis() - startTime;
				log.debug("[LiquorSearcher][searchLiquor exec (" + execTime+")ms");
								
			} catch(Exception e) {
				log.error("Search Liquor Error:["+e.getMessage()+"]", e);
			}

			return res;
			
		} catch (Exception e) {
    		Method nowmethod = new Object(){}.getClass().getEnclosingMethod();
    		throw new Exception(nowmethod.getName()+ " error "+ e.getMessage(), e);
    	}
		
	}
	
	public SearchResponse esSearchLiquorV1(int tid, SearchParam searchParam, List<RawCategDto> categList) throws Exception {
		
		RestHighLevelClient client = null;
		
		 try {
			 
			 client = EsClientManager.getInstance().getClient();
			 SearchRequest esRequest = new SearchRequest(INDEX_RAW_LIQUOR);
			 SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
    		
			 BoolQueryBuilder mainBool = QueryBuilders.boolQuery();
			 
			 MultiMatchQueryBuilder multiQuery = 
					QueryBuilders.multiMatchQuery(searchParam.getKeyword()) 
					.field("name_kr", 1.0f)
					.field("name_en", 1.0f);
			 			 
			 // TODO: 테스트 결과에 따라서 변경 필요
			 multiQuery.type(Type.MOST_FIELDS);
			 
			 mainBool.should(multiQuery);
			 
			 sourceBuilder.sort(new FieldSortBuilder("_score").order(SortOrder.DESC));
			 sourceBuilder.query(mainBool);	
			 sourceBuilder.from(searchParam.getFrom()); 
			 sourceBuilder.size(searchParam.getSize());
			 sourceBuilder.minScore(0.01f);
			 			 
			 JSONObject esQuery = new JSONObject(sourceBuilder.toString());
			 log.debug("["+tid+"] ES SOURCE[\n"+ esQuery.toString(4) +"\n]");

			 esRequest.source(sourceBuilder);
			 SearchResponse res = client.search(esRequest, RequestOptions.DEFAULT);
			 return res;
			 
		 } catch(Exception e) {			 
			 Method nowmethod = new Object(){}.getClass().getEnclosingMethod();
			 throw new Exception(nowmethod.getName()+ " error "+ e.getMessage(), e); 
		 } finally {
			 EsClientManager.getInstance().returnClient(client);
		 }	
	}
	

}
