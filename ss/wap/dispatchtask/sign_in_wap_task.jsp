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

       //�����Ƿ�����
	 function valiD(id){
      	var mysplit = /^\d{1,6}[\.]{0,1}\d{0,2}$/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("����д�����ֲ��Ϸ�,����������");
            obj.focus();
            obj.value = "0.00";
            return false;
        }

    }
function validate(form){
  if(valCharLength(form.remark.value)>1020){
    alert("ǩ�ձ�ע����̫�࣡���ܳ���510������");
    return false;
  }
}
		</script>
	</head>
	<body>
		<!--��ʾһ����ǩ���ɵ���ϸ��Ϣҳ��-->
		<br>
		<template:titile value="ǩ���ɵ�" />
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
				�������⣺
				<bean:write property="sendtopic" name="bean" />
				<br/>
				�������
				<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
					keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
				<apptag:quickLoadList cssClass="" name=""
					listName="dispatch_task_con" keyValue="${bean.sendtype}"
					type="look"></apptag:quickLoadList>
				<br />
				����˵����
				<bean:write property="sendtext" name="bean" />
				<br />
				����Ҫ��
				<bean:write property="replyRequest" name="bean" />
				<br />
				����ʱ�ޣ�
				<bean:write property="processterm" name="bean" />
			</p>
			<p>
				ǩ�ս����
				<input name="result" value="00" type="radio" checked>
				ǩ��
				<input name="result" value="01" type="radio">
				��ǩ
				<br />
				ǩ�ձ�ע��
				<html:textarea property="remark"
					title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" style="width:80%" rows="6"
					styleClass="textarea"></html:textarea>
			</p>
			<p>
				<html:submit >ǩ��</html:submit>
				<input type="button" value="���ش���" onclick="goBack()">
			</p>
		</html:form>
	</body>
</html>
