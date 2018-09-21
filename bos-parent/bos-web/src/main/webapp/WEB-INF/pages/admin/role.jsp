<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>	
<script type="text/javascript">
	$(function(){
		// 数据表格属性
		$("#grid").datagrid({
			toolbar : [
				{
					id : 'add',
					text : '添加角色',
					iconCls : 'icon-add',
					handler : function(){
						location.href='${pageContext.request.contextPath}/page_admin_role_add.action';
					}
				},
				{
					id : 'edit',
					text : '修改角色',
					iconCls : 'icon-edit',
					handler : function(){
						var rows = $("#grid").datagrid("getSelections");
	 					if(rows.length!=1){
	 						//没有选中记录 弹出提出
	 						$.messager.alert("提示信息","请选择一个需要修改的角色！","warning");
	 					}else{
	 						var id = rows[0].id;
	 						$("#editRoleWindow").window('open');
	 						
	 					// 授权树初始化
	 						var setting = {
	 							data : {
	 								key : {
	 									title : "t"
	 								},
	 								simpleData : {
	 									enable : true
	 								}
	 							},
	 							check : {
	 								enable : true//使用ztree的勾选效果
	 							}
	 						};
	 						$.ajax({
	 							url : '${pageContext.request.contextPath}/functionAction_listajax.action',
	 							type : 'POST',
	 							dataType : 'json',
	 							success : function(data) {
	 								$.fn.zTree.init($("#functionTree"), setting, data);
	 								$.post('roleAction_findFunctionByRole.action',{"id":id},function(value){
	 									// 获取树对象
	 									// 根据 treeId 获取 zTree 对象的方法 返回json格式
	 								    var treeObj = $.fn.zTree.getZTreeObj("functionTree");
	 								    /** 获取所有树节点 */
	 								    //将 zTree 使用的标准 JSON 嵌套格式的数据转换为简单 Array 格式
	 								    var nodes = treeObj.transformToArray(treeObj.getNodes());
	 									for(var i=0, length=value.length;i<length;i++){
	 								        // 遍历树节点设置树节点为选中
	 								        for (var k = 0, length_1 = nodes.length; k < length_1; k++) {
	 								            if (value[i].name == nodes[k].name) {
	 								            	//建立 treeNode 数据时设置 treeNode.checked = true 可以让节点的输入框默认为勾选状态
	 								               nodes[k].checked = true;
	 								            	//更新某节点数据，主要用于该节点显示属性的更新。
	 								　　　　　　　　　 　treeObj.updateNode(nodes[k],true);
	 								            }
	 								        }
	 									}
	 								});
	 								
	 							},
	 							error : function(msg) {
	 								alert('树加载异常!');
	 							}
	 						});
	 						
	 						$.post('roleAction_findRole.action',{"id":id},function(data){
	 							if(data != null){
	 								//查询到了客户信息，可以进行页面回显
	 								var id= data.id;
	 								var name = data.name;
	 								var code = data.code;
	 								var description = data.description;
	 								$("input[name=id]").val(id);
	 								$("input[name=name]").val(name);
	 								$("input[name=code]").val(code);
	 								$("textarea[name=description]").val(description);
	 							}else{
	 								//没有查询到客户信息，不能进行页面回显
	 								$("input[name=id]").val('');
	 								$("input[name=name]").val('');
	 								$("input[name=code]").val('');
	 								$("textarea[name=description]").val(description);
	 							}
	 						});
	 						
	 					// 点击保存
	 						$('#save').click(function(){
	 							//表单校验
	 							var v = $("#roleForm").form("validate");
	 							if(v){
	 									//根据ztree的id获取ztree对象
	 									var treeObj = $.fn.zTree.getZTreeObj("functionTree");
	 									//获取ztree上选中的节点，返回数组对象
	 									var nodes = treeObj.getCheckedNodes(true);
	 									var array = new Array();
	 									for(var i=0;i<nodes.length;i++){
	 										var id = nodes[i].id;
	 										array.push(id);
	 									}
	 									var functionIds = array.join(",");
	 									//为隐藏域赋值（权限的id拼接成的字符串）
	 									$("input[name=functionIds]").val(functionIds);
	 									$("#roleForm").submit();
	 							}
	 						});

	 					}
					}	
					}
			],
			url : 'roleAction_pageQuery.action',
			pagination:true,
			fit:true,
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 250
				},
				{
					field : 'name',
					title : '名称',
					width : 200
				}, 
				{
					field : 'description',
					title : '描述',
					width : 200
				} 
			]]
		});
		
		$('#editRoleWindow').window({
	        title: '修改角色',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
	
		
		
	
	});
</script>	
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table id="grid"></table>
	</div>
	<div class="easyui-window" title="修改角色" id="editRoleWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
			<div style="overflow:hidden;" split="false" border="false" >
			<form id="roleForm" method="post" action="roleAction_edit.action">
				<input type="hidden" name="id" >
				<input type="hidden" name="functionIds">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">角色信息</td>
					</tr>
					<tr>
						<td width="200">关键字</td>
						<td>
							<input type="text" name="code" class="easyui-validatebox" data-options="required:true" />						
						</td>
					</tr>
					<tr>
						<td>名称</td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td>描述</td>
						<td>
							<textarea name="description" rows="4" cols="60"></textarea>
						</td>
					</tr>
					<tr>
						<td>授权</td>
						<td>
							<ul id="functionTree" class="ztree"></ul>
						</td>
					</tr>
					</table>
			</form>
		</div>
		</div>
</body>
</html>