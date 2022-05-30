package tipsy.svcmgr.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import tipsy.common.basic.BasicController;
import tipsy.common.basic.ObjectMapperInstance;
import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.dao.RawLiquorDao;
import tipsy.svcmgr.web.dao.RawLiquorDto;
import tipsy.svcmgr.web.search.BuildSearchIndexService;


@Controller
public class EsManageApiController extends BasicController {

	private static final String URL_PREFIX = "api";
	
	Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	BuildSearchIndexService buildSearchIndexService;
	
	@Autowired
	public RawLiquorDao dao;
	
	@RequestMapping(
			value= URL_PREFIX+"/bulk_raw_liquor",
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> uploadRawLiquor(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response
			){
		long startTime = System.currentTimeMillis();
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");
						
			buildSearchIndexService.buildRawLiquorInfo(tid, null);
			
//			BasicListResponse res = countryService.getCountryList(tid);
//			resString = mapper.writeValueAsString(res);
			
		} catch (Exception e) {
			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setHeader(XSTATUS_CODE, "500");
			response.setHeader(XSTATUS_REASON, e.getMessage());
			resString = getSimpleErrRes(e);
			log.error("["+tid+"] status["+resStatus+"] ["+e+"]", e);	
		} finally {
			log.debug("["+tid+"]..........execTime("+(System.currentTimeMillis() - startTime)+")ms..........");
		}
		
		return new ResponseEntity<String>(resString, resStatus);
	}
	
	@RequestMapping(
			value= URL_PREFIX+"/test",
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> test(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response
			){
		long startTime = System.currentTimeMillis();
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");
			
			RawLiquorDto liquor = dao.selectOne(1);
			
			log.debug("["+liquor+"]");
			
			
		} catch (Exception e) {
			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setHeader(XSTATUS_CODE, "500");
			response.setHeader(XSTATUS_REASON, e.getMessage());
			resString = getSimpleErrRes(e);
			log.error("["+tid+"] status["+resStatus+"] ["+e+"]", e);	
		} finally {
			log.debug("["+tid+"]..........execTime("+(System.currentTimeMillis() - startTime)+")ms..........");
		}
		
		return new ResponseEntity<String>(resString, resStatus);
	}
 
	
}
