package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	//属性驱动 接受权限的Id
	private String functionIds;
	@Autowired
	private IRoleService service;
	
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	
	/**
	 * 添加角色
	 */
	public String add(){
		service.save(model,functionIds);
		return LIST;
	}
	/**
	 * 分页查询
	 * @return
	 */
	public String pageQuery(){
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"functions","users"});
		return NONE;
	}
	/**
	 * 查询所有的角色数据
	 */
	public String listajax(){
		List<Role> list = service.findAll();
		this.java2Json(list, new String[]{"functions","roles"});
		return NONE;
	}
	
	/**
	 * 根据id查询对应的角色
	 */


	public String findRole(){
		String id = model.getId();
		Role role = service.findRoleById(id);
		this.java2Json(role, new String[]{"users","roles","functions"});
		return NONE;
	}
	
	public String edit(){
		service.edit(model,functionIds);
		return LIST;
	}
	
	public String findFunctionByRole(){
		List<Function> list = service.findFunctionByRole(model.getId());
		this.java2Json(list, new String[]{"roles","children"});
		return NONE;
	}
}
