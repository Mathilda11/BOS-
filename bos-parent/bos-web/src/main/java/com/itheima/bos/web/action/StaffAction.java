package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;
import com.itheima.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	@Autowired
	private IStaffService staffService;
	/**
	 * 添加取派员
	 */
	public String add(){
		staffService.save(model);
		return LIST;
	}
	
	/**
	 * 分页查询方法
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		staffService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","decidedzones"});
		return NONE;//不需要调页面 返回数据
	}
	
	//属性驱动，接收页面提交的ids参数
	private String ids;
	
	/**
	 * 取派员批量删除
	 */
	@RequiresPermissions("staff-delete")//执行该方法 需要当前用户具有staff-delete权限
	public String deleteBatch(){
		staffService.deleteBatch(ids);
		return LIST;
	}
	public String restoreBatch(){
		staffService.restoreBatch(ids);
		return LIST;
	}
	/**
	 * 修改取派员信息
	 * @return
	 */
	public String edit(){
		//先查询数据库 根据id查询原始数据
		Staff staff = staffService.findById(model.getId());
		//使用页面提交的数据进行覆盖
		//关联表 不要直接excuteupdate对象
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		staffService.update(staff);
		return LIST;
	}
	
	/**
	 * 查询所有未删除的取派员，返回json
	 */
	public String listajax(){
		List<Staff> list = staffService.findListNotDelete();
		this.java2Json(list, new String[]{"decidedzones"});
		return NONE;
	}


	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
