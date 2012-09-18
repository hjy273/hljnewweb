<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxcommon.js"></script>
		<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxtree.js"></script>
		<script language="JavaScript" src="${ctx}/js/dhtmlxTree/ext/dhtmlxtree_json.js"></script>
		<link rel="STYLESHEET" type="text/css" href="${ctx}/js/dhtmlxTree/dhtmlxtree.css">
		<script language="javascript" type="">
			function getMenus(){
				return tree.getAllChecked();
			}
		</script>
	</head>
	<body>
		<div id="tree" />
		<script>
			$("tree").innerHTML="";
			var tree=new dhtmlXTreeObject("tree","100%","100%",0);
			tree.setImagePath("${ctx}/js/dhtmlxTree/imgs/csh_bluebooks/");
			tree.enableCheckBoxes(true);
			tree.enableThreeStateCheckboxes(true);
			eval("var json=<%=request.getAttribute("queryresult") %>");
			tree.loadJSONObject(json);
			parent.document.getElementById('welcomeStr1').style.display = "none";
		</script>
	</body>
</html>