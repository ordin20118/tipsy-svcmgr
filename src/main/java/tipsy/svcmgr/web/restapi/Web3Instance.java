package tipsy.svcmgr.web.restapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import tipsy.common.basic.ObjectMapperInstance;

public class Web3Instance {
	

	private static Web3Instance instance = null;
	
	
	private Web3Instance() {	
		
		
    }
	
    public static synchronized Web3Instance getInstance() {
        if (instance == null) {
            instance = new Web3Instance();
        }
        return instance;
    }
	
}
