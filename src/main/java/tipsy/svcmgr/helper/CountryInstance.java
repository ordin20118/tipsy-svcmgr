package tipsy.svcmgr.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.dao.CountryDao;
import tipsy.svcmgr.web.dao.CountryDto;


public class CountryInstance {

	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	private static CountryInstance instance = null;
	private CountryInstance() {
		
    }
	
    public static synchronized CountryInstance getInstance() {
        if (instance == null) {
            instance = new CountryInstance();
        }
        return instance;
    }
    
	private Date loadCountryOrg = null;    
	private List<CountryDto> countryList = null;
    
	public synchronized List<CountryDto> getAllCountry(CountryDao countryDao) {
		
    	if(countryList == null) {
    		//System.out.println("RELOAD ALL CATEGORY!!!");
    		countryList = countryDao.selectList();
    		loadCountryOrg = new Date();
    		log.debug(">>>>>>>>>>>>> LOAD getAllCateg() SIZE["+countryList.size()+"]");
    		
    	} else if((System.currentTimeMillis() - loadCountryOrg.getTime()) > (5 * 60 * 1000)) {
    		Thread thread = new Thread( () -> {
    			countryList = countryDao.selectList();
    			loadCountryOrg = new Date();
	    		log.debug(">>>>>>>>>>>>> LOAD getAllCateg() By background thread! SIZE["+countryList.size()+"]");
    		});
    		thread.start();    		
    	}
    	return countryList;
    }
	
	public String genCountryUpdateEtag() {		
		SimpleDateFormat sdf = new SimpleDateFormat();
		byte[] dateBytes = sdf.format(loadCountryOrg).getBytes();
		Random ran = new Random();
		ran.nextBytes(dateBytes);		
		String eTag = new String(Hex.encode(dateBytes));		
		return eTag;
	}
}
