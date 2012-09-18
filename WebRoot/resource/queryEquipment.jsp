<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxcommon.js"></script>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxtree.js"></script>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/ext/dhtmlxtree_json.js"></script>

<style type="text/css">
.input_line {
	BORDER-TOP-STYLE: none;
	BORDER-BOTTOM: #cccccc 1px solid;
	BORDER-RIGHT-STYLE: none;
	BORDER-LEFT-STYLE: none;
	TEXT-ALIGN: center;
	COLOR: RED
}
</style>

<head>
	
</head>
<body>
	<template:titile value="查询设备变更信息" />
<s:form action="equipmentAction_queryEqu.jspx" method="post">
	<template:formTable contentwidth="200" namewidth="200">
		<template:formTr name="所属区县" isOdd="false">
			<select name="districtId" class="inputtext" style="width: 150px">
				<c:forEach var="oneType" items="${sessionScope.DISTRICTS}">
					<option value="${oneType.key }">
						${oneType.value }
					</option>
				</c:forEach>
			</select>
		</template:formTr>
		<template:formTr name="变更类型">
			<input type="radio" name="type" value="new" checked="checked">新增设备</input>
			<input type="radio" name="type" value="change">变更设备</input>
			<input type="radio" name="type" value="redeploy">调入设备</input>
			<input type="radio" name="type" value="redeployout">调出设备</input>
		</template:formTr>
		<template:formTr name="查询时间区间">
			<input name="startTime" class="inputtext" style="width: 100px" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly />
					-
					<input name="endTime" class="inputtext" style="width: 100px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" readonly />
		</template:formTr>		
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">查询</html:submit>
			</td>
			<td>
				<input type="button" class="button" onclick=history.back(); value="返回">
			</td>
		</template:formSubmit>
	</template:formTable>
</s:form>
</body>
