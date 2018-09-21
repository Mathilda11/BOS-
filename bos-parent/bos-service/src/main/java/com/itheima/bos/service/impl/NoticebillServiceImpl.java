package com.itheima.bos.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.INoticebillDao;
import com.itheima.bos.dao.IWorkbillDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.crm.ICustomerService;

import net.sf.ehcache.search.expression.Not;
@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService {
	@Autowired
	private INoticebillDao noticebillDao;
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private IWorkbillDao workbillDao;
	@Autowired
	private ICustomerService customerService;
	/**
	 * 保存业务通知单
	 * 尝试自动分单
	 */
	public void save(Noticebill model) {
		//设置当前登录用户
		User user = BOSUtils.getLoginUser();
		model.setUser(user);
		noticebillDao.save(model);
		
		//获取客户的取件地址
		String pickaddress = model.getPickaddress();
		//远程调用crm服务 根据取件地址查询定区id
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
		if(decidedzoneId!=null){
			//查询到定区id 可以完成自动分单
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);
			//设置分单类型为自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//为取派员产生一个工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//创建时间 当前系统时间
			workbill.setNoticebill(model);//工单关联页面通知单
			workbill.setPickstate(Workbill.PICKSTATE_NO);//取件状态
			workbill.setRemark(model.getRemark());//备注信息
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType(Workbill.TYPE_1);//工单状态
			workbillDao.save(workbill);
			
			//调用短信平台 发送短信
		
			
		}else{
			//没有查询到定区id 不能完成自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
	}
	
	@Override
	public List<Noticebill> findNoassociations() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Noticebill.class);
		detachedCriteria.add(Restrictions.eq("ordertype","人工分单"));
		List<Noticebill> list = noticebillDao.findByCriteria(detachedCriteria);
		List<Noticebill> nlist = new ArrayList<Noticebill>();
		DetachedCriteria detachedCriteria1 = null;
		for (Noticebill noticebill : list) {
			detachedCriteria1 = DetachedCriteria.forClass(Workbill.class);
			detachedCriteria1.add(Restrictions.eq("noticebill.id", noticebill.getId()));
			List<Workbill> workbillList = workbillDao.findByCriteria(detachedCriteria1);
			if(workbillList!=null && workbillList.size()>0){
				continue;
			}
			nlist.add(noticebill);
		}
		return nlist;
	}


}
