package gwang.allalcohol.web.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import gwang.allalcohol.web.dao.BeerDto;
import gwang.allalcohol.web.dao.ImageDto;
import gwang.allalcohol.web.dao.PartJobDto;
import gwang.allalcohol.web.dao.PartTimeWorkerDto;
import gwang.allalcohol.web.service.CountryService;
import gwang.allalcohol.web.service.DataMgmtService;
import gwang.common.basic.BasicController;
import gwang.common.basic.BasicListResponse;
import gwang.common.basic.ObjectMapperInstance;
import gwang.common.configuration.LoggerName;


@Controller
public class FileApiController extends BasicController {

	private static final String URL_PREFIX = "api";
	
	Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	DataMgmtService dataMgmtService;
	
	
	
	@RequestMapping(
			value= URL_PREFIX+"/image/{dir}/{image}",
			method={RequestMethod.GET}, 
			produces="application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<byte[]> getImage(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("dir")String dir,
			@PathVariable("image")String image
			){
		
		long startTime = System.currentTimeMillis();
		int tid = genTid();
		String resString = "{}";
		HttpStatus resStatus = HttpStatus.OK;
		HttpHeaders header = new HttpHeaders();
		ObjectMapper mapper = ObjectMapperInstance.getInstance().getMapper();
		
		try {
			log.debug("[" + tid + "].......... Start (" + genReqInfo(request) + ")  ..........");
			log.debug("[" + tid + "][Get Image]");
			
			String rootPath = config.getStringExtra("datapath.image");
			String exp = "";
			int dotPosition = image.lastIndexOf('.');
		    
			if (-1 != dotPosition && image.length() - 1 > dotPosition) {
				exp = image.substring(dotPosition + 1);
			} else {
				exp = "png";
		    }
			
			File file = new File(rootPath + "/" + dir + "/" + image);
	        
			switch(exp) {
				case "jpeg":
					header.setContentType(MediaType.IMAGE_JPEG);		
					break;
				case "png":
					header.setContentType(MediaType.IMAGE_PNG);
					break;
			}  
			
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(new FileInputStream(file)), header, HttpStatus.CREATED);
					
		} catch (Exception e) {
			resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setHeader(XSTATUS_CODE, "500");
			response.setHeader(XSTATUS_REASON, e.getMessage());
			resString = getSimpleErrRes(e);
			log.error("["+tid+"] status["+resStatus+"] ["+e+"]", e);	
		} finally {
			log.debug("["+tid+"]..........execTime("+(System.currentTimeMillis() - startTime)+")ms..........");
		}
		
		return null;
	}
	

	
}
