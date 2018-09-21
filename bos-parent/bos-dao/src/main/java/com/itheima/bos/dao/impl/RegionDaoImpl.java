package com.itheima.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {
	/**
	 * 根据q参数进行模糊查询
	 */
	public List<Region> findListByQ(String q) {
		String hql = "from Region r where r.shortcode like ? or r.citycode like ? or r.province like ? or r.city like ? or r.district like ?";
		List<Region> list = (List<Region>) this.getHibernateTemplate().find(hql,"%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
		return list;
	}

}
