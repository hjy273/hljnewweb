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
  if(valCharLength(form.validateremark.value)>1020){
    alert("审核备注字数太多！不能超过510个汉字");
    return false;
  }
	var allCheck = document.getElementsByName('ifCheck');
		var length = allCheck.length;
		
		for(var i = 0; i < length; i++) {
			//var index = i + 1;
			var index = allCheck[i].value;
			var pathText = document.getElementById('uploadFile[' + index + '].file');
			var path = pathText.value;
			if(path.length == 0) {
				alert("请选择要上传的附件的路径，\n如果没有要上传的附件请删除!");
				pathText.focus();
				return false;
			}
		}
}
		</script>
	</head>
	<body>
		<!--显示派单验证的详细信息,进行验证-->
		<template:titile value="任务单反馈审核" />
		<html:form action="/dispatchtask/check_task.do?method=checkTask"
			styleId="addApplyForm" onsubmit="return validate(this);"
			enctype="multipart/form-data">
			<html:hidden property="sendtopic" name="bean" />
			<input type="hidden" name="env" value="${env }" />
			<input type="hidden" name="dispatch_id" value="${dispatch_id }" />
			<input type="hidden" name="sendacceptdeptid"
				value="<bean:write name="bean" property="subtaskid"/>" />
			<input type="hidden" name="replyid"
				value="<bean:write name="replybean" property="id"/>" />
			<input type="hidden" name="replyuserid"
				value="<bean:write name="replybean" property="replyuserid"/>" />
			<p>
				反馈人：
				<bean:write name="replybean" property="replyusername" />
				反馈结果：
				<c:if test="${replybean.replyresult=='00'}">未完成</c:if>
				<c:if test="${replybean.replyresult=='01'}">部分完成</c:if>
				<c:if test="${replybean.replyresult=='02'}">基本完成</c:if>
				<c:if test="${replybean.replyresult=='03'}">全部完成</c:if>
				<br/>
				反馈时间：
				<bean:write property="replytime" name="replybean" />
				<br/>
				是否超时：
				<c:if test="${replybean.replyTimeSign=='-'}">
					<font color="#FF0000">超时${replybean.replyTimeLength }</font>
				</c:if>
				<c:if test="${replybean.replyTimeSign=='+'}">
					<font color="#339999">提前${replybean.replyTimeLength }</font>
				</c:if>
				<br/>
				反馈备注：
				<bean:write property="replyremark" name="replybean" />
			</p>
			<p>
				审核结果：
				<input name="validateresult" value="0" type="radio" checked />
				通过审核
				<input name="validateresult" value="1" type="radio" />
				未通过审核
				<br/>
				审核备注：
				<html:textarea property="validateremark"
					title="请不要超过256个汉字或者512个英文字符，否则将截断。" style="width:80%" rows="5"
					styleClass="textarea"></html:textarea>
			</p>
			<p>
				<html:submit >提交</html:submit>
				<input type="button" value="返回待办"  onclick="goBack();">
			</p>
		</html:form>
	</body>
</html>
