<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/wap/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<script type="" language="javascript">
		goBack=function(){
			var url="${sessionScope.S_BACK_URL}";
			location=url;
		}
		toRefuseConfirmForm=function(signInId){
			var actionUrl="${ctx}/wap/dispatchtask/sign_in_task.do?method=refuseConfirmTaskForm&&env=${env}";
			var queryString="sign_in_id="+signInId+"&dispatch_id=${dispatch_id}";
			location=actionUrl+"&"+queryString+"&rnd="+Math.random();
		}
		</script>
	</head>
	<body>
		<!--显示一个派单详细信息页面-->
		<br>
		<template:titile value="任务单拒签确认" />
		<p>
			任务主题：
			<bean:write name="bean" property="sendtopic" />
			&nbsp;&nbsp; 
			<br/>
			工作类别：
			<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
				keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
			<apptag:quickLoadList cssClass="" name=""
				listName="dispatch_task_con" keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
		</p>
		<p>
			<logic:notEmpty name="sign_in_list">
				<logic:iterate id="oneSignInTask" name="sign_in_list">
					<bean:define id="signInId" name="oneSignInTask" property="signinid" />
						拒签人：<bean:write name="oneSignInTask" property="signinusername" />
						<br/>
						拒签时间：<bean:write name="oneSignInTask" property="signintime" />
						&nbsp;&nbsp; 
						<a href="javascript:toRefuseConfirmForm('${signInId }')"> 
						<br/>
						拒签确认
						</a>
					<br />
				</logic:iterate>
			</logic:notEmpty>
		</p>
		<p align="center">
			<input type="button" onclick="goBack()" value="返回待办">
		</p>
	</body>
</html>
