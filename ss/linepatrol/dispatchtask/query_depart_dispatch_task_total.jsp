<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>查询统计</title>
		<script type="" language="javascript">
		</script>
	</head>
	<body>
		<!--部门工单统计条件查询显示-->
		<br />
		<template:titile value="按条件统计查询部门工单" />
		<!-- 查询页面 -->
		<html:form action="/dispatchtask/query_dispatch_task.do?method=queryDepartDispatchTaskTotal"
			styleId="queryForm">
			<template:formTable namewidth="200" contentwidth="400">
				<template:formTr name="单位">
					<html:select property="acceptdeptid" styleClass="inputtext" style="width:200px">
							<html:option value="">不限</html:option>
							<html:options property="deptid" labelProperty="deptname"  collection="deptinfo"/>
					</html:select>
				</template:formTr>
				<template:formTr name="工作单类别">
				<html:select property="sendtype" styleClass="inputtext" style="width:200px">
							<html:option value="">不限</html:option>
							<html:options property="code" labelProperty="lable"  collection="dispatch_task_type_list"/>
					</html:select>
				</template:formTr>
				<template:formTr name="统计开始时间">
					<input id="begin" type="text" readonly="readonly" name="begintime"
						class="Wdate" value="${querycon.begintime}"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate:'#F{$dp.$D(\'end\')||\'%y-%M-%d\'}'})"
						style="width: 200px" />
				</template:formTr>
				<template:formTr name="统计结束时间">
					<input id="end" type="text" name="endtime" readonly="readonly"
						class="Wdate" value="${querycon.endtime}"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'begin\')}',maxDate:'%y-%M-%d'})"
						style="width: 200px" />
				</template:formTr>
			</template:formTable>
			<div align="center">
				<input type="hidden" name="isQuery" value="true" />
				<html:submit property="action" styleClass="button">查询</html:submit>
				<html:reset property="action" styleClass="button">重置</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<!-- 查询界面 -->
		<logic:notEmpty name="DEPART_TOTAL_LIST">
			<display:table name="sessionScope.DEPART_TOTAL_LIST"
				id="currentRowObject" pagesize="20" style="width:100%">
				<bean:define id="deptId" name="currentRowObject" property="departid" />
				<bean:define id="dispatchNum" name="currentRowObject"
					property="sum_num" />
				<bean:define id="waitSignInNum" name="currentRowObject"
					property="wait_sign_in_num" />
				<bean:define id="waitReplyNum" name="currentRowObject"
					property="wait_reply_num" />
				<bean:define id="waitCheckNum" name="currentRowObject"
					property="wait_check_num" />
				<bean:define id="refuseNum" name="currentRowObject"
					property="refuse_num" />
				<bean:define id="transferNum" name="currentRowObject"
					property="transfer_num" />
				<bean:define id="completeNum" name="currentRowObject"
					property="complete_num" />
				<bean:define id="replyOutTimeNum" name="currentRowObject"
					property="reply_out_time_num" />
				<bean:define id="checkOutTimeNum" name="currentRowObject"
					property="check_out_time_num" />
				<bean:define id="replyInTimeRatio" name="currentRowObject"
					property="reply_in_time_ratio" />
				<bean:define id="checkInTimeRatio" name="currentRowObject"
					property="check_in_time_ratio" />
				<display:column property="departname" title="单位名称" style="width:200px"
					sortable="true" maxLength="15" />
				<display:column media="html" title="处理派单总数" style="width:80px"
					sortable="true">
					${dispatchNum}
				</display:column>
				<display:column media="html" title="待签收" style="width:80px"
					sortable="true">
					${waitSignInNum}
				</display:column>
				<display:column media="html" title="待反馈" style="width:80px"
					sortable="true">
					${waitReplyNum}
				</display:column>
				<display:column media="html" title="待验证" style="width:80px"
					sortable="true">
					${waitCheckNum}
				</display:column>
				<display:column media="html" title="拒签" style="width:80px"
					sortable="true">
					${refuseNum}
				</display:column>
				<display:column media="html" title="转派" style="width:80px"
					sortable="true">
					${transferNum}
				</display:column>
				<display:column media="html" title="完成" style="width:80px"
					sortable="true">
					${completeNum}
				</display:column>
				<display:column media="html" title="反馈超时" style="width:80px"
					sortable="true">
					${replyOutTimeNum}
				</display:column>
				<display:column media="html" title="反馈及时率" style="width:100px"
					sortable="true">
					<fmt:formatNumber pattern="#0.0" value="${replyInTimeRatio}"></fmt:formatNumber>%
				</display:column>
			</display:table>
			<html:link
				action="/dispatchtask/query_dispatch_task.do?method=exportDepartTotalResult">导出为Excel文件</html:link>
		</logic:notEmpty>
		
	</body>
</html>
