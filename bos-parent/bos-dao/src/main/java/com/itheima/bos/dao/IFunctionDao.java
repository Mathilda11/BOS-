package com.itheima.bos.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.utils.PageBean;

public interface IFunctionDao extends IBaseDao<Function>{

	public List<Function> findFunctionListByUserId(String id);

	public List<Function> findAllMenu();

	public List<Function> findMenuByUserId(String id);


}
