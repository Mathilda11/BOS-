package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;

/**
 * 定区管理
 * @author 54060
 *
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	//属性驱动 接受多个分区id
	private String[] subareaid;

	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	
	@Autowired
	private IDecidedzoneService decidedzoneService;
	/**
	 * 添加定区
	 */
	public String add(){
		decidedzoneService.save(model,subareaid);
		return LIST;
	}
	
	/**
	 * 分页查询方法
	 */
	public String pageQuery() throws IOException{
		decidedzoneService.pageQuery(pageBean);
		//前三个是pageBean中不转的
		//在把取派员staff转json时 不能转decidedzones属性了
		//在把decidedzone转json时 不能转subareas了 避免死循环
		this.java2Json(pageBean, new String[]{"currentPage","detachedCriteria",
						"pageSize","subareas","decidedzones"});
		return NONE;
	}
	//注入crm代理对象
	@Autowired
	private ICustomerService proxy;
	public String findListNotAssociation(){
		List<Customer> list = proxy.findListNotAssociation();
		this.java2Json(list, new String[]{});
		return NONE;
	}
	public String findListHasAssociation(){
		//定区id封装到model里了
		String id = model.getId();
		List<Customer> list = proxy.findListHasAssociation(id);
		this.java2Json(list, new String[]{});
		return NONE;
	}
	//属性驱动，接收页面提交的多个客户id
	private List<Integer> customerIds;
	
	/**
	 * 远程调用crm服务，将客户关联到定区
	 */
	public String assigncustomerstodecidedzone(){
		//定区id decidezoneAction 所以id一定在model里
		proxy.assigncustomerstodecidedzone(model.getId(), customerIds);
		return LIST;
	}

	public List<Integer> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
}

