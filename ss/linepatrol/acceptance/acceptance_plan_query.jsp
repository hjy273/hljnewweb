
<%@page import="org.springframework.web.context.request.SessionScope"%><%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<script type='text/javascript'
			src='${ctx}/linepatrol/js/change_style.js'></script>
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
		//�༭
		function toEditForm(applyId){
			window.location.href = "${ctx}/acceptancePlanQueryAction.do?method=forwardEditAcceptancePlan&applyId="+applyId;
		}
		//�༭�ܵ��б�
		function toEditPipeList(applyId){
			window.location.href = "${ctx}/acceptancePlanQueryAction.do?method=getCablePipeList&type=pipe&canDelete=can&applyId="+applyId;
		}
		//�༭�����б�
		function toEditCableList(applyId){
			window.location.href = "${ctx}/acceptancePlanQueryAction.do?method=getCablePipeList&type=cable&canDelete=can&applyId="+applyId;
		}
	</script>
	<style type="text/css">
		.Content{ border:1px #000000 solid;}

		.table_1{  border:1px #000000 solid;}
		.table_1_tr1{ border-bottom:1px #000000 solid;}
		
		.table_2{  border:0px #000000 solid;}
		.table_2 td{ border-bottom:1px #000000 solid; border-right:1px #000000 solid;}
		.table_2_td{ border-top:1px #000000 solid; border-left:1px #000000 solid; }
		.table_2_left{border-left:1px #000000 solid; }
	</style>
	</head>
	<body onload="changeStyle()">
		<template:titile value="��ά�ƻ���ѯ" />
		<!-- ��ѯҳ�� -->
		<html:form action="/acceptancePlanQueryAction.do?method=queryAcceptancePlanResult"
			styleId="form" onsubmit="return check()">
			<template:formTable namewidth="150" contentwidth="400">
				<template:formTr name="��Ŀ����">
					<html:text property="name" styleClass="inputtext"
						style="width:120px" />
				</template:formTr>
				<template:formTr name="�Ƿ��׼" isOdd="false">
					<select name="ispassed" style="width:200px">
						<option value="">����</option>
						<option value="1">��</option>
						<option value="0">��</option>
					</select>
				</template:formTr>
				<template:formTr name="����ʱ��">
					<html:text property="begintime" styleClass="Wdate"
						style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" /> ��
				<html:text property="endtime" styleClass="Wdate" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})" />
				</template:formTr>
			</template:formTable>
			<div align="center">
				<input name="isQuery" value="true" type="hidden">
				<html:submit property="action" styleClass="button">�� ѯ</html:submit>
			</div>
		</html:form>
		<template:displayHide styleId="form"></template:displayHide>
		
		<logic:notEmpty name="list">
			<display:table name="sessionScope.list" id="row" pagesize="18" export="false">
				<%
					DynaBean bean = (DynaBean)pageContext.findAttribute("row");
					String applicant = bean.get("applicant") == null ? "" : (String)bean.get("applicant");
					String id = bean.get("id") == null ? "" : (String)bean.get("id");
				%>
				<display:column property="name" title="����" />
				<display:column property="code" title="���ս�ά���" />
				<display:column media="html" title="��Ŀ����">
					<apptag:assorciateAttr table="userinfo" label="username" key="userid" keyValue="<%=applicant %>" />
				</display:column>
				<display:column media="html" title="����ʱ��">
					<bean:write name="row" property="apply_date" format="yyyy/MM/dd" />
				</display:column>
				<display:column media="html" title="������Դ����">
					<logic:equal value="2" name="row" property="resource_type">
						�ܵ�
					</logic:equal>
					<logic:notEqual value="2" name="row" property="resource_type">
						����
					</logic:notEqual>
				</display:column>
				<display:column media="html" title="����">
					<logic:equal value="can" name="row" property="can_modify">
						<a href="javascript:toEditForm('<%=id %>')">�༭</a> | 
						<logic:equal value="2" name="row" property="resource_type">
							<a href="javascript:toEditPipeList('<%=id %>')">�༭�ܵ�</a>
						</logic:equal>
						<logic:notEqual value="2" name="row" property="resource_type">
							<a href="javascript:toEditCableList('<%=id %>')">�༭����</a>
						</logic:notEqual>
					</logic:equal>
				</display:column>
			</display:table>
		</logic:notEmpty>
		<!-- ��ѯ�б� -->
		<logic:notEmpty name="list">
			<!-- ��ѯ��� -->
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