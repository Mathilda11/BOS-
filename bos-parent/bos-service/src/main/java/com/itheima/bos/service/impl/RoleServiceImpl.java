package com.itheima.bos.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao dao;
	@Autowired
	private IFunctionDao fdao;
	public void save(Role role, String functionIds) {
		dao.save(role);//role是持久态
		if(StringUtils.isNotBlank(functionIds)){
			String[] fIds = functionIds.split(",");
			for (String functionId : fIds) {
				//手动构造一个权限对象 设置id 对象状态为脱管状态
				Function function = new Function(functionId);
				//角色关联权限
				role.getFunctions().add(function);
			}
		}
	
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	public List<Role> findAll() {
		return dao.findAll();
	}
	@Override
	public Role findRoleById(String id) {
		return dao.findById(id);
	}
	@Override
	public void edit(Role role, String functionIds) {
		//这样并不能将对象Role变成持久态
		//dao.executeUpdate("role.update", role.getCode(),role.getName(),role.getDescription(),role.getId());
		dao.update(role);
		if(StringUtils.isNotBlank(functionIds)){
			String[] fIds = functionIds.split(",");
			Set<Function> set = new HashSet<>();
			for (String functionId : fIds) {
				//角色关联权限
				Function function = new Function(functionId);
				set.add(function);
			}
			role.setFunctions(set);
		}

	}
	@Override
	public List<Function> findFunctionByRole(String id) {
		return dao.findFunctionByRole(id);
	}

}
