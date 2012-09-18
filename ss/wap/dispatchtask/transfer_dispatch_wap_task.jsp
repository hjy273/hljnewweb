<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/wap/header.jsp"%>
<head>
	<title>sendtask</title>
	<script type="" language="javascript">
		goBack=function(){
			var url="${sessionScope.S_BACK_URL}";
			location=url;
		}
	function valCharLength(Value){
      var j=0;
      var s = Value;
      for   (var   i=0;   i<s.length;   i++)
      {
              if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
              else   j++
      }
      return j;
    }
function validate(form){
  if(valCharLength(form.remark.value)>1020){
    alert("转派备注字数太多！不能超过510个汉字");
    return false;
  }
  if(typeof(form.acceptuserid)!="undefined"){
  if(form.acceptuserid.value==""){
    alert("没有选择转派的用户！");
	return false;
  }
  var users=form.acceptuserid.value.split(",");
  if(users.length>2||(users.length==2&&users[1]!="")){
	alert("只允许转派一个用户！");
	return false;
  }
  }
  return true;
}
	</script>
</head>
<body>
	<br>
	<template:titile value="转派任务" />
	<html:form action="/dispatchtask/sign_in_task.do?method=signInTask"
		styleId="addApplyForm" enctype="multipart/form-data"
		onsubmit="return validate(this);">
		<input type="hidden" name="env" value="${env }" />
		<input type="hidden" name="sendtaskid"
			value="<bean:write name="bean" property="id"/>" />
		<input type="hidden" name="result" value="02" />
		<html:hidden name="bean" property="sendtopic" />
		<input type="hidden" name="sendacceptdeptid"
			value="<bean:write name="bean" property="subtaskid"/>" />
		<input type="hidden" name="result" value="转派" />
		<input type="hidden" name="deptid"
			value="${sessionScope.LOGIN_USER.userID }" />
		<input type="hidden" name="userid"
			value="${sessionScope.LOGIN_USER.deptID }" />
		<input type="hidden" name="isTransfer" value="1" />
		<p>
			任务主题：
			<bean:write name="bean" property="sendtopic" />
			<br />
			工作类别：
			<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
				keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
			<apptag:quickLoadList cssClass="" name=""
				listName="dispatch_task_con" keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
			<br />
			任务说明：
			<bean:write name="bean" property="sendtext" />
			<br />
			反馈要求：
			<bean:write property="replyRequest" name="bean" />
			<br />
			处理时限：
			<bean:write property="processterm" name="bean" />
			<br />
			转派人：
			<c:if test="${sessionScope.INPUT_NAME_MAP!=null}">
				<c:forEach var="oneItem" items="${sessionScope.INPUT_NAME_MAP}">
					<c:if test="${oneItem.key!='span_value'}">
						<input name="${oneItem.key }" value="${oneItem.value }"
							type="hidden" />
					</c:if>
					<c:if test="${oneItem.key=='span_value'}">
							${oneItem.value }
						</c:if>
				</c:forEach>
			</c:if>
			<br />
			转派备注：
			<html:textarea property="remark" title="请不要超过256个汉字或者512个英文字符，否则将截断。"
				style="width:80%" rows="6" styleClass="textarea"></html:textarea>
		</p>
		<p>
			<html:submit property="action">提交</html:submit>
			<input type="button" value="返回待办" onclick="goBack()">
		</p>
	</html:form>
</body>
</html>
