<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>��ʱ��������б�</title>
		<script type="text/javascript">
			//�鿴����
			toAddApplyCut=function(cutId){
            	window.location.href = "${ctx}/cutAction.do?method=addCutApplyForm&cutId="+cutId;
			}
			//ɾ������
			toDeleteApplyCut=function(cutId){
            	window.location.href = "${ctx}/cutAction.do?method=deleteTempSaveCut&cutId="+cutId;
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
	<body>
		<template:titile value="��ʱ��������б�"/>
		<display:table name="sessionScope.list" id="cut" pagesize="18">
		<logic:notEmpty name="list">
			<bean:define id="sendUserId" name="cut"
				property="proposer" />
			<display:column property="workorder_id" title="������" headerClass="subject"  sortable="true"/>
			<display:column property="cut_name" title="�������" headerClass="subject"  sortable="true"/>
			<display:column property="charge_man" title="��������" headerClass="subject"  sortable="true"/>
			<display:column property="cut_level" title="��Ӽ���" headerClass="subject"  sortable="true"/>
			<display:column property="apply_date" title="����ʱ��" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toAddApplyCut('<bean:write name="cut" property="id"/>')">��������</a> | 
				<a href="javascript:toDeleteApplyCut('<bean:write name="cut" property="id"/>')">ɾ��</a>
					<c:if test="${sessionScope.LOGIN_USER.userID==sendUserId}">
						| <a href="javascript:toCancelForm('<bean:write name="cut" property="id"/>')">ȡ��</a>
					</c:if>
			</display:column>
			</logic:notEmpty>
		</display:table>
	</body>
</html>
