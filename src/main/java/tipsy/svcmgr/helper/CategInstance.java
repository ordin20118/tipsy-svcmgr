package tipsy.svcmgr.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.dao.RawCategDao;
import tipsy.svcmgr.web.dao.RawCategDto;


public class CategInstance {

	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	private static CategInstance instance = null;
	private CategInstance() {
		
    }
	
    public static synchronized CategInstance getInstance() {
        if (instance == null) {
            instance = new CategInstance();
        }
        return instance;
    }
    
	private Date loadCategOrg = null;    
	private List<RawCategDto> categList = null;
    
	public synchronized List<RawCategDto> getAllCateg(RawCategDao rawCategDao) {
		
    	if(categList == null) {
    		//System.out.println("RELOAD ALL CATEGORY!!!");
    		categList = rawCategDao.selectAll();
    		loadCategOrg = new Date();
    		log.debug(">>>>>>>>>>>>> LOAD getAllCateg() SIZE["+categList.size()+"]");
    		
    	} else if((System.currentTimeMillis() - loadCategOrg.getTime()) > (5 * 60 * 1000)) {
    		Thread thread = new Thread( () -> {
    			categList = rawCategDao.selectAll();
    			loadCategOrg = new Date();
	    		log.debug(">>>>>>>>>>>>> LOAD getAllCateg() By background thread! SIZE["+categList.size()+"]");
    		});
    		thread.start();    		
    	}
    	return categList;
    }
	
	public String genCategUpdateEtag() {		
		SimpleDateFormat sdf = new SimpleDateFormat();
		byte[] dateBytes = sdf.format(loadCategOrg).getBytes();
		Random ran = new Random();
		ran.nextBytes(dateBytes);		
		String eTag = new String(Hex.encode(dateBytes));		
		return eTag;
	}
}
