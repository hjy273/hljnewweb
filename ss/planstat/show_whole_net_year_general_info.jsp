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
	ȫ��	<bean:write name="CMMonthlyStatConBean" property="endYear" />
	��ͳ����ϸ��Ϣ
</div>
<hr width='100%' size='1'>
<br>
<table width="80%" border="0" align="center" cellpadding="3"
	cellspacing="0" class="tabout">
	<tr>
		<th class="thlist" align="center" width="200px">
			��Ŀ
		</th>
		<th class="thlist" align="center" width="200px">
			����
		</th>
	</tr>
	<template:formTr name="�ƻ����">
		<bean:write name="general_info" property="planpoint" />
	</template:formTr>
	<template:formTr name="Ѳ����">
		<bean:write name="general_info" property="factpoint" />
	</template:formTr>
	<template:formTr name="Ѳ����">
		<bean:write name="general_info" property="patrolp" />%</template:formTr>
	<template:formTr name="�ƻ�·�ɳ���">
		<bean:write name="general_info" property="sublinekm" />
	</template:formTr>
	<template:formTr name="Ѳ��·�ɳ���">
		<bean:write name="general_info" property="patrolkm" />
	</template:formTr>
</table>
