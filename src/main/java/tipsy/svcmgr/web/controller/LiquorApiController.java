package tipsy.svcmgr.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import tipsy.common.basic.BasicController;
import tipsy.common.basic.BasicListResponse;
import tipsy.common.basic.ObjectMapperInstance;
import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.dao.BeerDto;
import tipsy.svcmgr.web.dao.PartTimeWorkerDto;
import tipsy.svcmgr.web.service.CountryService;
import tipsy.svcmgr.web.service.LiquorService;
import tipsy.svcmgr.web.service.MemberMgmtService;
import tipsy.svcmgr.web.vo.LiquorContentInfoVo;


@Controller
public class LiquorApiController extends BasicController {

	private static final String URL_PREFIX = "api";
	private static final String URL_SUFFIX = ".tipsy";
	
	Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	LiquorService liquorService;
	
		
	@RequestMapping(
			value= URL_PREFIX + "/liquor" + URL_SUFFIX,
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> searchBeer(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(name="liquorId") int liquorId
			){
		long startTime = System.currentTimeMillis();
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");
			log.debug("[" + tid + "][Liquor_ID]:"+liquorId);
			
			LiquorContentInfoVo liquor = liquorService.genLiquorInfo(tid, liquorId);
			BasicListResponse res = new BasicListResponse();
			res.setData(liquor);
			res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);
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
