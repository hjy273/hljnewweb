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


		</script>
	</head>
	<body>
		<!--��ʾ�ɵ���֤����ϸ��Ϣ,������֤-->
		<template:titile value="���񵥷����鿴" />
		<html:form action="/dispatchtask/check_task.do?method=checkReadTask"
			styleId="addApplyForm" onsubmit="return true;"
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
				���������
				<c:if test="${replybean.replyresult=='00'}">δ���</c:if>
				<c:if test="${replybean.replyresult=='01'}">�������</c:if>
				<c:if test="${replybean.replyresult=='02'}">�������</c:if>
				<c:if test="${replybean.replyresult=='03'}">ȫ�����</c:if>
			</p>
			<p>
				����ʱ�䣺
				<bean:write property="replytime" name="replybean" />
				�Ƿ�ʱ��
				<c:if test="${replybean.replyTimeSign=='-'}">
					<font color="#FF0000">��ʱ${replybean.replyTimeLength }</font>
				</c:if>
				<c:if test="${replybean.replyTimeSign=='+'}">
					<font color="#339999">��ǰ${replybean.replyTimeLength }</font>
				</c:if>
			</p>
			<p>
				������ע��
				<bean:write property="replyremark" name="replybean" />
			</p>
			<p>
				<html:submit >���Ķ�</html:submit>
				<input type="button" value="���ش���" onclick="goBack();">
			</p>
		</html:form>
	</body>
</html>
