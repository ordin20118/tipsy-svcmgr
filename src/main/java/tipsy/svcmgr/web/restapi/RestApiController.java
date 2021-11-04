package tipsy.svcmgr.web.restapi;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import tipsy.common.basic.BasicController;
import tipsy.common.basic.BasicListResponse;
import tipsy.common.basic.ObjectMapperInstance;
import tipsy.common.configuration.LoggerName;
import tipsy.svcmgr.web.dao.BeerDto;


@Controller
public class RestApiController extends BasicController {
	
	//private static final String URL_PREFIX = "search";
	private static final String URL_PREFIX = "api";
	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);

	@Autowired
	private RestApiServiceImpl restApiService;
	
	@RequestMapping(
			value = { URL_PREFIX + "/new_coupon" }, 
			method = {RequestMethod.POST}, 
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> newCoupon(
			Model model, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@ModelAttribute BeerDto coupon
			) {
		
		long startTime = System.currentTimeMillis();
		int retSize = 0;
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");			
			log.debug("[" + tid + "].......... Param (" + coupon + ")  ..........");
			
			//log.debug("Request String : " + request.getParameter("hi"));
//			BasicListResponse res = restApiService.createCoupon(tid, coupon);
//			
//			log.debug("[" + tid + "][Result Of Create Coupon]:" + res);
//			resString = mapper.writeValueAsString(res);
		
		} catch (Exception e) {
			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setHeader("X-Status-Code", "500");
			response.setHeader("X-Status-Reason", e.getMessage());

			log.error("[" + tid + "] status[" + resStatus + "] [" + e + "]", e);
		} finally {
			log.debug("[" + tid + "].......... End retSize(" + retSize + ") execTime(" + (System.currentTimeMillis() - startTime) + ")ms..........");
		}

		return new ResponseEntity<String>(resString, resStatus);
	}
	

	@RequestMapping(
			value = { URL_PREFIX + "/coupon" }, 
			method = {RequestMethod.GET}, 
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getCoupon(
			Model model, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam("code")String code) {
		
		long startTime = System.currentTimeMillis();
		int retSize = 0;
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");			
			log.debug("[Check coupon]code:" + code);
			
//			BasicListResponse res = restApiService.getCoupon(tid, code);
//			
//			log.debug("[Result Of Check Coupon]:" + res);
//			
//			resString = mapper.writeValueAsString(res);
		
		} catch (Exception e) {
			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setHeader("X-Status-Code", "500");
			response.setHeader("X-Status-Reason", e.getMessage());

			log.error("[" + tid + "] status[" + resStatus + "] [" + e + "]", e);
		} finally {
			log.debug("[" + tid + "].......... End retSize(" + retSize + ") execTime(" + (System.currentTimeMillis() - startTime) + ")ms..........");
		}

		return new ResponseEntity<String>(resString, resStatus);
	}
	

	@RequestMapping(
			value = { URL_PREFIX + "/coupon_list" }, 
			method = {RequestMethod.GET}, 
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> getCouponList(
			Model model, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@ModelAttribute BeerDto coupon) {
		
		long startTime = System.currentTimeMillis();
		int retSize = 0;
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");			
			log.debug("[" + tid + "][Get coupon list]param:" + coupon);
			
//			BasicListResponse res = restApiService.getCouponList(tid, coupon);
//			
//			log.debug("[Result Of Get CouponList]:" + res);
//			
//			resString = mapper.writeValueAsString(res);
		
		} catch (Exception e) {
			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setHeader("X-Status-Code", "500");
			response.setHeader("X-Status-Reason", e.getMessage());

			log.error("[" + tid + "] status[" + resStatus + "] [" + e + "]", e);
		} finally {
			log.debug("[" + tid + "].......... End retSize(" + retSize + ") execTime(" + (System.currentTimeMillis() - startTime) + ")ms..........");
		}

		return new ResponseEntity<String>(resString, resStatus);
	}
	

	@RequestMapping(
			value = { URL_PREFIX + "/use_coupon" }, 
			method = {RequestMethod.POST}, 
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> useCoupon(
			Model model, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam("code")String code) {
		
		long startTime = System.currentTimeMillis();
		int retSize = 0;
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");			
//			log.debug("[Use coupon]code:" + code);
//			
//			BasicListResponse res = restApiService.useCoupon(tid, code);
//			
//			log.debug("[Result Of Check Coupon]:" + res);
//			
//			resString = mapper.writeValueAsString(res);
		
		} catch (Exception e) {
			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setHeader("X-Status-Code", "500");
			response.setHeader("X-Status-Reason", e.getMessage());

			log.error("[" + tid + "] status[" + resStatus + "] [" + e + "]", e);
		} finally {
			log.debug("[" + tid + "].......... End retSize(" + retSize + ") execTime(" + (System.currentTimeMillis() - startTime) + ")ms..........");
		}

		return new ResponseEntity<String>(resString, resStatus);
	}


}
