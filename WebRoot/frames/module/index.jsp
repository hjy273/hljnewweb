<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>${AppName}</title>
		<script type="text/javascript">
Ext.onReady(function() {
	// 开启提示功能
	Ext.QuickTips.init();
		var root = new Ext.tree.AsyncTreeNode({
					expanded : false,
					id : 'root'
				});
		// 树左侧
		var lefttree = new Ext.tree.TreePanel({
					// 设置异步树节点的数据加载器
					loader : new Ext.tree.TreeLoader({
						      	baseParams: {// 设置子节点的基本属性
									type : '${type}'
								},
								dataUrl : '${ctx}/DesktopAction.do?method=getMenu'
							}),
					width : 180,
					border : false,
					autoScroll : true,
					title : '${title}',
					root : root,
					rootVisible : false,
					collapsible : true,
					region : 'west',
					listeners : {
						'expand' : function(_nowCmp) {
							onActiveSize();
						},
						'collapse' : function(_nowCmp) {
							onActiveSize();
						},
						'click' : function(node) {
							if (node.isLeaf()) {
								Ext.getDom('desktopmain').src = node.attributes.hrefurl;
								return true;
							}
						}
					}
				});
		url="${url}";
		if(url==""){
			url="${ctx}/DesktopAction.do?method=loadinfo&type=<%=request.getParameter("type")%>";
			
		}
		
		// 中心面板
		var centerpanel = new Ext.Panel({
			region : 'center',
			id:'centerpanel',
			html : "<iframe id='desktopmain' src='"+url+"' id='maintext' width='100%' height='99%' scrolling='auto' frameborder='no' style='overflow-x: hidden; overflow-y: auto;'></iframe>"
		});
		var viewport = new Ext.Viewport({
					layout : 'border',

					items : [lefttree, centerpanel]
				});
});
// 调整tab页内容大小
function onActiveSize() {
	// 获取当前活动的tab页的body元素的宽度 (不含任何框架元素)
	var w = Ext.getCmp('centerpanel').getInnerWidth();
	// 获取当前活动的tab页的body元素的高度 (不含任何框架元素)
	var h = Ext.getCmp('centerpanel').getInnerHeight();

	// 获取组件
	var submain = Ext.getDom('desktopmain');

	if (submain) {
		submain.width=w;
	}
};
</script>
	</head>
	<body>
		<!--  -->
	<frameset cols="185,*" frameborder="NO" border="0" framespacing="0" style="overflow-x: hidden; overflow-y: auto;">

	</frameset>
	</body>

</html>