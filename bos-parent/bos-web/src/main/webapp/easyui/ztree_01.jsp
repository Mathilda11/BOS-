<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<body>
	<script type="text/javascript">
	$(function(){
		var setting = {
				data:{
					simpleData:{
						enable:true
					}
				}
		}
		zNodes=[
		        {"id":"1","name":"节点一","pId":"2"},
		        {"id":"2","name":"节点二","pId":"3"},
		        {"id":"3","name":"节点三","pId":"0"},
		];
		$.fn.zTree.init($("#ztree"),setting,zNodes);
	});
	</script>
	<ul id="ztree" class="ztree"></ul>
</body>
</html>