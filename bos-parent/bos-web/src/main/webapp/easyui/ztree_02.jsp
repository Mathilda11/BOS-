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

</head>
		
	<div title="面板四">
		<!-- 展示ztree效果 :发送ajax请求获取简单json数据构造ztree-->
		<ul id="ztree3" class="ztree"></ul>
		<script type="text/javascript">
			$(function(){
				//页面加载完成后，执行这段代码----动态创建ztree
				var setting3 = {
						data: {
							simpleData: {
								enable: true//使用简单json数据构造ztree节点
							}
						},
						callback: {
							//为ztree节点绑定单击事件
							onClick: function(event, treeId, treeNode){
								if(treeNode.page != undefined){
									//判断选项卡是否已经存在
									var e = $("#mytabs").tabs("exists",treeNode.name);
									if(e){
										//已经存在，选中
										$("#mytabs").tabs("select",treeNode.name);
									}else{
										//动态添加一个选项卡
										$("#mytabs").tabs("add",{
											title:treeNode.name,
											closable:true,
											content:'<iframe frameborder="0" height="100%" width="100%" src="'+treeNode.page+'"></iframe>'
										});
									}
								}
							}
						}
				};
						
				//发送ajax请求，获取json数据
				//jQuery提供 的ajax方法：ajax、post、get、load、getJSON、getScript
				var url = "${pageContext.request.contextPath}/json/menu.json";
				$.post(url,{},function(data){
					//调用API初始化ztree
					$.fn.zTree.init($("#ztree3"), setting3, data);
				},'json');
			});
		</script>
	</div>
	<div data-options="region:'center'">
		<!-- 制作一个tabs选项卡面板 -->
		<div id="mytabs" class="easyui-tabs" data-options="fit:true">
			<!-- 使用子div表示每个面板 -->
			<div data-options="iconCls:'icon-cut'" title="面板一">1111</div>
			<div data-options="closable:true" title="面板二">2222</div>
			<div title="面板三">3333</div>
		</div>
	</div>
</body>
</html>