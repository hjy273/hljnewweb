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
		<input type="hidden" name="env" value="${env }" />
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
		<p>
			�������⣺
			<bean:write name="bean" property="sendtopic" />
			<br />
			�������
			<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
				keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
			<apptag:quickLoadList cssClass="" name=""
				listName="dispatch_task_con" keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
			<br />
			����˵����
			<bean:write name="bean" property="sendtext" />
			<br />
			����Ҫ��
			<bean:write property="replyRequest" name="bean" />
			<br />
			����ʱ�ޣ�
			<bean:write property="processterm" name="bean" />
			<br />
			ת���ˣ�
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
			ת�ɱ�ע��
			<html:textarea property="remark" title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�"
				style="width:80%" rows="6" styleClass="textarea"></html:textarea>
		</p>
		<p>
			<html:submit property="action">�ύ</html:submit>
			<input type="button" value="���ش���" onclick="goBack()">
		</p>
	</html:form>
</body>
</html>
