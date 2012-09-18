<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>��ѯͳ��</title>
		<script type="" language="javascript">
		</script>
	</head>
	<body>
		<!--���Ź���ͳ��������ѯ��ʾ-->
		<br />
		<template:titile value="������ͳ�Ʋ�ѯ���Ź���" />
		<!-- ��ѯҳ�� -->
		<html:form action="/dispatchtask/query_dispatch_task.do?method=queryDepartDispatchTaskTotal"
			styleId="queryForm">
			<template:formTable namewidth="200" contentwidth="400">
				<template:formTr name="��λ">
					<html:select property="acceptdeptid" styleClass="inputtext" style="width:200px">
							<html:option value="">����</html:option>
							<html:options property="deptid" labelProperty="deptname"  collection="deptinfo"/>
					</html:select>
				</template:formTr>
				<template:formTr name="���������">
				<html:select property="sendtype" styleClass="inputtext" style="width:200px">
							<html:option value="">����</html:option>
							<html:options property="code" labelProperty="lable"  collection="dispatch_task_type_list"/>
					</html:select>
				</template:formTr>
				<template:formTr name="ͳ�ƿ�ʼʱ��">
					<input id="begin" type="text" readonly="readonly" name="begintime"
						class="Wdate" value="${querycon.begintime}"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate:'#F{$dp.$D(\'end\')||\'%y-%M-%d\'}'})"
						style="width: 200px" />
				</template:formTr>
				<template:formTr name="ͳ�ƽ���ʱ��">
					<input id="end" type="text" name="endtime" readonly="readonly"
						class="Wdate" value="${querycon.endtime}"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'begin\')}',maxDate:'%y-%M-%d'})"
						style="width: 200px" />
				</template:formTr>
			</template:formTable>
			<div align="center">
				<input type="hidden" name="isQuery" value="true" />
				<html:submit property="action" styleClass="button">��ѯ</html:submit>
				<html:reset property="action" styleClass="button">����</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<!-- ��ѯ���� -->
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
				<display:column property="departname" title="��λ����" style="width:200px"
					sortable="true" maxLength="15" />
				<display:column media="html" title="�����ɵ�����" style="width:80px"
					sortable="true">
					${dispatchNum}
				</display:column>
				<display:column media="html" title="��ǩ��" style="width:80px"
					sortable="true">
					${waitSignInNum}
				</display:column>
				<display:column media="html" title="������" style="width:80px"
					sortable="true">
					${waitReplyNum}
				</display:column>
				<display:column media="html" title="����֤" style="width:80px"
					sortable="true">
					${waitCheckNum}
				</display:column>
				<display:column media="html" title="��ǩ" style="width:80px"
					sortable="true">
					${refuseNum}
				</display:column>
				<display:column media="html" title="ת��" style="width:80px"
					sortable="true">
					${transferNum}
				</display:column>
				<display:column media="html" title="���" style="width:80px"
					sortable="true">
					${completeNum}
				</display:column>
				<display:column media="html" title="������ʱ" style="width:80px"
					sortable="true">
					${replyOutTimeNum}
				</display:column>
				<display:column media="html" title="������ʱ��" style="width:100px"
					sortable="true">
					<fmt:formatNumber pattern="#0.0" value="${replyInTimeRatio}"></fmt:formatNumber>%
				</display:column>
			</display:table>
			<html:link
				action="/dispatchtask/query_dispatch_task.do?method=exportDepartTotalResult">����ΪExcel�ļ�</html:link>
		</logic:notEmpty>
		
	</body>
</html>
