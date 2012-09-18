<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
			 toUpdateTroubleForm=function(idValue){
	            window.location.href = "${ctx}/troubleInfoAction.do?method=editTroubleForm&troubleid="+idValue;
			}
		toCancelForm=function(idValue){
			var url;
			if(confirm("确定要取消该故障流程吗？")){
				url="${ctx}/troubleInfoAction.do?method=cancelTroubleForm";
				var queryString="troubleId="+idValue;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
		<title>临时故障一览表</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />
		 <bean:size id="size"  name="tempTroubles" />
		<template:titile value="临时故障派单一览表  (<font color='red'>${size}条</font>)" />
		<display:table name="sessionScope.tempTroubles" id="currentRowObject" pagesize="15">
			<bean:define id="sendUserId" name="currentRowObject"
				property="send_man_id" />
			<display:column property="troubleName" sortable="true" title="故障名称" headerClass="subject" />
			<c:if test="${ not empty currentRowObject.troubleStartTime}">
				<fmt:formatDate value="${currentRowObject.troubleStartTime}" var="stime" pattern="yyyy/MM/dd hh:mm:dd"/>
				<display:column value="${stime}" sortable="true" title="故障发生时间" headerClass="subject"/>
			</c:if>
			<c:if test="${empty currentRowObject.troubleStartTime}">
			    <display:column value="" sortable="true" title="故障发生时间" headerClass="subject"/>
			</c:if>
			<c:if test="${currentRowObject.isGreatTrouble==1}">
						<display:column value="是" sortable="true" title="重大故障" headerClass="subject" maxLength="10"/>
			</c:if>
			<c:if test="${currentRowObject.isGreatTrouble==0}">
						<display:column value="否" sortable="true" title="重大故障" headerClass="subject" maxLength="10"/>
			</c:if>
			 <display:column media="html" title="操作" >
	            	 <a href="javascript:toUpdateTroubleForm('${currentRowObject.id}')">修改派单</a>
					<c:if test="${sessionScope.LOGIN_USER.userID==sendUserId}">
						| <a href="javascript:toCancelForm('${currentRowObject.id }')">取消</a>
					</c:if>
             </display:column>
		</display:table>
	</body>
</html>
