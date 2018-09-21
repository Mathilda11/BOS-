package com.itheima.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.service.IWorkbillService;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class WorkbillAction extends BaseAction<Workbill> {
	@Autowired
	private IWorkbillService workbillService;
	@Autowired
	private INoticebillService noticebillService;
	
	public String save(){
		workbillService.save(model);
		return LIST;
	}
}
