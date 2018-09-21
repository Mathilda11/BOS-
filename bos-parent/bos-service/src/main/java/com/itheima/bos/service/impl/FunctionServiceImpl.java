package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
	@Autowired
	private IFunctionDao dao;
	
	public List<Function> findAll() {
		return dao.findAll();
	}
	
	public void save(Function model) {
		Function parentFunction = model.getParentFunction();
		if(parentFunction!=null && parentFunction.getId().equals("")){
			model.setParentFunction(null);
		}
		dao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	/**
	 * 个根据当前登录人查询对应的菜单
	 */
	public List<Function> findMenu() {
		List<Function> list = null;
		User user = BOSUtils.getLoginUser();
		if(user.getUsername().equals("admin")){
			//超级管理员查询所有菜单
			list = dao.findAllMenu();
		}else{
			//其他用户根据用户id查询菜单
			list = dao.findMenuByUserId(user.getId());
		}
		return list;
	}

}
