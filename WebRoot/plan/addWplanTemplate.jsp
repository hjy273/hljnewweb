<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>


<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>

<html>
	<head>
		<script type="text/javascript" language="javascript">
function valiSub() {


	if(document.getElementById("vo.templateName").value=='')
	{
		alert("ģ�����Ʋ���Ϊ�գ�");
		return false;
	}

    var rds = "";//��Դ�ַ�ID
    var rtype="";//��Դ����
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
		alert("�ƻ�ģ�����Ϊ�գ�");
		return false;
	}
	document.wplantemplate.voitems.value = rds;
	document.wplantemplate.submit();
}


//�ƻ����͸ı�ʱ����
function change() {
	var obj = document.getElementsByName("plantype");
	for (var i = 0; i < obj.length; i++) {
		if (obj[i].checked == true) {
			setPlanName(obj[i].value);
		}
	}
}
//ά�����������
var patrolgroupcombotree;
//��Դ�����
var patrolresoucetree;
Ext.onReady(function() {
	resoucetreeload = new Ext.tree.TreeLoader({
				dataUrl : '${ctx}/patrolManager/wplanTemplateAction_getPatrolItemTreddDate.jspx',
				baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } ,
				baseParams :{
		 	   	businessType : '${businessType }',
		 	   		flag:'add'
				}
			});
	patrolresoucetree = new Ext.tree.TreePanel({
				id : 'check_id',
				checkModel: 'cascade',   //�����ļ�����ѡ
				applyTo : 'combotree_patrolresourcediv',
				autoScroll : true,
				margins : '0 2 0 0',
				width: 600,
				height : 300,
				root : new Ext.tree.AsyncTreeNode({
							id : "root",
							text : "����Ѳ����",
							iconCls : 'treeroot-icon',
							expanded : true,
							loader : resoucetreeload
						})
			});
});


<%--function businessTypeChange(obj){--%>
<%--		resoucetreeload.baseParams = {--%>
<%--		 	   businessType : '${businessType }',--%>
<%--		 	   flag:'add'--%>
<%--		};--%>
<%--		patrolresoucetree.getRootNode().reload();--%>
<%--}--%>


    </script>
		<title>���Ѳ��ƻ���Ϣ</title>
	</head>
	<body>
		<template:titile value="��Ӽƻ�ģ����Ϣ" />
		<s:form action="/patrolManager/wplanTemplateAction_save.jspx" id="wplantemplate" name="wplantemplate" method="post">
			<template:formTable >
				<template:formTr name="רҵ">
				    <input type="hidden" name="vo.businessType" value="${businessType }"/>
					<apptag:dynLabel dicType="businesstype" objType="dic" id="${businessType }"></apptag:dynLabel>
				</template:formTr>
				<template:formTr name="ģ������">
					<input type="text" id="vo.templateName" maxlength="10" style="width: 220px;" name="vo.templateName"/><span style="color:red">*</span>
				</template:formTr>
				<template:formTr name="��ע">
					<textarea rows="3" name="vo.remark"  style="width: 220px;"></textarea>
				</template:formTr>

				<template:formTr name="�ƻ�ģ����">
					<div id="combotree_patrolresourcediv" style="width: 220px;">
					<input type="hidden" name="vo.items" id="voitems">
				</template:formTr>

				<template:formSubmit>
					<td>
						<input type="button" onclick="valiSub()" id="button" class="button" value="���">
					</td>
					<td>
					</td>
					<td>
						<input type="button" class="button" onclick="history.back()" value="����" >
					</td>
				</template:formSubmit>
			</template:formTable>
		</s:form>
	</body>
</html>


