package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 权限管理
 * @author 54060
 *
 */
@Controller
@Scope("prototype")
public class functionAction extends BaseAction<Function> {
	@Autowired
	private IFunctionService service;
	public String listajax(){
		List<Function> list = service.findAll();
		System.out.println(list);
		this.java2Json(list, new String[]{"parentFunction","roles"});
		return NONE;
	}
	
	public String add(){
		service.save(model);
		return LIST;
	}
	public String pageQuery(){
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	/**
	 * 根据当前登录人查询菜单
	 * @return
	 */
	public String findMenu(){
		List<Function> list = service.findMenu();
		this.java2Json(list, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
}
