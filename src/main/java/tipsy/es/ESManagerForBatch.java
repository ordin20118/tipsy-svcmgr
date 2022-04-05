package tipsy.es;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import tipsy.common.basic.ObjectMapperInstance;
import tipsy.common.configuration.LoggerName;

public class ESManagerForBatch {

	private static ESManagerForBatch instance = null;
	private ESManagerForBatch() {	
    }
	
    public static synchronized ESManagerForBatch getInstance() {
        if (instance == null) {
            instance = new ESManagerForBatch();
        }
        return instance;
    }
    
    private Logger log = LoggerFactory.getLogger(LoggerName.SVC);  
    
    public void requestTest() {
    	
//    	ElasticsearchClient client = null;
//    	
//    	try {
//    		
//    		client = EsClientManager.getInstance().getClient();
//    		
//    		SearchResponse<EsRawLiquorVo> search = client.search(s -> s
//        		    .index("raw_liquor_info")
//        		    .query(q -> q
//        		        .term(t -> t
//        		            .field("name_kr")
//        		            .value(v -> v.stringValue("bicycle"))
//        		        )),
//        		    EsRawLiquorVo.class);
//
//    		for (Hit<EsRawLiquorVo> hit: search.hits().hits()) {
//    		    //processProduct(hit.source());
//    			log.debug(hit.source().toString());
//    		}
//    		
//    	} catch(Exception e) {
//    		e.printStackTrace();
//    	}
    	
    	
    }
    
    
    public BulkResponse createOrUpdateBulk(String indexName, List<Object> list, ActionListener<BulkResponse> listener) throws IOException {
    	ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
    	RestHighLevelClient client = null;
    	try {
    		client = EsClientManager.getInstance().getClient();
    		BulkRequest bulkRequest = new BulkRequest();
    		
    		for(int i=0; i<list.size(); i++) {
    			IndexRequest request = new IndexRequest(indexName);
        		request.id(list.get(i).toString());
        		request.source(mapper.writeValueAsString(list.get(i)).getBytes("UTF-8"), XContentType.JSON);
        		bulkRequest.add(request);        		
    		}
    		
    		if(listener != null) {
    			client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, listener);
    			return null;
    		} else {
    			return client.bulk(bulkRequest, RequestOptions.DEFAULT);	
    		}
    		
    	} finally {
    		//
    	}
    }
    
    
    
//    public IndexResponse createOrUpdateRawLiquor(EsRawLiquorVo esLiquorVo) throws IOException {
//    	ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
//    	
//    	RestHighLevelClient client = null;
//    	try {
//    		
//    		client = EsClientManager.getInstance().getClient();
//    		
//    		IndexRequest request = new IndexRequest(ESManagerForLiquorSearch.INDEX_RAW_LIQUOR_INFO);
//    		request.id(esLiquorVo.getLiquorId() + "");
//    		log.debug(mapper.writeValueAsString(esLiquorVo));
//    		request.source(mapper.writeValueAsString(esLiquorVo).getBytes("UTF-8"), XContentType.JSON);
//    		return client.index(request, RequestOptions.DEFAULT);
//    		
//    	} finally {
//    		EsClientManager.getInstance().returnClient(client);
//    	}
//    }
//    
//    public BulkResponse createOrUpdateRawLiquorBulk(List<EsRawLiquorVo> esRawLiquorList, ActionListener<BulkResponse> listener) throws IOException {
//    	ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
//    	RestHighLevelClient client = null;
//    	try {
//    		
//    		client = EsClientManager.getInstance().getClient();
//    		BulkRequest bulkRequest = new BulkRequest();
//    		
//    		for(int i=0; i<esRawLiquorList.size(); i++) {
//    			IndexRequest request = new IndexRequest(ESManagerForLiquorSearch.INDEX_RAW_LIQUOR_INFO);
//        		request.id(esRawLiquorList.get(i).getLiquorId() + "");
//        		request.source(mapper.writeValueAsString(esRawLiquorList.get(i)).getBytes("UTF-8"), XContentType.JSON);
//        		bulkRequest.add(request);
//    		}
//    		
//    		if(listener != null) {
//    			client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, listener);
//    			return null;
//    		} else {
//    			return client.bulk(bulkRequest, RequestOptions.DEFAULT);	
//    		}
//    		
//    	} finally {
//    		EsClientManager.getInstance().returnClient(client);
//    	}
//    }
//    
//    
   
//    
//    
//    public BulkByScrollResponse deleteByProcCode(String index, String procCode) throws IOException {
//    	
//    	RestHighLevelClient client = null;
//    	try {
//    		client = EsClientManager.getInstance().getClient();
//    		DeleteByQueryRequest delReq = new DeleteByQueryRequest(index);
//    		BoolQueryBuilder boolQ = QueryBuilders.boolQuery();
//    		boolQ.mustNot(QueryBuilders.matchQuery("proc_code", procCode));
//    		
//    		delReq.setQuery(boolQ);
//    		
//    		return client.deleteByQuery(delReq, RequestOptions.DEFAULT);
//    	} finally {
//    		EsClientManager.getInstance().returnClient(client);
//    	}
//    }
    
}
