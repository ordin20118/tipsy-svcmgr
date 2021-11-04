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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import tipsy.common.basic.BasicController;
import tipsy.common.basic.BasicListResponse;
import tipsy.common.basic.ObjectMapperInstance;
import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.dao.BeerDto;
import tipsy.svcmgr.web.dao.PartTimeWorkerDto;
import tipsy.svcmgr.web.service.CountryService;
import tipsy.svcmgr.web.service.MemberMgmtService;


@Controller
public class MemberApiController extends BasicController {

	private static final String URL_PREFIX = "api/member_mgmt";
	
	Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	MemberMgmtService memberMgmtService;
		
	@RequestMapping(
			value= URL_PREFIX+"/search_beer2",
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> searchBeer(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response
			//@ModelAttribute ParamTxVo txListInfo
			){
		long startTime = System.currentTimeMillis();
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");
			//log.debug("[" + tid + "[Get Tx List]:"+txListInfo);
			//log.debug("[" + tid + "[Get Tx Paging]:"+txListInfo.getPaging());
			
			//BasicListResponse res = txService.getTxList(tid, txListInfo);
			//resString = mapper.writeValueAsString(res);
			
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
			value= URL_PREFIX+"/insert_part_worker",
			method={RequestMethod.POST}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> insertPartWorker(
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
			
			byte[] buf = getInputStreamToByte(request.getInputStream());
			
			log.debug("[" + tid + "].......... buf (" + buf + ")  ..........");
			
			PartTimeWorkerDto worker = mapper.readValue(buf, PartTimeWorkerDto.class);
			
			
			log.debug("[" + tid + "[Join Worker]:" + worker);
			
			BasicListResponse res = memberMgmtService.joinPartTimeWorker(tid, worker);
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
