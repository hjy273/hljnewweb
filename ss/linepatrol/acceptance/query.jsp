<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
		<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>

		<script type="text/javascript">
		function check(){
			if( ($('begintime').value == '' && $('endtime').value != '') ||
				($('begintime').value != '' && $('endtime').value == '') ){
				alert("���ڶα���ͬʱ�п�ʼ���ںͽ�������");
				return false;
			}
			if( ($('begintime').value != '' && $('endtime').value != '')
					&& ($('begintime').value == $('endtime').value) ){
				alert('��ʼ���ںͽ������ڲ������');
				return false;
			}
			return true;
		}
		//ȡ������
		toCancelForm=function(applyId){
			var url;
			if(confirm("ȷ��Ҫȡ�������ս�ά������")){
				url="${ctx}/acceptanceAction.do?method=cancelAcceptanceForm";
				var queryString="apply_id="+applyId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			};
		};
	</script>
	</head>
	<body onload="changeStyle()">
		<template:titile value="���ս�ά��ѯ" />
		<div style="text-align:center;">
			<iframe
				src="${ctx}/acceptanceAction.do?method=viewAcceptanceProcess&&task_name=${task_names }&&forward=query_acceptance_state"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div>
		<!-- ��ѯҳ�� -->
		<html:form action="/acceptanceQueryAction.do?method=query"
			styleId="form" onsubmit="return check()">
			<template:formTable namewidth="150" contentwidth="400">
				<template:formTr name="��������">
					<html:text property="name" styleClass="inputtext required"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="��ά��λ">
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
				</template:formTr>
				<template:formTr name="�Ƿ�ȡ��">
					<html:select property="processState" styleClass="inputtext" style="width:140px">
						<html:option value="">����</html:option>
						<html:option value="999">��</html:option>
						<html:option value="0">��</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="������Դ����">
					<html:radio property="resourceType" value="" />ȫ��
			    <html:radio property="resourceType" value="1" />����
			    <html:radio property="resourceType" value="2" />�ܵ�
			</template:formTr>
				<template:formTr name="���ս�ά״̬">
					<html:multibox property="acceptanceState" value="10" />��Ҫ��׼����
				<html:multibox property="acceptanceState" value="20" />��Ҫ��������
				<html:multibox property="acceptanceState" value="30" />��Ҫ��׼����
				<html:multibox property="acceptanceState" value="40" />��Ҫ¼������
				<html:multibox property="acceptanceState" value="00" />���
			</template:formTr>
				<template:formTr name="����ʱ��">
					<html:text property="begintime" styleId="begintime" styleClass="Wdate"
						style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" /> ��
				<html:text property="endtime" styleId="endtime" styleClass="Wdate" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'begintime\')}'})" />
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
			<div align="center">
				<input name="isQuery" value="true" type="hidden">
				<html:submit property="action" styleClass="button">�� ѯ</html:submit>
			</div>
		</html:form>
		<template:displayHide styleId="form"></template:displayHide>
		
		<script type="text/javascript">
		function view(id){
			window.location.href = '${ctx}/acceptanceQueryAction.do?method=view&id='+id;
		}
		</script>
		<logic:notEmpty name="result">
			<!-- ��ѯ��� -->
			<display:table name="sessionScope.result" id="row" pagesize="18" export="false">
				<bean:define id="sendUserId" name="row" property="applicant" />
				<bean:define id="applyState" name="row" property="processState"></bean:define>
				<display:column property="name" title="����" />
				<display:column property="code" title="���ս�ά���" />
				<display:column media="html" title="��Ŀ����">
					<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="${row.applicant}" />
				</display:column>
				<display:column media="html" title="����ʱ��">
					<bean:write name="row" property="applyDate" format="yyyy/MM/dd" />
				</display:column>
				<display:column media="html" title="������Դ����">
					<c:choose>
						<c:when test="${row.resourceType eq '1'}">����</c:when>
						<c:otherwise>�ܵ�</c:otherwise>
					</c:choose>
				</display:column>
				<display:column media="csv excel xml html" title="״̬">
					<c:if test="${row.processState eq '10'}">��Ҫ��׼����</c:if>
					<c:if test="${row.processState eq '20'}">��Ҫ��������</c:if>
					<c:if test="${row.processState eq '30'}">��Ҫ��׼����</c:if>
					<c:if test="${row.processState eq '40'}">��Ҫ¼������</c:if>
					<c:if test="${row.processState eq '46'}">��Ҫ�����׼</c:if>
					<c:if test="${row.processState eq '00'}">�����</c:if>
					<c:if test="${row.processState eq '999'}">��ȡ������</c:if>
				</display:column>
				<display:column media="html" title="����">
					<a href="javascript:view('${row.id}')">�鿴</a>
					<c:if test="${sessionScope.LOGIN_USER.deptype=='1' && applyState=='10'}">
						<a href="javascript:toCancelForm('${row.id }')">ȡ��</a>
					</c:if>
				</display:column>
			</display:table>
			<table class="tdlist">
				<tr>
					<td class="tdlist">
						<input type="button" class="button" value="����"
							onclick="history.back()" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>