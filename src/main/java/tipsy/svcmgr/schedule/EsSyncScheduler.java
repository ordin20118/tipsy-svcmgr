package tipsy.svcmgr.schedule;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tipsy.common.configuration.Configuration;
import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.search.BuildSearchIndexService;


@Component
public class EsSyncScheduler {

	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);
		
	@Autowired
	private BuildSearchIndexService buildSearchIndexService;
	
	//매일 18시 05분에 실행>> UTC18시 - 한국시간 새벽3시.
	//@Scheduled(cron="0 05 18 * * *")
	@Scheduled(fixedDelay=3000)
	public void syncEsWithRDB () {
		
		int tid = (new Random()).nextInt(10000);
		Configuration config = Configuration.getInstance();
		
		log.debug("[syncEsWithRDB]-" + config.getStringExtra("enable.es.sync", "false"));
		
		if((config.getStringExtra("enable.es.sync", "false").equals("true")) == false) {
			return;
		}
		
		try {
			
			log.debug("[Sync RawData to ES]");
			
			List<Integer> testList = new ArrayList<>();
			testList.add(1);
			testList.add(2);
			testList.add(3);
			
			buildSearchIndexService.buildRawLiquorInfo(tid, testList);
			//buildSearchIndexService.buildIngredient(tid, null);
						
		} catch (Exception e) {
			Method nowmethod = new Object(){}.getClass().getEnclosingMethod();
			log.error("["+tid+"] "+nowmethod.getName()+" ERROR !" + e, e);
		}
		
		//log.debug("["+tid+"] ===================================================");
	}
	
}
