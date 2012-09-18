<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<link rel="stylesheet" type="text/css" href="${ctx}/js/extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="${ctx}/js/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/js/extjs/ext-all.js"></script>

<style type="text/css">
	.fileIcon {background-image : url(${ctx}/js/extjs/resources/images/default/tree/folder.gif) !important;}
</style>
<script type="text/javascript">
	Ext.onReady(function(){
		Ext.BLANK_IMAGE_URL = '${ctx}/js/extjs/resources/images/default/s.gif';
		var Toolbar = new Ext.Toolbar({
			applyTo : 'toolbar'
		});
		var Menu = new Ext.menu.Menu({
			items : [
				{
					text : '一级菜单',
					iconCls : 'fileIcon',
					handler : addFirstMenu
				},
				{
					text : '二级菜单',
					iconCls : 'fileIcon',
					handler : addSecondMenu
				},
				{
					text : '三级菜单',
					iconCls : 'fileIcon',
					handler : addThirdMenu
				}
			]
		});
		Toolbar.add(
			{text : '添加菜单 (请选择) ' , menu : Menu}
		);
		function addFirstMenu(){
			document.all.menu.src = "baseinfo/menu/addFirstMenu.jsp";
		}
		function addSecondMenu(){
			document.all.menu.src = "baseinfo/menu/addSecondMenu.jsp";
		}
		function addThirdMenu(){
			document.all.menu.src = "baseinfo/menu/addThirdMenu.jsp";
		}
	});
</script>

<body>
	<table width="100%" id="tab">
		<tr>
			<td><div id="toolbar"></div></td>
		</tr>
		<tr>
			<td>
				<iframe src="" id="menu" frameborder="no" border="1" marginwidth="0" marginheight="0" width=100% height=600></iframe>
			</td>
		</tr>
	</table>
</body>