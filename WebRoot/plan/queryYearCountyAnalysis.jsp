<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title></title>
		<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
		<script type="text/javascript" language="javascript">
		function check() {
				var regionid = document.all.item("regionid").value
				if(regionid == '' || regionid == null) {
					alert("��ѡ���ѯͳ�Ƶ�����");
					return false;
				}
				return true;
			}
		
//��������
var patrolregioncombotree;
Ext.onReady(function() {
	patrolregioncombotree = new TreeComboField({
		width : 220,
		height : 100,
		allowBlank : false,
		renderTo : 'combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "regionid",
		displayField : 'text',
		leafOnly:true,
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			autoHeight:true,
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/common/externalresources_getRegionJson.jspx'
				})

			})
		})
	});
})
    </script>
	</head>
	<body>
		<template:titile value="���������ͳ�Ʋ�ѯ" />
		<html:form
			action="/Patrolanalysis.do?method=doMonthCountyQuery&type=${businesstype}"
			styleId="queryForm2" onsubmit="return check();">
			<html:hidden property="timeFlgStr" value="year" />
			<template:formTable namewidth="200" contentwidth="200">
				<template:formTr name="Ѳ������">
					<div id="combotree_patrolregiondiv" style="width: 220;"></div>
				</template:formTr>
				<template:formTr name="ͳ�����">
					<html:text property="startdate" readonly="true"
						styleClass="inputtext Wdate" style="width: 220px"
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy',vel:'starttime',realDateFmt:'yyyy'})" />
					<input id="starttime" name="starttime" type="hidden" />

				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">����</html:submit>
						<html:reset property="action" styleClass="button">ȡ��</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
