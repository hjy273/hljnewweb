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
  if(valCharLength(form.confirmRemark.value)>1020){
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
		<template:titile value="任务单拒签确认" />
		<html:form
			action="/dispatchtask/sign_in_task.do?method=refuseConfirmTask"
			styleId="addApplyForm" onsubmit="return validate(this);"
			enctype="multipart/form-data">
			<html:hidden property="sendtopic" name="bean" />
			<input type="hidden" name="env" value="${env }" />
			<input type="hidden" name="dispatch_id" value="${dispatch_id }" />
			<input type="hidden" name="sendtaskid" value="${dispatch_id }" />
			<input type="hidden" name="sendacceptdeptid"
				value="<bean:write name="bean" property="subtaskid"/>" />
			<input type="hidden" name="signInId"
				value="<bean:write name="endorsebean" property="id"/>" />
			<input type="hidden" name="refuseUserId"
				value="<bean:write name="endorsebean" property="userid"/>" />
			<p>
				拒签人：
				<bean:write name="endorsebean" property="endorseusername" />
				<br/>
				拒签时间：
				<bean:write property="time" name="endorsebean" />
				<br/>
				备注：
				<bean:write property="remark" name="endorsebean" />
			</p>
			<p>
				拒签确认结果：
				<input name="confirmResult" value="0" type="radio" checked />
				通过
				<input name="confirmResult" value="1" type="radio" />
				未通过
				<br/>
				拒签确认备注：
				<html:textarea property="confirmRemark"
					title="请不要超过256个汉字或者512个英文字符，否则将截断。" style="width:80%" rows="5"
					styleClass="textarea"></html:textarea>
			</p>
			<p>
				<html:submit>提交</html:submit>
				<input type="button" value="返回待办" onclick="goBack();">
			</p>
		</html:form>
	</body>
</html>
