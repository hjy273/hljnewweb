<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>��ʾ��������б�</title>
		<script type="text/javascript">
			//�鿴����
			toViewCutForm=function(cutId){
            	window.location.href = "${ctx}/cutAction.do?method=viewCut&cutId="+cutId;
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
		<template:titile value="��������б�"/>
		<display:table name="sessionScope.list" id="cut" pagesize="18">
			<display:column property="workorder_id" title="������" headerClass="subject"  sortable="true"/>
			<display:column property="cut_level" title="��Ӽ���" headerClass="subject"  sortable="true"/>
			<display:column property="cut_begintime" title="��ӿ�ʼʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="cut_endtime" title="��ӽ���ʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="cut_builder" title="ʩ����λ" headerClass="subject"  sortable="true"/>
			<display:column property="cut_state" title="���״̬" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toViewCutForm('<bean:write name="cut" property="id"/>')">�鿴</a>
					<c:if test="${sessionScope.LOGIN_USER.userID==sendUserId&&cut.cut_state!='999'}">
						| <a href="javascript:toCancelForm('${cut.id }')">ȡ��</a>
					</c:if>
			</display:column>
		</display:table>
	</body>
</html>
