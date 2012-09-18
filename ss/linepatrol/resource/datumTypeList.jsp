<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
		<script language="javascript" src="/WebApp/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="/WebApp/js/validation/effects.js" type=""></script>
	<script language="javascript" src="/WebApp/js/validation/validation_cn.js" type=""></script>
	<link href="/WebApp/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	
	<script type='text/javascript' src='/WebApp/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='/WebApp/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='/WebApp/js/extjs/resources/css/ext-all.css' />
	
	<script type='text/javascript' src='/WebApp/linepatrol/js/change_style.js'></script>
	<link rel="stylesheet" href="/WebApp/css/screen.css" type="text/css" media="screen, print"/>
	
	<script type='text/javascript'>
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:500,
				height:300, 
				resizable:true,
				closeAction : 'close',
				modal:true,
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function close(){
		window.location.href ='/WebApp/datumAction.do?method=typelist';
			win.close();
			
		}
		function addType(){
			showWin('/WebApp/linepatrol/resource/datumTypeAdd.jsp');
		}
		function update(id){
			window.location.href ='/WebApp/datumAction.do?method=upType&id='+id;
		}
		function del(id){
			window.location.href = '/WebApp/datumAction.do?method=delType&id='+id;
		}
	</script>
</head>
<body>
	<template:titile value="资料分类列表" />			
					
	<display:table name="sessionScope.typelist" id="row"  pagesize="10" export="false" sort="list" >
	<display:column media="html" title="资料分类id号" sortable="true" >
			<apptag:assorciateAttr table="datum_type" label="id" key="id" keyValue="${row.id}"/>
		</display:column>
		<display:column media="html" title="资料分类名称" sortable="true">
			<apptag:assorciateAttr table="datum_type" label="name" key="id" keyValue="${row.id}" />
		</display:column>
		<display:column media="html" title="操作" sortable="true">
			<a href="javascript:update('${row.id}');">修改</a>|
			<a href="javascript:del('${row.id}');">删除</a>
		</display:column>			
	</display:table>
	<center><input type="button" value="添加" onclick="addType()" /></center>
</body>
</html>