<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ztree</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
$(function(){
	var reg = /^1[3|4|5|7|8][0-9]{9}$/;
	$.extend($.fn.validatebox.defaults.rules, { 
		telephone: { 

			validator: function(value,param){ 

			return reg.test(value);

		}, 

			message: '手机号输入有误.' 

		} 

		}); 

});
</script>
</head>
<body>
<input id="pwd" required="true"  name="pwd" type="password" class="easyui-validatebox" data-options="validType:'length[4,6]'" />
<input id="rpwd" required="true" name="rpwd" type="password" class="easyui-validatebox"  data-options="validType:'telephone'" /> 

<br>
<!-- 方式二：发送ajax请求获取json数据创建datagrid -->
<table class="easyui-datagrid" style="width:130px" data-options="url:'${pageContext.request.contextPath }/json/datagrid_data.json'"  > 

	<thead> 
	
	<tr> 
	
		<th data-options="field:'id'">编号</th> 
		
		<th data-options="field:'name'">姓名</th> 
		
		<th data-options="field:'age'">年龄</th> 
	
	</tr> 
	
	</thead> 

</table> 
<!-- 方式三：使用easyUI提供的API创建datagrid -->
<table id="dg"></table>

<script type="text/javascript">
	$(function(){

		$('#dg').datagrid({ 

		url:'${pageContext.request.contextPath }/json/datagrid_data.json', 
		
		pagination:true,
		
		rownumbers:true,
		
		singleSelect:true,

		columns:[[ 

			{field:'id',title:'编号',width:100,checkbox:true}, 
	
			{field:'name',title:'姓名',width:100}, 
	
			{field:'age',title:'年龄',width:100} 

		]],
		//定义工具栏
		toolbar:[
		         {text:'添加',iconCls:'icon-add',
		        	 //为按钮绑定单击事件
		        	 handler:function(){
		        	 	alert('add...');
		         	 }
		         },
		         {text:'删除',iconCls:'icon-remove'},
		         {text:'修改',iconCls:'icon-edit'},
		         {text:'查询',iconCls:'icon-search'}
		         ],


		}); 

	});
</script>
<input data-options="url:'${pageContext.request.contextPath }/json/combobox_data.json',
			valueField:'id',
			textField:'name'" 
			class="easyui-combobox">

</body>
</html>