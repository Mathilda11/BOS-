package com.itheima.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

@Repository
public class SubareaDaoImpl extends BaseDaoImpl<Subarea> implements ISubareaDao {

	public List<Object> findSubareasGroupByProvince() {
		String hql = "select r.province, count(*) from Subarea s left outer join s.region r group by r.province";
		return (List<Object>) this.getHibernateTemplate().find(hql);
	}


}
