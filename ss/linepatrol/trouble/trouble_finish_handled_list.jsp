<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		 toViewForm=function(idValue,isread,flowState){
           window.location.href = "${ctx}/troubleQueryStatAction.do?method=viewTrouble&troubleid="+idValue;
		}
		toCancelForm=function(idValue){
			var url;
			if(confirm("ȷ��Ҫȡ���ù���������")){
				url="${ctx}/troubleInfoAction.do?method=cancelTroubleForm";
				var queryString="troubleId="+idValue;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
		<title>�Ѱ����һ����</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		 <bean:size id="size"  name="handeledReplys" />
		<template:titile value="�Ѱ����һ����  (<font color='red'>${size}���Ѱ�</font>)" />
		<!--<div style="text-align:center;">
			<iframe
				src="${ctx}/troubleReplyAction.do?method=viewTroubleProcessHistory&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div>  -->
		<display:table name="sessionScope.handeledReplys" id="currentRowObject" pagesize="15" defaultsort="5" defaultorder="descending" sort="list">
			<logic:notEmpty name="currentRowObject">
			<bean:define id="troubleId" name="currentRowObject"
				property="id" />
			<bean:define id="sendUserId" name="currentRowObject"
				property="send_man_id" />
			<bean:define id="troubleState" name="currentRowObject"
				property="trouble_state" />
		    <display:column media="html" title="���ϵ����"  sortable="true">
      			<a href="javascript:toViewForm('<bean:write name="currentRowObject" property="id"/>')"><bean:write name="currentRowObject" property="trouble_id"/></a> 
      		</display:column>	
			<display:column property="trouble_name" sortable="true" title="��������" headerClass="subject" />
			<display:column property="trouble_send_man" sortable="true" title="�����ɷ���" headerClass="subject" />
			<display:column property="trouble_start_time" sortable="true" title="���Ϸ���ʱ��" headerClass="subject"/>
			<display:column property="trouble_send_time" sortable="true" title="�����ɷ�ʱ��" headerClass="subject" />
			<display:column property="is_great_trouble" sortable="true" title="�ش����" headerClass="subject" maxLength="10"/>
			 <display:column media="html" title="����" >
					<c:if test="${sessionScope.LOGIN_USER.deptype== '1' && troubleState=='0'}">
						<a href="javascript:toCancelForm('${troubleId }')">ȡ��</a>
					</c:if>
             </display:column>
             </logic:notEmpty>
		</display:table>
	</body>
</html>
