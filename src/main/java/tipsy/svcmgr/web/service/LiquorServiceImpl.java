package tipsy.svcmgr.web.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import tipsy.common.basic.BasicListResponse;
import tipsy.common.basic.ObjectMapperInstance;
import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.dao.LiquorDao;
import tipsy.svcmgr.web.dao.PartTimeWorkerDao;
import tipsy.svcmgr.web.dao.PartTimeWorkerDto;
import tipsy.svcmgr.web.vo.LiquorContentInfoVo;
import tipsy.svcmgr.web.vo.LiquorListItemVo;

@Service
public class LiquorServiceImpl implements LiquorService {

	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	private LiquorDao liquorDao;

	@Override
	public LiquorContentInfoVo genLiquorInfo(int tid, int liquorId) throws Exception {
		
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		LiquorContentInfoVo res = new LiquorContentInfoVo();
		HashMap<String,Object> resMap = new HashMap<String,Object>();
		
		try {
			
			// select liquor
			LiquorListItemVo item = liquorDao.selLiquorView(liquorId);
			res.setItem(item);
			
			// select imges except repImg
			
			// select rating
			
			
		} catch(Exception e) {
			log.error("[getLiquorInfo]Error:"+e.getMessage(), e);
			throw e;
		}
		
		return res;
	}

	@Override
	public List<LiquorListItemVo> genLiquorListItem(int tid, List<Integer> liquorIds) throws Exception {
		
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();

		List<LiquorListItemVo> res = null;
		
		try {
			
			// select list
			
			
		} catch(Exception e) {
			log.error("[getLiquorListItem]Error:"+e.getMessage(), e);
			throw e;
		}
		
		return res;
	}
	
}