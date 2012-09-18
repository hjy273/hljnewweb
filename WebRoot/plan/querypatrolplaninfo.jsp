<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title></title>
		<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
		<script type="text/javascript" language="javascript">
//ά�����������
var patrolgroupcombotree;
//��������
var patrolregioncombotree;
Ext.onReady(function() {
	patrolgroupcombotree = new TreeComboField({
		width : 300,
		maxHeight : 100,
		allowBlank : false,
		leafOnly : true,
		renderTo : 'combotree_patrolgroupdiv',
		name : "patrolgroupname",
		hiddenName : "patrolgroupid",
		displayField : 'text',
		forceSelection : true,
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '${ctx}/common/externalresources_getPatrolmanJson.jspx'
						})

			})
		})
	});
	patrolregioncombotree = new TreeComboField({
		width : 300,
		maxHeight : 100,
		allowBlank : false,
		renderTo : 'combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "regionid",
		displayField : 'text',
		forceSelection : true,
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
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
		<template:titile value="Ѳ��ƻ���ѯ" />
		<html:form
			action="/TowerPatrolinfo.do?method=doQuery&type=${businesstype}"
			styleId="queryForm2">
			<template:formTable namewidth="200" contentwidth="400">
				<template:formTr name="���">
					<apptag:getYearOptions />
					<html:select property="year">
						<html:options collection="yearCollection" property="value"
							labelProperty="label"></html:options>
					</html:select>
				</template:formTr>
				<template:formTr name="�ƻ�����">
				    <html:radio property="plantype" value=""  /> ����
					<html:radio property="plantype" value="1" /> ����
					<html:radio property="plantype" value="2" /> ����
					<html:radio property="plantype" value="3" /> �¶�
					<html:radio property="plantype" value="4" /> �Զ���
				</template:formTr>
				<template:formTr name="Ѳ������">
					<div id="combotree_patrolregiondiv" style="width: 300"></div>
				</template:formTr>
				<template:formTr name="Ѳ����">
					<div id="combotree_patrolgroupdiv" style="width: 300;"></div>
					<html:hidden property="patrolgroupname" />
					<html:hidden property="patrolgroupid" />
				</template:formTr>
				<template:formTr name="�ƻ�����">
					<html:text property="planname" styleClass="inputtext"
						style="width:300;" maxlength="25" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">����</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">ȡ��</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
