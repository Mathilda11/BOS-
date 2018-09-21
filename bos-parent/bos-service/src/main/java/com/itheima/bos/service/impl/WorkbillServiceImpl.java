package com.itheima.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IWorkbillDao;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.service.IWorkbillService;
@Service
@Transactional
public class WorkbillServiceImpl implements IWorkbillService {
	@Autowired
	private IWorkbillDao workbillDao;
	public void save(Workbill model) {
		model.setAttachbilltimes(0);
		model.setBuildtime(new Timestamp(System.currentTimeMillis()));
		model.setPickstate(Workbill.PICKSTATE_NO);//取件状态
		model.setRemark(model.getRemark());//备注信息
		model.setType(Workbill.TYPE_1);//工单状态
		workbillDao.save(model);
	}

}
