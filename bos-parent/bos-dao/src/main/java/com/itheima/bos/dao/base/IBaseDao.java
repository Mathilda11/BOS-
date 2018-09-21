package com.itheima.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.utils.PageBean;

public interface IBaseDao<T> {
	    public void save(T entity);
		public void delete(T entity);
		public void update(T entity);
		public void saveOrUpdate(T entity);
		public T findById(Serializable id);
		public List<T> findAll();
		public List<T> findByCriteria(DetachedCriteria detachedCriteria);
		public void executeUpdate(String queryName,Object ...objects);
		//操作PageBean这样对象 属性里就有值了 不需要返回值
		public void pageQuery(PageBean pageBean);
}

