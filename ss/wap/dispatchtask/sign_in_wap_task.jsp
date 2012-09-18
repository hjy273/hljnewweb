<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/wap/header.jsp"%>
<html>
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

       //检验是否数字
	 function valiD(id){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("你填写的数字不合法,请重新输入");
            obj.focus();
            obj.value = "0.00";
            return false;
        }

    }
function validate(form){
  if(valCharLength(form.remark.value)>1020){
    alert("签收备注字数太多！不能超过510个汉字");
    return false;
  }
}
		</script>
	</head>
	<body>
		<!--显示一个待签收派单详细信息页面-->
		<br>
		<template:titile value="签收派单" />
		<html:form action="/dispatchtask/sign_in_task.do?method=signInTask"
			styleId="addApplyForm" onsubmit="return validate(this)"
			enctype="multipart/form-data">
			<input type="hidden" name="env" value="${env }" />
			<input type="hidden" name="sendtaskid"
				value="<bean:write name="bean" property="id"/>" />
			<input type="hidden" name="sendacceptdeptid"
				value="<bean:write name="bean" property="subtaskid"/>" />
			<input name="sendtopic" type="hidden"
				value="<bean:write property="sendtopic" name="bean" />" />
			<input type="hidden" name="isTransfer" value="0" />
			<p>
				任务主题：
				<bean:write property="sendtopic" name="bean" />
				<br/>
				工作类别：
				<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
					keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
				<apptag:quickLoadList cssClass="" name=""
					listName="dispatch_task_con" keyValue="${bean.sendtype}"
					type="look"></apptag:quickLoadList>
				<br />
				任务说明：
				<bean:write property="sendtext" name="bean" />
				<br />
				反馈要求：
				<bean:write property="replyRequest" name="bean" />
				<br />
				处理时限：
				<bean:write property="processterm" name="bean" />
			</p>
			<p>
				签收结果：
				<input name="result" value="00" type="radio" checked>
				签收
				<input name="result" value="01" type="radio">
				拒签
				<br />
				签收备注：
				<html:textarea property="remark"
					title="请不要超过256个汉字或者512个英文字符，否则将截断。" style="width:80%" rows="6"
					styleClass="textarea"></html:textarea>
			</p>
			<p>
				<html:submit >签收</html:submit>
				<input type="button" value="返回待办" onclick="goBack()">
			</p>
		</html:form>
	</body>
</html>
