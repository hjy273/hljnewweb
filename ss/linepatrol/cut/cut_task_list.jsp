<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<%@page import="com.cabletech.linepatrol.cut.dao.*" %>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<html>
	<head>
		<title>��ʾ��������б�</title>
		<script type="text/javascript">
			//�鿴����
			toViewCutForm=function(cutId){
            	window.location.href = "${ctx}/cutAction.do?method=viewCut&cutId="+cutId;
			}
			toViewReadCutForm=function(cutId,isread){
            	window.location.href = "${ctx}/cutAction.do?method=viewCut&cutId="+cutId+"&isread="+isread;
			}
			//�������������ת��
			toApproveCutForm=function(cutId,operator){
            	window.location.href = "${ctx}/cutAction.do?method=approveCutApplyForm&cutId="+cutId+"&operator="+operator;
			}
			//�༭�������
			toEditForm=function(cutId){
            	window.location.href = "${ctx}/cutAction.do?method=editCutApplyForm&cutId="+cutId;
			}
			//��д������뷴��
			toAddFeedbackForm=function(cutId){
            	window.location.href = "${ctx}/cutFeedbackAction.do?method=addCutFeedbackForm&cutId="+cutId;
			}
			//�鿴����
			toViewCutFeedback=function(cutId){
            	window.location.href = "${ctx}/cutFeedbackAction.do?method=viewCutFeedback&cutId="+cutId;
			}
			toViewReadCutFeedback=function(cutId,isread){
            	window.location.href = "${ctx}/cutFeedbackAction.do?method=viewCutFeedback&cutId="+cutId+"&isread="+isread;
			}
			//������뷴������
			toApproveCutFeedbackForm=function(cutId,operator){
            	window.location.href = "${ctx}/cutFeedbackAction.do?method=addCutFeedbackApproveForm&cutId="+cutId+"&operater="+operator;
			}
			//�༭��ӷ���
			toEditFeedbackForm=function(cutId){
            	window.location.href = "${ctx}/cutFeedbackAction.do?method=editCutFeedbackForm&cutId="+cutId;
			}
			//��Ӹ�����ս���
			toAddAcceptanceForm=function(cutId){
            	window.location.href = "${ctx}/cutAcceptanceAction.do?method=addCutAcceptanceForm&cutId="+cutId;
			}
			//�鿴���ս���
			toViewCutAcceptance=function(cutId){
            	window.location.href = "${ctx}/cutAcceptanceAction.do?method=viewCutAcceptance&cutId="+cutId;
			}
			toViewReadCutAcceptance=function(cutId,isread){
            	window.location.href = "${ctx}/cutAcceptanceAction.do?method=viewCutAcceptance&cutId="+cutId+"&isread="+isread;
			}
			//���ս�������
			toApproveCutAcceptanceForm=function(cutId,operator){
            	window.location.href = "${ctx}/cutAcceptanceAction.do?method=cutAcceptanceApproveForm&cutId="+cutId+"&operater="+operator;
			}
			//�޸����ս���
			toEditAcceptanceForm=function(cutId){
            	window.location.href = "${ctx}/cutAcceptanceAction.do?method=editCutAcceptanceForm&cutId="+cutId;
			}
			//��������
			toCutRemarkForm=function(cutId){
            	window.location.href = "${ctx}/checkAndMarkAction.do?method=checkAndMarkFrom&cutId="+cutId;
			}
			//��������
			toInvalidCutForm=function(cutId){
				if(confirm("��ȷ���Ƿ����ϸ����룿")){
            		window.location.href = "${ctx}/cutAction.do?method=invalidCut&cutId="+cutId;
            	}
			}
		//ȡ���������
		toCancelForm=function(cutId){
			var url;
			if(confirm("ȷ��Ҫȡ���ø��������")){
				url="${ctx}/cutAction.do?method=cancelCutForm";
				var queryString="cutId="+cutId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
	</head>
	<%
		BasicDynaBean object=null;
		String proposer=null;
	%>
	<body>
		
		<c:if test="${empty type}">
			<template:titile value="ȫ�����칤�� (<font color='red'>��${num }������</font>)"/>
		</c:if>
		<c:if test="${type=='apply'}">
			<template:titile value="���������칤�� (<font color='red'>��${num }������</font>)"/>
		</c:if>
		<c:if test="${type=='feedback'}">
			<template:titile value="��ӷ������칤�� (<font color='red'>��${num }������</font>)"/>
		</c:if>
		<c:if test="${type=='acceptance'}">
			<template:titile value="���ս�����칤�� (<font color='red'>��${num }������</font>)"/>
		</c:if>
		<div style="text-align:center;">
			<iframe
				src="${ctx}/cutAction.do?method=viewCutProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="75" scrolling="no"
				width="95%"></iframe>
		</div>
		<display:table name="sessionScope.list" id="cut" pagesize="18">
		<logic:notEmpty name="list">
			<bean:define id="sendUserId" name="cut" property="proposer" />
			<bean:define id="state" name="cut" property="state" />
			<display:column property="workorder_id" title="������" headerClass="subject"  sortable="true"/>
			<display:column property="cut_name" title="�������" headerClass="subject"  sortable="true"/>
			<display:column property="cut_level" title="��Ӽ���" headerClass="subject"  sortable="true"/>
			<display:column property="contractorname" title="��ά��λ" headerClass="subject"  sortable="true"/>
			<display:column property="apply_date" title="����ʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="username" title="������" headerClass="subject"  sortable="true"/>
			<display:column property="cut_state" title="���״̬" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
					<c:if test="${state eq '1' }">
						<logic:equal value="true" name="cut" property="isread">
							<a href="javascript:toViewReadCutForm('<bean:write name="cut" property="id"/>','isread')">�鿴</a>
						</logic:equal>
						<logic:equal value="false" name="cut" property="isread">
							<a href="javascript:toViewCutForm('<bean:write name="cut" property="id"/>')">�鿴</a> | 
							<a href="javascript:toApproveCutForm('<bean:write name="cut" property="id"/>','approve')" title="�����������">����</a> | 
							<a href="javascript:toApproveCutForm('<bean:write name="cut" property="id"/>','transfer')">ת��</a>
						</logic:equal>
					</c:if>
					<c:if test="${state eq '2' }">
						<a href="javascript:toViewCutForm('<bean:write name="cut" property="id"/>')">�鿴</a> | 
						<a href="javascript:toEditForm('<bean:write name="cut" property="id"/>')">�޸�����</a> | 
						<a href="javascript:toInvalidCutForm('<bean:write name="cut" property="id"/>')">����</a>
					</c:if>
					<c:if test="${state eq '3' }">
						<a href="javascript:toViewCutForm('<bean:write name="cut" property="id"/>')">�鿴</a> | 
						<a href="javascript:toAddFeedbackForm('<bean:write name="cut" property="id"/>')">��д����</a>
					</c:if>
					<c:if test="${state eq '4' }">
						<logic:equal value="true" name="cut" property="isread">
							<a href="javascript:toViewReadCutFeedback('<bean:write name="cut" property="id"/>','isread')">�鿴</a>
						</logic:equal>
						<logic:equal value="false" name="cut" property="isread">
							<a href="javascript:toViewCutFeedback('<bean:write name="cut" property="id"/>')">�鿴</a> | 
							<a href="javascript:toApproveCutFeedbackForm('<bean:write name="cut" property="id"/>','approve')" title="��ӷ�������">����</a> | 
							<a href="javascript:toApproveCutFeedbackForm('<bean:write name="cut" property="id"/>','transfer')">ת��</a>
						</logic:equal>
					</c:if>
					<c:if test="${state eq '5' }">
						<a href="javascript:toViewCutFeedback('<bean:write name="cut" property="id"/>')">�鿴</a> | 
						<a href="javascript:toEditFeedbackForm('<bean:write name="cut" property="id"/>')">�޸ķ���</a>
					</c:if>
					<c:if test="${state eq '6' }">
						<a href="javascript:toViewCutFeedback('<bean:write name="cut" property="id"/>')">�鿴</a> | 
						<a href="javascript:toAddAcceptanceForm('<bean:write name="cut" property="id"/>')">��д����</a>
					</c:if>
					<c:if test="${state eq '7' }">
						<logic:equal value="true" name="cut" property="isread">
							<a href="javascript:toViewReadCutAcceptance('<bean:write name="cut" property="id"/>','isread')">�鿴</a>
						</logic:equal>
						<logic:equal value="false" name="cut" property="isread">
							<a href="javascript:toViewCutAcceptance('<bean:write name="cut" property="id"/>')">�鿴</a> | 
							<a href="javascript:toApproveCutAcceptanceForm('<bean:write name="cut" property="id"/>','approve')" title="������ս�������">����</a> | 
							<a href="javascript:toApproveCutAcceptanceForm('<bean:write name="cut" property="id"/>','transfer')">ת��</a>
						</logic:equal>
					</c:if>
					<c:if test="${state eq '8' }">
						<a href="javascript:toViewCutAcceptance('<bean:write name="cut" property="id"/>')">�鿴</a> |
						<a href="javascript:toEditAcceptanceForm('<bean:write name="cut" property="id"/>')">�޸Ľ���</a>
					</c:if>
					<c:if test="${state eq '9' }">
						<a href="javascript:toViewCutAcceptance('<bean:write name="cut" property="id"/>')">�鿴</a> |
						<a href="javascript:toCutRemarkForm('<bean:write name="cut" property="id"/>')">��������</a>
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER.deptype == '1'}">
						<c:if test="${state eq '1'}">
						| <a href="javascript:toCancelForm('<bean:write name="cut" property="id"/>')">ȡ��</a>
						</c:if>
					</c:if>
			</display:column>
			</logic:notEmpty>
		</display:table>
	</body>
</html>
