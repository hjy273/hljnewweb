<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>

<html>
	<head>
		<script type="text/javascript" language="javascript">
function valiSub() {

	if(document.getElementById("vo.templateName").value=='')
	{
		alert("模板名称不能为空！");
		return false;
	}

    var rds = "";//资源字符ID
    var rtype="";//资源类型
	var checkedNodes = patrolresoucetree.getChecked();
	for (var i = 0; i < checkedNodes.length; i++) {
		if (checkedNodes[i].isLeaf()) {
			rds +=","+checkedNodes[i].attributes.id;
		}
	}
	if (rds != "") {
		rds = rds.substring(1, rds.length);
	}
	if(rds.length==0)
	{
		alert("计划模板项不能为空！");
		return false;
	}
	document.wplantemplate.voitems.value = rds;
	document.wplantemplate.submit();
}


//计划类型改变时触发
function change() {
	var obj = document.getElementsByName("plantype");
	for (var i = 0; i < obj.length; i++) {
		if (obj[i].checked == true) {
			setPlanName(obj[i].value);
		}
	}
}
//维护组下拉组件
var patrolgroupcombotree;
//资源树组件
var patrolresoucetree;
Ext.onReady(function() {
	resoucetreeload = new Ext.tree.TreeLoader({
				dataUrl : '${ctx}/patrolManager/wplanTemplateAction_getPatrolItemTreddDate.jspx',
				baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } ,
				baseParams : {
		 	   		businessType : '${vo.businessType }',
		 	   		templateId : '${vo.id }',
		 	   		flag:'edit'
				}
			});
	patrolresoucetree = new Ext.tree.TreePanel({
				id : 'check_id',
				checkModel: 'cascade',   //对树的级联多选
				applyTo : 'combotree_patrolresourcediv',
				autoScroll : true,
				margins : '0 2 0 0',
				width: 600,
				height : 300,
				root : new Ext.tree.AsyncTreeNode({
							id : "root",
							text : "所有巡检项",
							iconCls : 'treeroot-icon',
							expanded : true,
							loader : resoucetreeload
						})
			});
			
<%--		resoucetreeload.baseParams = {--%>
<%--		 	   businessType : '${vo.businessType }',--%>
<%--		 	   flag:'edit'--%>
<%--		};--%>
<%--		patrolresoucetree.getRootNode().reload();--%>
});


function businessTypeChange(obj){
		resoucetreeload.baseParams = {
		 	   businessType : ''+obj,
		 	   templateId : '${vo.id }',
		 	   flag:'edit'
		};
		patrolresoucetree.getRootNode().reload();
}




    </script>
		<title>添加巡检计划信息</title>
	</head>
	<body>
		<template:titile value="修改计划模板信息" />
		<s:form action="/patrolManager/wplanTemplateAction_update.jspx" id="wplantemplate" name="wplantemplate" method="post">
		    <input type="hidden" id="vo.id" name="vo.id" value="${vo.id }"/>
		    <input type="hidden" id="vo.id" name="vo.businessType" value="${vo.businessType }"/>
			<template:formTable >
				<template:formTr name="专业">
					<apptag:dynLabel dicType="businesstype" objType="dic" id="${vo.businessType }"></apptag:dynLabel>
				</template:formTr>
				<template:formTr name="模板名称">
					<input type="text" maxlength="10" style="width: 220px;" id="vo.templateName" name="vo.templateName" value="${vo.templateName }"/><span style="color:red">*</span>
				</template:formTr>
				<template:formTr name="备注">
					<textarea rows="3" nmae="vo.remark" value="${vo.remark }" style="width: 220px;"></textarea>
				</template:formTr>

				<template:formTr name="计划模板项">
					<div id="combotree_patrolresourcediv" style="width: 220px;"></div>
					<input type="hidden" name="vo.items" id="voitems">
				</template:formTr>

				<template:formSubmit>
					<td>
						<input type="button" onclick="valiSub()" id="button" class="button" value="修改">
					</td>
					<td>
					</td>
					<td>
						<input type="button" class="button" onclick="history.back()" value="返回" >
					</td>
				</template:formSubmit>
			</template:formTable>
		</s:form>
	</body>
</html>


