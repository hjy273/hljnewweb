<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link type="text/css" rel="stylesheet" href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<head>
<script language="javascript" type="text/javascript">
</script>
</head>
<body><br>
	<template:titile value="�鿴��վ�ϵ�澯"/>
	<template:formTable>
		<template:formTr name="�ϵ��վ"> 
			${oeAttemperTask.blackoutStation } 
		</template:formTr>
		<template:formTr name="�ϵ�ʱ��">
			 ${oeAttemperTask.blackoutTime}
		</template:formTr>
		<template:formTr name="�ϵ�ԭ��">
			${oeAttemperTask.blackoutReason }
		</template:formTr>
	</template:formTable>
	<template:formSubmit>
			<td>
				<input type="button" class="button" onclick="history.back()" value="����">
			</td>
		</template:formSubmit>
</body>
