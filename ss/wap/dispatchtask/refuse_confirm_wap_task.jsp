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
    alert("��˱�ע����̫�࣡���ܳ���510������");
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
				alert("��ѡ��Ҫ�ϴ��ĸ�����·����\n���û��Ҫ�ϴ��ĸ�����ɾ��!");
				pathText.focus();
				return false;
			}
		}
}
		</script>
	</head>
	<body>
		<!--��ʾ�ɵ���֤����ϸ��Ϣ,������֤-->
		<template:titile value="���񵥾�ǩȷ��" />
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
				��ǩ�ˣ�
				<bean:write name="endorsebean" property="endorseusername" />
				<br/>
				��ǩʱ�䣺
				<bean:write property="time" name="endorsebean" />
				<br/>
				��ע��
				<bean:write property="remark" name="endorsebean" />
			</p>
			<p>
				��ǩȷ�Ͻ����
				<input name="confirmResult" value="0" type="radio" checked />
				ͨ��
				<input name="confirmResult" value="1" type="radio" />
				δͨ��
				<br/>
				��ǩȷ�ϱ�ע��
				<html:textarea property="confirmRemark"
					title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" style="width:80%" rows="5"
					styleClass="textarea"></html:textarea>
			</p>
			<p>
				<html:submit>�ύ</html:submit>
				<input type="button" value="���ش���" onclick="goBack();">
			</p>
		</html:form>
	</body>
</html>
