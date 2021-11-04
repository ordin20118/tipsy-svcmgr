package tipsy.svcmgr.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tipsy.common.basic.BasicListResponse;
import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.dao.PartTimeWorkerDao;
import tipsy.svcmgr.web.dao.PartTimeWorkerDto;

@Service
public class MemberMgmtServiceImpl implements MemberMgmtService {

	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	private PartTimeWorkerDao partTimeWorkerDao;

	@Override
	public BasicListResponse joinPartTimeWorker(int tid, PartTimeWorkerDto worker) {
		
		BasicListResponse res = new BasicListResponse();
		
		try {
			
			
			worker.setBankAccount(worker.getBankAccount().replace("-", ""));
			
			// 중복 체크
			PartTimeWorkerDto dupWorker = partTimeWorkerDao.selectByNameAccount(worker);
			
			if(dupWorker == null) {
				partTimeWorkerDao.insert(worker);
				res.setState(BasicListResponse.STATE_SUCCESS);	
			} else {
				res.setState(BasicListResponse.STATE_DUPLICATION);
			}
			
		} catch(Exception e) {
			log.error("[getCountryList]Error:"+e.getMessage(), e);
			res.setState(BasicListResponse.STATE_ERROR);	
		}
		
		return res;
	}
	
}