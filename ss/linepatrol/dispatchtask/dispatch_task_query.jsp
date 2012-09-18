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
	<template:titile value="按条件查找任务派单" />
	<html:form
		action="/dispatchtask/dispatch_task.do?method=queryForDispatchTask"
		styleId="queryForm">
		<template:formTable namewidth="200" contentwidth="450">
			<template:formTr name="派单主题">
				<html:text property="sendtopic" styleClass="inputtext"
					style="width:180" />
			</template:formTr>
			<template:formTr name="工作类别">
				<select name="sendtype" id="sendtype" style="" class="inputtext" />
					<option value="">
						不限
					</option>
					<logic:notEmpty name="dispatch_task_type_list">
						<logic:iterate id="oneDispatchTaskType"
							name="dispatch_task_type_list">
							<option
								value="<bean:write name="oneDispatchTaskType" property="code" />">
								<bean:write name="oneDispatchTaskType" property="lable" />
							</option>
						</logic:iterate>
					</logic:notEmpty>
				</select>
			</template:formTr>
			<template:formTr name="处理时限">
				<select name="processterm" style="width: 180" class="inputtext">
					<option value="">
						不限
					</option>
					<option value="0">
						超时
					</option>
					<option value="-1">
						未超时
					</option>
				</select>
			</template:formTr>
			<template:formTr name="派单开始时间">
				<input id="begin" type="text" readonly="readonly" name="begintime"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate:'#F{$dp.$D(\'end\')||\'%y-%M-%d\'}'})"
					style="width: 150" />
			</template:formTr>
			<template:formTr name="派单结束时间">
				<input id="end" type="text" name="endtime" readonly="readonly"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'begin\')}',maxDate:'%y-%M-%d'})"
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
	</html:form>
</body>
</html>
