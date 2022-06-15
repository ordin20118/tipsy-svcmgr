package tipsy.svcmgr.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tipsy.common.configuration.LoggerName;

@Controller
public class UiController {
   
	private static final String URL_PREFIX = "page";
	private static final String URL_SUFFIX = ".tipsy";
	 
	Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@RequestMapping(value= URL_PREFIX + "/home"+URL_SUFFIX, method={RequestMethod.GET})
	public String moveHomePage(Model model, HttpServletRequest request) {
		return "home";
	}
	
	@RequestMapping(value= URL_PREFIX + "/insert_beer"+URL_SUFFIX, method={RequestMethod.GET})
	public String moveInsertBeerPage(Model model, HttpServletRequest request) {
		return "manage/insert_beer";
	}
	
	@RequestMapping(value= URL_PREFIX + "/insert_spirits"+URL_SUFFIX, method={RequestMethod.GET})
	public String moveInsertSpiritsPage(Model model, HttpServletRequest request) {
		return "manage/insert_spirits";
	}
	
	@RequestMapping(value= URL_PREFIX + "/join_part_worker"+URL_SUFFIX, method={RequestMethod.GET})
	public String moveJoinPartWorkerPage(Model model, HttpServletRequest request) {
		return "manage/join_part_worker";
	}
	
	@RequestMapping(value= URL_PREFIX + "/my_job"+URL_SUFFIX, method={RequestMethod.GET})
	public String moveMyJob(Model model, HttpServletRequest request) {
		return "manage/my_job";
	}

	@RequestMapping(value= URL_PREFIX + "/job_manage"+URL_SUFFIX, method={RequestMethod.GET})
	public String moveJobManage(Model model, HttpServletRequest request) {
		return "manage/job_manage";
	}
	
}
