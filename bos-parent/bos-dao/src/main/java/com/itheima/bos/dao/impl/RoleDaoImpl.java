package com.itheima.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Role;
import com.itheima.bos.utils.PageBean;
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao {

	public List<Function> findFunctionByRole(String id) {
		String hql = "select r.functions from Role r where r.id = ?";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql,id);
		return list;
	}


}
