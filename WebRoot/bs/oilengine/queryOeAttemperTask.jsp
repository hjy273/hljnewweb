<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="${ctx}/js/extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="${ctx}/js/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/js/extjs/ext-all.js"></script>
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
	<script language="javascript">
</script>
</head>
<body>
	<template:titile value="��ѯ��վ�ϵ�澯" />
	<form action="${ctx}/oeAttemperTaskAction_query.jspx"  name="form" method="post">
		<template:formTable contentwidth="400" namewidth="220">
			<template:formTr name="�ϵ��վ���ƻ����">
				<input type="type" name="stationName" class="inputtext" style="width: 220px" />
					</template:formTr>
				<template:formTr name="�ϵ�ʱ������">
					�ӣ�<input name="startTime" class="inputtext" style="width: 196px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  readonly /><br />
					
					����<input name="endTime" class="inputtext" style="width: 196px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly />
				</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">��ѯ</html:submit>
			</td>
			<td>
				<input type="button" class="button" onclick=history.back();
					value="����">
			</td>
		</template:formSubmit>
	</template:formTable>
	</form>
</body>