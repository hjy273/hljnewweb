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


function validate(form){
	if(form.validateresult.value=="2"&&form.approverid.value==""){
		alert("û��ѡ��ת���ˣ�");
		return false;
	}
  if(valCharLength(form.validateremark.value)>1020){
    alert("ת��ע����̫�࣡���ܳ���510������");
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
		<template:titile value="���񵥷���ת��" />
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
				�����ˣ�
				<bean:write name="replybean" property="replyusername" />
				<br />
				���������
				<c:if test="${replybean.replyresult=='00'}">δ���</c:if>
				<c:if test="${replybean.replyresult=='01'}">�������</c:if>
				<c:if test="${replybean.replyresult=='02'}">�������</c:if>
				<c:if test="${replybean.replyresult=='03'}">ȫ�����</c:if>
				<br />
				����ʱ�䣺
				<bean:write property="replytime" name="replybean" />
				<br />
				�Ƿ�ʱ��
				<c:if test="${replybean.replyTimeSign=='-'}">
					<font color="#FF0000">��ʱ${replybean.replyTimeLength }</font>
				</c:if>
				<c:if test="${replybean.replyTimeSign=='+'}">
					<font color="#339999">��ǰ${replybean.replyTimeLength }</font>
				</c:if>
				<br />
				������ע��
				<bean:write property="replyremark" name="replybean" />
			</p>
			<p>
				ת���ˣ�
				<c:if test="${sessionScope.APPROVER_INPUT_NAME_MAP!=null}">
					<c:forEach var="oneItem"
						items="${sessionScope.APPROVER_INPUT_NAME_MAP}">
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
				ת��ע��
				<input name="validateresult" value="2" type="hidden" />
				<html:textarea property="validateremark"
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
