package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.utils.PageBean;

public interface IRoleService {

	public void save(Role model, String functionIds);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();

	public Role findRoleById(String id);

	public void edit(Role model, String functionIds);

	public List<Function>  findFunctionByRole(String id);

}
