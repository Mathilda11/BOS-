package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;

public interface IRoleDao extends IBaseDao<Role> {

	List<Function>  findFunctionByRole(String id);

	
}
