package tipsy.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import tipsy.common.configuration.Configuration;

public class EsClientManager {

	 //private ElasticsearchClient gClient = null;
	 private RestHighLevelClient gClient = null;
	
	private static EsClientManager instance = null;
	private EsClientManager() {	
    }
	
    public static synchronized EsClientManager getInstance() {
        if (instance == null) {
            instance = new EsClientManager();
        }
        return instance;
    }
        
    
    public synchronized RestHighLevelClient getClient() {
    	
    	String 	host   = Configuration.getInstance().getStringExtra("es.host");
    	int 	port   = Configuration.getInstance().getIntegerExtra("es.port", 80);
    	String 	schema = Configuration.getInstance().getStringExtra("es.schema");
    	
    	if(gClient != null) {
    		
    		return gClient;
    	} else {
    		
    		gClient = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, schema))
        			.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(60000)
        			.setSocketTimeout(60000))
        			);
    		
    		return gClient;	
    	}
    	
    }
    
    public void returnClient(RestHighLevelClient client) {
    	
    }
    
    /*
     * ES Client v8.1
     */
//    public synchronized ElasticsearchClient getClient() {
//    	
//    	String 	host   = Configuration.getInstance().getStringExtra("es.host");
//    	int 	port   = Configuration.getInstance().getIntegerExtra("es.port", 80);
//    	String 	schema = Configuration.getInstance().getStringExtra("es.schema");
//    	
//    	System.out.println("["+host+"]["+port+"]["+schema+"]");
//    	
//    	if(gClient != null) {
//    		
//    		return gClient;
//    	} else {
//    		
//    		// Create the low-level client
//    		RestClient restClient = RestClient.builder(
//    		    new HttpHost(host, port)).build();
//
//    		// Create the transport with a Jackson mapper
//    		ElasticsearchTransport transport = new RestClientTransport(
//    		    restClient, new JacksonJsonpMapper());
//
//    		// And create the API client
//    		gClient = new ElasticsearchClient(transport);
//    		
//    		return gClient;	
//    	}
//    	
//    }
    
    
}
