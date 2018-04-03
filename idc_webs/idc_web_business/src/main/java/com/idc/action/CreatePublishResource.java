package com.idc.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import system.data.page.PageBean;
import system.data.supper.action.BaseController;

import com.idc.model.TestResouces;
import com.idc.service.TestResoucesService;
@Controller
@RequestMapping("/createPublishResource")
public class CreatePublishResource  extends BaseController{
	@Autowired
	private TestResoucesService resoucesService;
	/**
	 * query list of CreatePublishResource
	 */
	@ResponseBody
	@RequestMapping(value = "/listPage.do")
	public PageBean<TestResouces> resouceCtlAction_ResBuildTable(PageBean<TestResouces> page,TestResouces testResouces) throws Exception {
		resoucesService.queryListPage(page,testResouces);
		return page;
	}
	//弹出框
	@RequestMapping("menuSearch.do")
	public String menuSearch(TestResouces testResouces) {
		//query by id
		testResouces = resoucesService.getModelById(testResouces.getId());
		model.addAttribute("testResouces", testResouces);
		return "bussness/resouce_process";
	}
	
	@RequestMapping("menuEdit.do")
	public String menuEdit(TestResouces testResouces) {
		//query by id
		testResouces = resoucesService.getModelById(testResouces.getId());
		model.addAttribute("testResouces", testResouces);
		return "bussness/menuEdit";
	}
	
	
	
	
}
