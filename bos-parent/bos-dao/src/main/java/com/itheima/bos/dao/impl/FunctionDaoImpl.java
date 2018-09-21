package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Function;
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao {
	public List<Function> findAll(){
		String hql = "from Function f where f.parentFunction is null";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}
	//根据用户id查询对应的权限
	public List<Function> findFunctionListByUserId(String userId) {
		//为了只包装成权限对象
		String hql = "select distinct f from Function f left outer join f.roles r left outer join r.users u where u.id=?";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}
	//查询所有菜单
	public List<Function> findAllMenu() {
		String hql = "from Function f where f.generatemenu = '1' order by f.zindex desc";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}
	//根据用户id查询菜单
	public List<Function> findMenuByUserId(String userId) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles"
				+ " r LEFT OUTER JOIN r.users u WHERE u.id = ? AND f.generatemenu = '1' "
				+ "ORDER BY f.zindex DESC";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}
}
