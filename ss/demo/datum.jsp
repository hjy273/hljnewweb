<%@include file="/common/header.jsp"%>
<html>
<head>
	<title>中继段</title>
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type="text/javascript">
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
			win.close();
		}
		function add(){
			window.location.href = '${ctx}/linepatrol/resource/datumAdd.jsp';
		} 
		function edit(){
			window.location.href = '${ctx}/datumAction.do?method=list';
		}
	</script>
</head>
<body>
		<input type="button" value="更新资料" onclick="showWin('/WebApp/trunkAction.do?method=updateTrunk&id=000000009311&type=cable')">
	<input type="button" value="审核资料" onclick="showWin('/WebApp/trunkAction.do?method=updateTrunkApprove&id=000000009311&type=cable')">
	<input type="button" value="查看资料" onclick="showWin('/WebApp/trunkAction.do?method=updateTrunkView&id=000000009311&type=cable')">
	<input type="button" value="添加维护资料" onclick="add()">
	<input type="button" value="维护资料更新或审核(用户不同)" onclick="edit()">
</body>
</html>