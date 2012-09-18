<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>������ѯ</title>
		<script type="text/javascript">
			function checkForm(){
				return true;
			}
		</script>
	</head>
	<body>
		<template:titile value="������ѯ" />
		<div style="text-align: center;">
			<iframe
				src="${ctx}/drillTaskAction.do?method=viewDrillTaskProcess&&forward=query_drill_task_state&&task_names=${task_names }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div>
		<html:form action="/queryStatAction.do?method=queryStat"
			onsubmit="return checkForm();" styleId="queryForm">
			<template:formTable>
				<template:formTr name="��ά��˾">
					<c:if test="${deptype=='1'}">
						<html:select property="conId" styleId="conId" styleClass="inputtext" style="width:140px">
							<html:option value="">ȫ��</html:option>
						<html:options collection="list" property="contractorID" labelProperty="contractorName"></html:options>
						</html:select>
					</c:if>
					<c:if test="${deptype=='2'}">
						<input type="hidden" value="${conId }" name="conId" />
						<c:out value="${conName}"></c:out>
					</c:if>
				</template:formTr>
				<template:formTr name="�Ƿ�ȡ��">
					<html:select property="drillState" styleClass="inputtext" style="width:140px">
						<html:option value="">����</html:option>
						<html:option value="999">��</html:option>
						<html:option value="0">��</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="��������">
					<html:text property="name" styleId="name" styleClass="inputtext" style="width:140px"/>
				</template:formTr>
				<template:formTr name="����ʱ��">
					<html:text property="beginTime" styleClass="Wdate" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
					 -- <html:text property="endTime" styleClass="Wdate validate-date-after-startTime" style="width:102px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly="true"/>
				</template:formTr>
				<template:formTr name="��������">
				<html:multibox property="levels" value="2" />һ������
				<html:multibox property="levels" value="1" />�ص�����
				<html:multibox property="levels" value="0" />�Զ�������
				</template:formTr>
				<template:formTr name="״̬">
					<html:multibox property="state" value="'1'" />�����ɵ�
					<html:multibox property="state" value="'2','3','4','9','10'" />��������
					<html:multibox property="state" value="'5','6'" />�����ܽ�
					<html:multibox property="state" value="'7'" />����
					<html:multibox property="state" value="'8'" />���
				</template:formTr>
			</template:formTable>
			<logic:present name="queryCondition" property="taskStates">
				<logic:iterate id="oneTaskState" name="queryCondition"
					property="taskStates">
					<input id="taskStates_<bean:write name="oneTaskState" />"
						type="hidden" name="taskStates"
						value="<bean:write name="oneTaskState" />" />
				</logic:iterate>
			</logic:present>
			<div align="center" style="height: 40px">
				<input name="isQuery" value="true" type="hidden" />
				<html:submit property="action" styleClass="button">��ѯ</html:submit>
				&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">��д</html:reset>
				&nbsp;&nbsp;
				<html:button property="button" styleClass="button"
					onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
		<template:displayHide styleId="queryForm"></template:displayHide>

		<logic:notEmpty name="result">
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<link rel='stylesheet' type='text/css'
				href='${ctx}/js/extjs/resources/css/ext-all.css' />
			<script type='text/javascript'
				src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
			<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
			<script type="text/javascript">
			toViewDrill=function(taskId,planId,summaryId,conId){
            	window.location.href = "${ctx}/queryStatAction.do?method=viewDrill&taskId="+taskId+"&planId="+planId+"&conId="+conId+"&summaryId="+summaryId;
			}
			//ȡ������
		toCancelForm=function(drillTaskId){
			var url;
			if(confirm("ȷ��Ҫȡ��������������")){
				url="${ctx}/drillTaskAction.do?method=cancelDrillTaskForm";
				var queryString="drill_task_id="+drillTaskId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				//window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
				window.showModalDialog(url+"&"+queryString+"&rnd="+Math.random(),"","dialogWidth:500px;dialogHeight:350px;center:yes;");
				document.forms[0].submit();
			}
		}
		</script>
			<%
				BasicDynaBean object = null;
					Object taskId = null;
					Object planId = null;
					Object summaryId = null;
					Object conId = null;
					Object taskName = null;
			%>
			<display:table name="sessionScope.result" id="drill" pagesize="18">
			<logic:notEmpty name="result">
				<bean:define id="applyState" name="drill" property="state"></bean:define>
				<bean:define id="createUser" name="drill" property="creator"></bean:define>
				
				<display:column title="��������" media="html" sortable="true">
					<%
						object = (BasicDynaBean) pageContext.findAttribute("drill");
									if (object != null) {
										taskId = object.get("task_id");
										taskName = object.get("task_name");
										planId = object.get("plan_id");
										summaryId = object.get("summary_id");
										conId=object.get("taskcon_id");
									}
					%>
					<a
						href="javascript:toViewDrill('<%=taskId%>','<%=planId%>','<%=summaryId%>','<%=conId%>')"><%=taskName%></a>
				</display:column>
				<display:column property="contractorname" title="��ά��λ"
					headerClass="subject" sortable="true" />
				<display:column property="drill_level" title="��������"
					headerClass="subject" sortable="true" />
				<display:column property="task_begintime" title="�ƻ���ʼʱ��"
					headerClass="subject" sortable="true" />
				<display:column property="task_endtime" title="�ƻ�����ʱ��"
					headerClass="subject" sortable="true" />
				<display:column property="plan_person_number" title="�ƻ�Ͷ������"
					headerClass="subject" sortable="true" />
				<display:column property="plan_car_number" title="�ƻ�Ͷ�복����"
					headerClass="subject" sortable="true" />
				<display:column property="plan_equipment_number" title="�ƻ�Ͷ���豸��"
					headerClass="subject" sortable="true" />
				<display:column property="summary_person_number" title="ʵ��Ͷ������"
					headerClass="subject" sortable="true" />
				<display:column property="summary_car_number" title="ʵ��Ͷ�복����"
					headerClass="subject" sortable="true" />
				<display:column property="sum_equipment_number" title="ʵ��Ͷ���豸��"
					headerClass="subject" sortable="true" />
				<display:column property="createtime" title="�ɷ�����ʱ��"
					headerClass="subject" sortable="true" />
				<display:column media="html" title="����">
					<a href="javascript:toViewDrill('<%=taskId%>','<%=planId%>','<%=summaryId%>','<%=conId%>')">�鿴</a>
					
					<c:if test="${sessionScope.LOGIN_USER.userID == createUser }">
						<c:if test="${applyState!='999'}">
						| <a href="javascript:toCancelForm('<%=taskId%>')">ȡ��</a>
						</c:if>
					</c:if>
					
					
				</display:column>
				</logic:notEmpty>
			</display:table>
			<p>
				<html:link action="/queryStatAction.do?method=exportDrillList">����ΪExcel�ļ�</html:link>
			</p>
			<div style="height: 50px; text-align: center;">
				<html:button property="button" styleClass="button"
					onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</logic:notEmpty>
	</body>
</html>
