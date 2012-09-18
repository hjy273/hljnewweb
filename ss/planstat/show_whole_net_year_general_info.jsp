<%@ include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />

<script language="javascript" type="text/javascript">
	
</script>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<div class='title2' style='font-size: 14px; font-weight: 600; bottom: 1'
	align='center'>
	全网	<bean:write name="CMMonthlyStatConBean" property="endYear" />
	年统计详细信息
</div>
<hr width='100%' size='1'>
<br>
<table width="80%" border="0" align="center" cellpadding="3"
	cellspacing="0" class="tabout">
	<tr>
		<th class="thlist" align="center" width="200px">
			项目
		</th>
		<th class="thlist" align="center" width="200px">
			内容
		</th>
	</tr>
	<template:formTr name="计划点次">
		<bean:write name="general_info" property="planpoint" />
	</template:formTr>
	<template:formTr name="巡检点次">
		<bean:write name="general_info" property="factpoint" />
	</template:formTr>
	<template:formTr name="巡检率">
		<bean:write name="general_info" property="patrolp" />%</template:formTr>
	<template:formTr name="计划路由长度">
		<bean:write name="general_info" property="sublinekm" />
	</template:formTr>
	<template:formTr name="巡回路由长度">
		<bean:write name="general_info" property="patrolkm" />
	</template:formTr>
</table>
