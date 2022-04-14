package tipsy.svcmgr.web.search;

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
import tipsy.common.basic.BasicListResponse;
import tipsy.common.basic.ObjectMapperInstance;
import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.controller.param.WebSearchParam;
import tipsy.svcmgr.web.search.BuildSearchIndexService;
import tipsy.svcmgr.web.search.SearchService.SearchResultVo;


@Controller
public class SearchApiController extends BasicController {

	private static final String URL_PREFIX = "api";
	
	Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	SearchService searchService;
	
	@RequestMapping(
			value= URL_PREFIX+"/search",
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> search(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			WebSearchParam searchParam
			){
		long startTime = System.currentTimeMillis();
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			
			log.debug("[" + tid + "] .......... Start (" + genReqInfo(request) + ") ..........");
			log.debug("[" + tid + "] .......... searchParam(" + searchParam + ") ..........");
			
			BasicListResponse res = new BasicListResponse();
			SearchResultVo sRes = searchService.searchRawLiquor(tid, searchParam);
			
			//log.debug("[" + tid + "] .......... res(" + sRes + ") ..........");
			
			res.setData(sRes);
			res.setSParam(searchParam);
			
			resString = mapper.writeValueAsString(res);
			
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
