package tipsy.common.basic;

import java.util.Date;

import org.slf4j.Logger;

import tipsy.common.configuration.Configuration;
import tipsy.common.util.GenException;

public class BasicService {

	
	protected Configuration config = Configuration.getInstance();
	
	public String getServerName() {
		return config.getServerName();
	}
	
	
	public BasicResponse genErrorRes(int tid, String operation, Exception ex, Logger log) {
		BasicResponse res = null;
		
		if(ex instanceof GenException) {
			res = new BasicResponse(getServerName(), operation, new Date(), ((GenException)ex).getErrorCode(), ex.getMessage());
		} else {
			res = new BasicResponse(getServerName(), operation, new Date(), GenException.INTERNAL_ERROR, ex.getMessage());
		}
		
		if(log != null) {
			log.error("["+tid+"] ERROR ["+operation+"] code["+res.getStatus()+"] ["+res.getMessage()+"]", ex);
		}
		
		return res;
	}
	
}
