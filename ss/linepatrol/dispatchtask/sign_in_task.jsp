<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
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
			<input type="hidden" name="sendtaskid"
				value="<bean:write name="bean" property="id"/>" />
			<input type="hidden" name="sendacceptdeptid"
				value="<bean:write name="bean" property="subtaskid"/>" />
			<input name="sendtopic" type="hidden"
				value="<bean:write property="sendtopic" name="bean" />" />
			<input type="hidden" name="isTransfer" value="0" />
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" width="15%">
						�ɵ���ˮ�ţ�
					</td>
					<td class="tdl" width="35%">
						<bean:write property="serialnumber" name="bean" />
					</td>
					<td class="tdr" width="15%">
						�������
					</td>
					<td class="tdl" width="35%">
						<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
							keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
						<apptag:quickLoadList cssClass="" name="" listName="dispatch_task_con"
							keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						�ɵ�ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write property="sendtime" name="bean" />
					</td>
					<td class="tdr">
						�ɵ����ţ�
					</td>
					<td class="tdl">
						<bean:write name="bean" property="senddeptname" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						�� �� �ˣ�
					</td>
					<td class="tdl">
						<bean:write name="bean" property="sendusername" />
					</td>
					<td class="tdr">
						�����ź������ˣ�
					</td>
					<td class="tdl">
						<logic:present name="bean" property="acceptUserList">
							<logic:iterate id="oneAcceptUser" name="bean"
								property="acceptUserList">
								���ţ�<bean:write name="oneAcceptUser" property="departname" />
								�û���<bean:write name="oneAcceptUser" property="username" />
								<br />
							</logic:iterate>
						</logic:present>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						�������⣺
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="sendtopic" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						����˵����
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="sendtext" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						����Ҫ��
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="replyRequest" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						����ʱ�ޣ�
					</td>
					<td class="tdl" colspan="3">
						<bean:write property="processterm" name="bean" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						���񸽼���
					</td>
					<td class="tdl" colspan="3">
						<apptag:upload cssClass="" entityId="${bean.id}"
							entityType="LP_SENDTASK" state="look" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ǩ�ս����
					</td>
					<td class="tdl" colspan="3">
						<input name="result" value="00" type="radio" checked>
						ǩ��
						<input name="result" value="01" type="radio">
						��ǩ
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ǩ�ձ�ע��
					</td>
					<td class="tdl" colspan="3">
						<html:textarea property="remark"
							title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" style="width:80%" rows="6"
							styleClass="textarea"></html:textarea>
					</td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td>
						<html:submit styleClass="button">ǩ��</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">����</html:reset>
					</td>
					<td>
						<input type="button" value="����" class="button" onclick="goBack()">
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
