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
	<!--条件查询显示-->
	<br />
	<template:titile value="按条件查找调度任务" />
	<form action="${ctx}/schedule.do?method=queryForList"
		id="queryForm" method="post">
		<template:formTable namewidth="200" contentwidth="450">
			<template:formTr name="调度任务类型">
				<input name="send_type" value="" type="radio" checked />不限
				<input name="send_type" value="-1" type="radio"  />定时单次发送
				<input name="send_type" value="1" type="radio"  />定时重复发送
				<input name="send_type" value="2" type="radio"  />周期发送
			</template:formTr>
			<template:formTr name="定时任务创建时间">
				<input id="begin" type="text" readonly="readonly" name="begin_time"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
					style="width: 150" />
				到
				<input id="end" type="text" name="end_time" readonly="readonly"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
					style="width: 150" />
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">查找</html:submit>
				</td>
				<td>
					<html:reset property="action" styleClass="button">重置</html:reset>
				</td>
			</template:formSubmit>
		</template:formTable>
	</form>
</body>
</html>
