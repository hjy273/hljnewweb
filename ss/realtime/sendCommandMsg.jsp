<%@include file="/common/header.jsp"%>
<%
String id = request.getParameter("id");
String sim = request.getParameter("sim");
String pw = request.getParameter("pw");
String tm = request.getParameter("tm");
%>
<script type="text/javascript" language="javascript">
	function isValidForm(){
	 	 var messageValue = SMSBean.message.value.length;
	  	//alert(SMSBean.message.value.length);
	  	if(messageValue>100){
	   	 	alert("��Ϣ���ݲ��ܶ���100����!");
	  	  	return false;
	  	}
	}
	function sendSm(){
		url = "${ctx}/sendsm.do?method=dispatch&mobile="+$("simid").value+"&content="+$("message").value;
		new Ajax.Request(url,{
    	method:'post',
    	evalScripts:true,
    	onSuccess:function(transport){
				alert(transport.responseText);
    	},
    	onFailure: function(){ alert('���Ͷ���ʧ�ܣ�') }
	 	});
	}

</script>
<template:titile value="���Ͷ���"/>
<html:form  method="Post" action="/smsAction.do?method=sendCommandSMS"  onsubmit="return isValidForm()">
  <template:formTable>
    <template:formTr name="�豸�ֻ�����" isOdd="false">
      <html:text property="simid" styleClass="inputtext" style="width:200" value="<%=sim%>"/>
	  <html:hidden property="deviceid" value="<%=id%>"/>
	  <html:hidden property="password" value="<%=pw%>"/>
      <html:hidden property="terminalmodel" value="<%=tm%>"/>
    </template:formTr>
    <template:formTr name="������Ϣ">
      <html:textarea property="message" styleClass="forTextArea2" style="width:200"/>
    </template:formTr>
    <!-- template:formTr name="�Ƿ���Ҫ�ظ�">
      <html:checkbox property="needreply" value="Y" />
    <--/template:formTr-->
    <template:formSubmit>
      <td>
        <html:button styleClass="button" property="����" onclick="sendSm()">����</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
