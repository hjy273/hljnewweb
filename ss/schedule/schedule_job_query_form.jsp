<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<head>
	<title>sendtask</title>
	<script type="" language="javascript">
		</script>
</head>
<body>
	<!--������ѯ��ʾ-->
	<br />
	<template:titile value="���������ҵ�������" />
	<form action="${ctx}/schedule.do?method=queryForList"
		id="queryForm" method="post">
		<template:formTable namewidth="200" contentwidth="450">
			<template:formTr name="������������">
				<input name="send_type" value="" type="radio" checked />����
				<input name="send_type" value="-1" type="radio"  />��ʱ���η���
				<input name="send_type" value="1" type="radio"  />��ʱ�ظ�����
				<input name="send_type" value="2" type="radio"  />���ڷ���
			</template:formTr>
			<template:formTr name="��ʱ���񴴽�ʱ��">
				<input id="begin" type="text" readonly="readonly" name="begin_time"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
					style="width: 150" />
				��
				<input id="end" type="text" name="end_time" readonly="readonly"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
					style="width: 150" />
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">����</html:submit>
				</td>
				<td>
					<html:reset property="action" styleClass="button">����</html:reset>
				</td>
			</template:formSubmit>
		</template:formTable>
	</form>
</body>
</html>
