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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import tipsy.common.basic.BasicController;
import tipsy.common.basic.BasicListResponse;
import tipsy.common.basic.ObjectMapperInstance;
import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.controller.param.PagingParam;
import tipsy.svcmgr.web.dao.BeerDto;
import tipsy.svcmgr.web.dao.ImageDto;
import tipsy.svcmgr.web.dao.LiquorDto;
import tipsy.svcmgr.web.dao.PartJobDto;
import tipsy.svcmgr.web.dao.PartTimeWorkerDto;
import tipsy.svcmgr.web.service.CountryService;
import tipsy.svcmgr.web.service.DataMgmtService;


@Controller
public class DataCollectApiController extends BasicController {

	private static final String URL_PREFIX = "api";
	
	Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	DataMgmtService dataMgmtService;
	
	
	
	@RequestMapping(
			value= URL_PREFIX+"/country_list",
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> getCountryList(
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
			log.debug("[" + tid + "][Get Country List]");
						
			BasicListResponse res = countryService.getCountryList(tid);
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
	

	
	@RequestMapping(
			value= URL_PREFIX+"/my_job_list",
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> getMyJobList(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute PartTimeWorkerDto worker
			){
		long startTime = System.currentTimeMillis();
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");
			log.debug("[" + tid + "][Get My Job List]" + worker);
			log.debug("[" + tid + "][Get My Job Paging]" + worker.getPaging());
			log.debug("[" + tid + "][Get My Job Orderby]" + worker.getOrderby());
						
			BasicListResponse res = dataMgmtService.getPartJobList(tid, worker);
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
	
	

	@RequestMapping(
			value= URL_PREFIX+"/jobL",
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> getJobList(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			PagingParam param
			){
		long startTime = System.currentTimeMillis();
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");
			log.debug("[" + tid + "][Get Job List]");
			log.debug("[" + tid + "][Get Job Paging]" + param.getPaging());
			log.debug("[" + tid + "][Get Job Orderby]" + param.getOrderby());
						
			BasicListResponse res = dataMgmtService.getPartJobList(tid, param);
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
	
	


	@RequestMapping(
			value= URL_PREFIX+"/category_child",
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> getCategoryChild(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("categId") String categId
			){
		long startTime = System.currentTimeMillis();
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");
			log.debug("[" + tid + "].......... categId (" + categId + ")  ..........");
						
			BasicListResponse res = dataMgmtService.getCategoryChild(tid, categId);
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
	

	@RequestMapping(
			value= URL_PREFIX+"/search_beer",
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> searchBeerName(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("keyword") String keyword
			){
		long startTime = System.currentTimeMillis();
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");
			log.debug("[" + tid + "].......... Search Beer Name - keyword (" + keyword + ")  ..........");
						
			BasicListResponse res = dataMgmtService.searchBeer(tid, keyword);
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
	
	

	@RequestMapping(
			value= URL_PREFIX+"/update_category",
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> updateCategory(
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
						
			BasicListResponse res = dataMgmtService.addCategory(tid);
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
	
	

	@RequestMapping(
			value= URL_PREFIX+"/insert_beer",
			method={RequestMethod.POST}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> insertBeer(
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
			
			BeerDto beer = mapper.readValue(buf, BeerDto.class);
			PartJobDto job = mapper.readValue(buf, PartJobDto.class);
			PartTimeWorkerDto worker = mapper.readValue(buf, PartTimeWorkerDto.class);
			
			log.debug("[" + tid + "][Insert Beer Info]:" + beer);
			
			BasicListResponse res = dataMgmtService.insertBeerInfo(tid, beer, worker, job);
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
	
	@RequestMapping(
			value= URL_PREFIX+"/insert_spirits",
			method={RequestMethod.POST}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> insertSpirits(
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
			
			LiquorDto liquor = mapper.readValue(buf, LiquorDto.class);
			
			log.debug("[" + tid + "][Insert Spirits Info]:" + liquor);
			
			BasicListResponse res = dataMgmtService.insertSpiritsInfo(tid, liquor);
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
	
	@RequestMapping(
			value= URL_PREFIX + "/upload_image", 
			method={RequestMethod.POST},
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> uploadImage(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute MultipartFile image,
			@ModelAttribute ImageDto imageParam
			){
		long startTime = System.currentTimeMillis();
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");
			log.debug("[" + tid + "].......... Image (" + imageParam + ")  ..........");
			
			
			
//			ImageParam imageParam = new ImageParam();
//			imageParam.setImageFile(image);
//			imageParam.setName(request.getHeader("name"));
//			imageParam.setContentId(request.getHeader("content_id"));
//			imageParam.setContentType(request.getHeader("content_type"));
			
			imageParam.setImageFile(image);			
			BasicListResponse res = dataMgmtService.uploadImage(tid, imageParam);
			res.setState(BasicListResponse.STATE_SUCCESS);
			
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
 

	@RequestMapping(
			value= URL_PREFIX+"/checkin_part_worker",
			method={RequestMethod.POST}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<String> checkinPartWorker(
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
			
			
			log.debug("[" + tid + "[CheckIn Worker]:" + worker);
			
			BasicListResponse res = dataMgmtService.checkInPartTimeWorker(tid, worker);
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
