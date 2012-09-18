<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link type="text/css" rel="stylesheet" href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<head>
<script language="javascript" type="text/javascript">
</script>
</head>
<body><br>
	<template:titile value="查看基站断电告警"/>
	<template:formTable>
		<template:formTr name="断电基站"> 
			${oeAttemperTask.blackoutStation } 
		</template:formTr>
		<template:formTr name="断电时间">
			 ${oeAttemperTask.blackoutTime}
		</template:formTr>
		<template:formTr name="断电原因">
			${oeAttemperTask.blackoutReason }
		</template:formTr>
	</template:formTable>
	<template:formSubmit>
			<td>
				<input type="button" class="button" onclick="history.back()" value="返回">
			</td>
		</template:formSubmit>
</body>
