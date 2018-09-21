package com.itheima.bos.web.action;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 业务通知单管理
 */
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;
@Controller
@Scope("prototype")
public class noticebillAction extends BaseAction<Noticebill> {
	@Autowired
	private ICustomerService customerService;

	/**
	 * 远程调用crm服务 根据手机号查询客户信息
	 */
	public String findCustomerByTelephone(){
		String telephone = model.getTelephone();
		Customer customer = customerService.findCustomerByTelephone(telephone);
		this.java2Json(customer, new String[]{});
		return NONE;
	}
	@Autowired
	private INoticebillService noticebillService;
	/**
	 * 保存一个业务通知单 并尝试自动分单
	 * @return
	 */
	public String add(){
		noticebillService.save(model);
		return "noticebill_add";
	}
	public String findNoassociations(){
		List<Noticebill> list = noticebillService.findNoassociations();
		this.java2Json(list, new String[]{"user","staff","workbills"});
		return NONE;
	}
}
