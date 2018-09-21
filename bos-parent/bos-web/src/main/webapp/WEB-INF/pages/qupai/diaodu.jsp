<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人工调度</title>
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
<script type="text/javascript">
	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		$("#grid").datagrid({
			singleSelect : true,
			rownumbers : true,
			toolbar : [ {
				id : 'diaodu',
				text : '人工调度',
				iconCls : 'icon-edit',
 				handler : function() {
 					//getSelected 返回第一个被选中的行或如果没有选中的行则返回null
 					var row = $("#grid").datagrid("getSelections");
 					if(row.length!=1){
 						//没有选中记录 弹出提出
 						$.messager.alert("提示信息","请选择需要调度的业务通知单！","warning");
 					}else{
	 					// 弹出窗口
	 					$("#diaoduWindow").window('open');
	 					$("input[name='noticebill.id']").val(row[0].id);
	 					$("#noticebillIdView").html(row[0].id);
 					}
				}
			} ],
			url : '${pageContext.request.contextPath}/noticebillAction_findNoassociations.action',
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 200
			}, {
				field : 'delegater',
				title : '联系人',
				width : 100
			}, {
				field : 'telephone',
				title : '电话',
				width : 100
			}, {
				field : 'pickaddress',
				title : '取件地址',
				width : 150
			}, {
				field : 'product',
				title : '商品名称',
				width : 100
			}, {
				field : 'pickdate',
				title : '取件日期',
				width : 100,
				formatter:function(val,row,index){
					if(val==null){
						return "";
					}
					var year=parseInt(val.year)+1900;  
				    var month=(parseInt(val.month)+1);  
				    month=month>9?month:('0'+month);  
				    var date=parseInt(val.date);  
				    date=date>9?date:('0'+date);  
				    var time=year+'-'+month+'-'+date;
				    return time; 
				}
			} ] ],
		});

		// 点击保存按钮，为通知单 进行分单 --- 生成工单
		$("#save").click(function() {
			var v = $("#diaoduForm").form("validate");
			if(v){
				$("#diaoduForm").submit();
			}
		});
	});

	
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<table id="grid"></table>
	</div>
	<div class="easyui-window" title="人工调度" id="diaoduWindow" closed="true"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:100px;left:200px;width: 500px; height: 300px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="diaoduForm" method="post" action="${pageContext.request.contextPath}/workbillAction_save.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">人工调度</td>
					</tr>
					<tr>
						<td>通知单编号</td>
						<td><input type="hidden" name="noticebill.id" id="noticebillId" /> <span
							id="noticebillIdView">
							</span>
					</tr>
					<tr>
						<td>选择取派员</td>
						<td><input class="easyui-combobox" required="true"
							name="staff.id"
							data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/staffAction_listajax.action'" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>