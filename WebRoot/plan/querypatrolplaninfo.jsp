<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title></title>
		<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
		<script type="text/javascript" language="javascript">
//维护组下拉组件
var patrolgroupcombotree;
//所属区县
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
		<template:titile value="巡检计划查询" />
		<html:form
			action="/TowerPatrolinfo.do?method=doQuery&type=${businesstype}"
			styleId="queryForm2">
			<template:formTable namewidth="200" contentwidth="400">
				<template:formTr name="年份">
					<apptag:getYearOptions />
					<html:select property="year">
						<html:options collection="yearCollection" property="value"
							labelProperty="label"></html:options>
					</html:select>
				</template:formTr>
				<template:formTr name="计划类型">
				    <html:radio property="plantype" value=""  /> 所有
					<html:radio property="plantype" value="1" /> 半年
					<html:radio property="plantype" value="2" /> 季度
					<html:radio property="plantype" value="3" /> 月度
					<html:radio property="plantype" value="4" /> 自定义
				</template:formTr>
				<template:formTr name="巡检区域">
					<div id="combotree_patrolregiondiv" style="width: 300"></div>
				</template:formTr>
				<template:formTr name="巡检组">
					<div id="combotree_patrolgroupdiv" style="width: 300;"></div>
					<html:hidden property="patrolgroupname" />
					<html:hidden property="patrolgroupid" />
				</template:formTr>
				<template:formTr name="计划名称">
					<html:text property="planname" styleClass="inputtext"
						style="width:300;" maxlength="25" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">查找</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">取消</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
