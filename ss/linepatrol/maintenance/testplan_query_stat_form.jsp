<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��˲��Լƻ�</title>
		<script type='text/javascript'>
	
</script>
	</head>

	<body>
		<template:titile value="ͳ�Ʋ��Լƻ�" />
		<html:form action="/testPlanQueryStatAction.do?method=statTestPlans"
			styleId="queryForm">
			<table width="80%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<template:formTr name="�ƻ����Կ�ʼ����">
					<html:text property="planBeginTimeStart" styleId="planBeginTimeStart" styleClass="Wdate" style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})"></html:text>
					��
					<html:text property="planBeginTimeEnd" styleId="planBeginTimeEnd" styleClass="Wdate" style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate: '#F{$dp.$D(\'planBeginTimeStart\')}'})"></html:text>
				</template:formTr>
				<template:formTr name="�ƻ����Խ�������">
					<html:text property="planEndTimeStart" styleId="planEndTimeStart" styleClass="Wdate" style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					��
					<html:text property="planEndTimeEnd" styleId="planEndTimeEnd" styleClass="Wdate" style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate: '#F{$dp.$D(\'planEndTimeStart\')}'})"></html:text>
				</template:formTr>
				<logic:equal value="1" name="LOGIN_USER" property="deptype">
					<template:formTr name="���Ե�λ">
						<html:select property="contractorId" styleId="contractorId" styleClass="inputtext" style="width:140px">
							<html:option value="">����</html:option>
							<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
						</html:select>
					</template:formTr>
				</logic:equal>
				<template:formTr name="��������">
					<html:multibox property="planType" value="'1'" />���˲���
					<html:multibox property="planType" value="'2'" />�ӵص������
				</template:formTr>
				<td colspan="2" style="text-align: center;">
					<input type="hidden" name="isQuery" value="true" />
					<input name="action" class="button" value="��ѯ" type="submit" />
				</td>
			</table>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>
		<logic:notEmpty name="statPlans">
			<script type="text/javascript" language="javascript">
	exportList = function() {
		var url = "${ctx}/testPlanQueryStatAction.do?method=exportTestPlansStatList";
		self.location.replace(url);
	}
	function goBack() {
		var url = "${ctx}/testPlanQueryStatAction.do?method=statTestPlanForm";
		self.location.replace(url);
	}
</script>
			<title>���Լƻ�һ����</title>
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<style type="text/css">
.subject {
	text-align: center;
}
</style>

			<%
				BasicDynaBean object = null;
					int num = 0;
					int solvenum = 0;
			%>
			<display:table name="sessionScope.statPlans" id="currentRowObject"
				pagesize="15">
				<display:column property="contractorname" sortable="true"
					title="��ά��λ" headerClass="subject" />
				<display:column property="test_plan_name" sortable="true"
					title="���Լƻ�����" headerClass="subject" />
				<display:column property="plantime" sortable="true" title="���Լƻ�ʱ��"
					headerClass="subject" />
				<display:column property="plantype" sortable="true" title="��������"
					headerClass="subject" />
				<display:column property="plannum" sortable="true" title="�ƻ���������"
					headerClass="subject" />
				<display:column property="testnum" sortable="true" title="ʵ�ʲ�������"
					headerClass="subject" />
				<display:column property="num" sortable="true" title="������������"
					headerClass="subject" />
				<display:column property="solvenum" sortable="true" title="�����������"
					headerClass="subject" />
				<%
					String rate = "100%";
							object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
							if (object != null) {
								num = Integer.parseInt(object.get("num").toString());
								solvenum = Integer.parseInt(object.get("solvenum").toString());
								if (num != 0) {
									double r = (double) solvenum / num;
									rate = r * 100 + "%";
								}
							}
				%>
				<display:column value="<%=rate%>" sortable="true" title="�����޸���"
					headerClass="subject" />
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td>
						<logic:notEmpty name="statPlans">
							<a href="javascript:exportList()">����ΪExcel�ļ�</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="����" onclick="goBack();"
							type="button" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>
