<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��˲��Լƻ�</title>
		<script type='text/javascript'>
		
	    </script>
	</head>

	<body>
		<template:titile value="��ѯ���Լƻ�" />
		<div style="text-align: center;">
			<iframe
				src="${ctx}/testPlanAction.do?method=viewTestPlanProcess&&forward=query_test_plan_state&&task_names=${task_names }"
				frameborder="0" id="processGraphFrame" height="70" scrolling="no"
				width="95%"></iframe>
		</div>
		<html:form action="/testPlanQueryStatAction.do?method=getTestPlans"
			styleId="queryForm">
			<table width="100%" border="0" align="center" cellpadding="3"
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
				<tr class=trcolor>
					<td class="tdulleft">
						�ƻ����ƣ�
					</td>
					<td class="tdulright">
						<html:text property="planName" styleId="planName" style="width:205" styleClass="inputtext"></html:text>
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						�ƶ��ˣ�
					</td>
					<td class="tdulright">
						<html:text property="createMan" styleId="createMan" style="width:205" styleClass="inputtext"></html:text>
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						��ά��˾��
					</td>
					<td class="tdulright">
						<c:if test="${deptype=='1'}">
							<html:select property="contractorId" styleId="contractorId" styleClass="inputtext" style="width:140px">
								<html:option value="">ȫ��</html:option>
								<html:options collection="list" property="contractorID" labelProperty="contractorName"></html:options>
							</html:select>
						</c:if>
						<c:if test="${deptype=='2'}">
							<input type="hidden" value="${sessionScope.conId }" name="contractorId" />
							<c:out value="${sessionScope.conName}"></c:out>
						</c:if>
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						�Ƿ�ȡ��:
					</td>
					<td class="tdulright">
						<html:select property="testState" styleClass="inputtext" style="width:140px">
							<html:option value="">����</html:option>
							<html:option value="999">��</html:option>
							<html:option value="0">��</html:option>
						</html:select>
					</td>
				</tr>
				<template:formTr name="��������">
					<html:multibox property="planType" value="'1'" />���˲���
					<html:multibox property="planType" value="'2'" />�ӵص������
				</template:formTr>
				<td colspan="2" style="text-align: center;">
					<input type="hidden" name="isQuery" value="true" />
					<input name="action" class="button" value="��ѯ" type="submit" />
				</td>
			</table>
			<logic:present name="queryCondition" property="taskStates">
				<logic:iterate id="oneTaskState" name="queryCondition"
					property="taskStates">
					<input id="taskStates_<bean:write name="oneTaskState" />"
						type="hidden" name="taskStates"
						value="<bean:write name="oneTaskState" />" />
				</logic:iterate>
			</logic:present>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>
		<logic:notEmpty name="plans">
			<script type="text/javascript">
		 toViewForm=function(idValue){
            window.location.href = "${ctx}/testPlanAction.do?method=viewPaln&planId="+idValue;
		}
		
		exportList=function(){
			//var url="${ctx}/troubleReplyAction.do?method=exportWaitReplyList";
			//self.location.replace(url);
		}
		//ȡ������
		toCancelForm=function(planId){
			var url;
			if(confirm("ȷ��Ҫȡ���ü���ά��������")){
				url="${ctx}/testPlanAction.do?method=cancelTestPlanForm";
				var queryString="test_plan_id="+planId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
       	function  goBack(){
			var url="${ctx}/testPlanQueryStatAction.do?method=queryTestPlanForm";
			self.location.replace(url);
		}
		</script>
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<style type="text/css">
.subject {
	text-align: center;
}
</style>
			<display:table name="sessionScope.plans" id="currentRowObject"
				pagesize="15">
				<logic:notEmpty name="currentRowObject">
					<bean:define id="pid" name="currentRowObject" property="id"></bean:define>
					<bean:define id="testState" name="currentRowObject" property="planstate"></bean:define>
					<display:column property="contractorname" sortable="true"
						title="��ά��λ" headerClass="subject" />
					<display:column media="html" title="���Լƻ�����">
						<a href="javascript:toViewForm('<%=pid %>')"> <bean:write
								name="currentRowObject" property="test_plan_name" />
						</a>
					</display:column>
					<display:column property="plantime" sortable="true" title="���Լƻ�ʱ��"
						headerClass="subject" />
					<display:column property="plantype" sortable="true" title="��������"
						headerClass="subject" />
					<display:column property="plannum" sortable="true" title="�ƻ���������"
						headerClass="subject" />
					<display:column property="testnum" sortable="true" title="ʵ�ʲ�������"
						headerClass="subject" />
					<display:column property="state" sortable="true" title="״̬"
						headerClass="subject" />
					<display:column media="html" title="����">
						<a href="javascript:toViewForm('<%=pid %>')">�鿴</a>
						<c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&testState!='999'&&testState!='50'}">
					| <a href="javascript:toCancelForm('<%=pid%>')">ȡ��</a>
				</c:if>
					</display:column>
				</logic:notEmpty>
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td>
						<logic:notEmpty name="waitPlans1">
							<a href="javascript:exportList()">����ΪExcel�ļ�</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="����" onclick="history.back();"
							type="button" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>
