<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>${AppName}</title>
		<script type="text/javascript">
Ext.onReady(function() {
	// ������ʾ����
	Ext.QuickTips.init();
		var root = new Ext.tree.AsyncTreeNode({
					expanded : false,
					id : 'root'
				});
		// �����
		var lefttree = new Ext.tree.TreePanel({
					// �����첽���ڵ�����ݼ�����
					loader : new Ext.tree.TreeLoader({
						      	baseParams: {// �����ӽڵ�Ļ�������
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
		
		// �������
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
// ����tabҳ���ݴ�С
function onActiveSize() {
	// ��ȡ��ǰ���tabҳ��bodyԪ�صĿ�� (�����κο��Ԫ��)
	var w = Ext.getCmp('centerpanel').getInnerWidth();
	// ��ȡ��ǰ���tabҳ��bodyԪ�صĸ߶� (�����κο��Ԫ��)
	var h = Ext.getCmp('centerpanel').getInnerHeight();

	// ��ȡ���
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