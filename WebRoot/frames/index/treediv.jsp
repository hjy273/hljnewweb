<%@ page contentType="text/html; charset=GBK"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">

		<title>Ѳ��ϵͳ</title>
		<script type="text/javascript">
	Ext.onReady(function(){
		var requestConfig = {
			url :'${ctx}/DesktopAction.do?method=getTitle',
			callback : function(options,success,response){
				showTree(response.responseText);
			}
		};
		Ext.Ajax.request(requestConfig);
		function showTree(title){
			var root = new Ext.tree.AsyncTreeNode({
				text : title,
				expanded : false,
				id : 'root'
			});

			var tree = new Ext.tree.TreePanel({
				//�����첽���ڵ�����ݼ�����
				loader : new Ext.tree.TreeLoader({
					baseAttrs : {//�����ӽڵ�Ļ�������
						 cust : 'client'
					},
					dataUrl : '${ctx}/DesktopAction.do?method=getMenu'
				}),
				width : 180,
				height :550,
				border : false,
				autoScroll : true,
				renderTo : 'tree-div',
				root : root,
				rootVisible : true
			});

			tree.on('click', function (node){
		      	if(node.isLeaf()){
		      	window.parent.parent.frames.mainFrame.frames.location.href= node.attributes.link;
		        	return true;
		     	}
		    });
	    }
	});
</script>

	</head>

	<body id="tree-div" class="left_border">
	
	</body>
</html>