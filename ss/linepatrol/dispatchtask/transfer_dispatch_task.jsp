<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
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
    alert("ת�ɱ�ע����̫�࣡���ܳ���510������");
    return false;
  }
  if(typeof(form.acceptuserid)!="undefined"){
  if(form.acceptuserid.value==""){
    alert("û��ѡ��ת�ɵ��û���");
	return false;
  }
  var users=form.acceptuserid.value.split(",");
  if(users.length>2||(users.length==2&&users[1]!="")){
	alert("ֻ����ת��һ���û���");
	return false;
  }
  }
  return true;
}
	</script>
</head>
<body>
	<br>
	<template:titile value="ת������" />
	<html:form action="/dispatchtask/sign_in_task.do?method=signInTask"
		styleId="addApplyForm" enctype="multipart/form-data"
		onsubmit="return validate(this);">
		<input type="hidden" name="sendtaskid"
			value="<bean:write name="bean" property="id"/>" />
		<input type="hidden" name="result" value="02" />
		<html:hidden name="bean" property="sendtopic" />
		<input type="hidden" name="sendacceptdeptid"
			value="<bean:write name="bean" property="subtaskid"/>" />
		<input type="hidden" name="result" value="ת��" />
		<input type="hidden" name="deptid"
			value="${sessionScope.LOGIN_USER.userID }" />
		<input type="hidden" name="userid"
			value="${sessionScope.LOGIN_USER.deptID }" />
		<input type="hidden" name="isTransfer" value="1" />
		<table id="tableID" width="90%" border="0" align="center"
			cellpadding="3" cellspacing="0" class="tabout">
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
					�ɵ����ţ�
				</td>
				<td class="tdl">
					${sessionScope.LOGIN_USER.deptName }
				</td>
				<td class="tdr">
					�� �� �ˣ�
				</td>
				<td class="tdl">
					${sessionScope.LOGIN_USER.userName }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					�������⣺
				</td>
				<td class="tdl" colspan="3">
					<bean:write name="bean" property="sendtopic" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					����˵����
				</td>
				<td class="tdl" colspan="3">
					<bean:write name="bean" property="sendtext" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					����Ҫ��
				</td>
				<td class="tdl" colspan="5">
					<bean:write property="replyRequest" name="bean" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					����ʱ�ޣ�
				</td>
				<td class="tdl" colspan="5">
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
			<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
				<apptag:processorselect
					inputName="acceptdeptid,user,mobileId,acceptuserid"
					spanId="userSpan" displayType="mobile" inputType="radio" />
			</c:if>
			<c:if test="${sessionScope.LOGIN_USER.deptype=='2'}">
				<apptag:processorselect
					inputName="acceptdeptid,user,mobileId,acceptuserid"
					spanId="userSpan" />
			</c:if>
			<tr class=trcolor>
				<td class="tdr">
					ת�ɱ�ע��
				</td>
				<td class="tdl" colspan="5">
					<html:textarea property="remark"
						title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" style="width:80%" rows="6"
						styleClass="textarea"></html:textarea>
				</td>
			</tr>
		</table>
		<table align="center">
			<tr>
				<td colspan="2" class="tdc">
					<template:formSubmit>
						<td>
							<html:submit styleClass="button" property="action">�ύ</html:submit>
						</td>
						<td>
							<input type="button" value="����" class="button" onclick="goBack()">
						</td>
					</template:formSubmit>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html>
