<%@include file="/common/header.jsp"%>



<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
String strRegion = userinfo.getRegionID();
%>
<script language="javascript" type="">

function isValidForm() {
	var myform = document.forms[0];
    if(myform.cableno.value==''){
       alert('���¶α�Ų���Ϊ�գ�');
        return false;
    }
    if(myform.cablename.value==''){
       alert('�������Ʋ���Ϊ�գ�');
        return false;
    }
    if(myform.cablelinename.value==''){
       alert('���¶����Ʋ���Ϊ�գ�');
        return false;
    }
	if(isNaN(myform.fibernumber.value)){
       alert('��о�������������֣�');
        return false;
    }
    if(isNaN(myform.cablelength.value)){
       alert('Ƥ�����������֣�');
        return false;
    }
     if(isNaN(myform.unusecable.value)){
       alert('Ԥ�����������֣�');
        return false;
    }
	if(isNaN(myform.filberlength.value)){
       alert('��о���ȱ��������֣�');
        return false;
    }

	return true;
}


</script>

<body>
<logic:equal value="e" name="TYPE">
<template:titile value="�޸Ĺ��¶���Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/CableAction.do?method=updateCable">
  <template:formTable contentwidth="220" namewidth="220">
    <template:formTr name="���¶α��">
    <html:hidden property="id" />
      <html:text property="cableno"  styleClass="inputtext" style="width:160" maxlength="40"/>
    </template:formTr>
           
   <template:formTr name="��ά��λ" isOdd="false">
      <html:select property="contractorid" styleClass="inputtext" style="width:160">
        <html:options collection="contractorlist" property="contractorid" labelProperty="contractorname"/>
      </html:select>
    </template:formTr>
    <template:formTr name="Ƭ��">
      <html:text property="area" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
    <template:formTr name="����">
      <html:text property="county" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="ϵͳ����">
      <html:text property="systemname" styleClass="inputtext" style="width:160" maxlength="400"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="cablename" styleClass="inputtext" style="width:160" maxlength="200"/>
    </template:formTr>
    <template:formTr name="���¶�����">
      <html:text property="cablelinename" styleClass="inputtext" style="width:160" maxlength="200"/>
    </template:formTr>
  <template:formTr name="���跽ʽ">
      <html:text property="laytype" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="company" styleClass="inputtext" style="width:160" maxlength="200"/>
    </template:formTr>
     <template:formTr name="ʩ����λ">
      <html:text property="construct" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="��Ȩ����">
      <html:text property="property" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="���·�ʽ">
      <html:text property="cabletype" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
     <template:formTr name="Ͷ������">
      <html:text  property="createtime" styleClass="inputtext" style="width:160" maxlength="20"/>
    </template:formTr>
    <template:formTr name="��о�ͺ�">
      <html:text property="fibertype" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="��о����">
      <html:text property="fibernumber" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
     <template:formTr name="Ƥ��">
      <html:text property="cablelength" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
     <template:formTr name="Ԥ��">
      <html:text property="unusecable" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
    <template:formTr name="��ע">
      <html:text property="remark" styleClass="inputtext" style="width:160;height:100;" maxlength="120"/>
    </template:formTr>
     <template:formTr name="�Ƿ�����">
      <html:select property="isaccept" styleClass="inputtext" style="width:160">
         <html:option value="0">��</html:option>
         <html:option value="1">��</html:option>
       </html:select>
    </template:formTr>
     <template:formTr name="ͼֽ���">
      <html:text property="blueprintno" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
     <template:formTr name="��о����">
      <html:text property="fiberlength" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
    
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">�޸�</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
      <td>
        <input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
      </td>

    </template:formSubmit>
  </template:formTable>
 <iframe name="hiddenInfoFrame" style="display:none"></iframe>
</html:form>
</logic:equal>
<logic:notEqual value="e" name="TYPE">
<template:titile value="�鿴���¶���ϸ��Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/CableAction.do?method=updateCable">
<template:formTable contentwidth="220" namewidth="220">
    <template:formTr name="���¶α��">
    	<bean:write name="cableBean" property="id"/>
    </template:formTr>
    <template:formTr name="��ά��λ">
    	<bean:write name="cableBean" property="contractorid"/>
    </template:formTr>
    <template:formTr name="Ƭ��">
    	<bean:write name="cableBean" property="area"/>
    </template:formTr>
    <template:formTr name="����">
    	<bean:write name="cableBean" property="county"/>
    </template:formTr>
    <template:formTr name="ϵͳ����">
    	<bean:write name="cableBean" property="systemname"/>
    </template:formTr>
    <template:formTr name="��������">
    	<bean:write name="cableBean" property="cablename"/>
    </template:formTr>
    <template:formTr name="���¶�����">
    	<bean:write name="cableBean" property="cablelinename"/>
    </template:formTr>
    <template:formTr name="A��վ">
    	<bean:write name="cableBean" property="apoint"/>
    </template:formTr>
    <template:formTr name="Z��վ">
    	<bean:write name="cableBean" property="zpoint"/>
    </template:formTr>
    <template:formTr name="���跽ʽ">
    	<bean:write name="cableBean" property="laytype"/>
    </template:formTr>
    <template:formTr name="��������">
    	<bean:write name="cableBean" property="company"/>
    </template:formTr>
    <template:formTr name="ʩ����λ">
    	<bean:write name="cableBean" property="construct"/>
    </template:formTr>
    <template:formTr name="���·�ʽ">
    	<bean:write name="cableBean" property="cabletype"/>
    </template:formTr>
    <template:formTr name="Ͷ������">
    	<bean:write name="cableBean" property="createtime"/>
    </template:formTr>
    <template:formTr name="��о�ͺ�">
    	<bean:write name="cableBean" property="fibertype"/>
    </template:formTr>
    <template:formTr name="��о����">
    	<bean:write name="cableBean" property="fibernumber"/>
    </template:formTr>
    <template:formTr name="Ƥ��">
    	<bean:write name="cableBean" property="cablelength"/>
    </template:formTr>
    <template:formTr name="Ԥ��">
    	<bean:write name="cableBean" property="unusecable"/>
    </template:formTr>
    <template:formTr name="ͼֽ���">
    	<bean:write name="cableBean" property="blueprintno"/>
    </template:formTr>
    <template:formTr name="��о����">
    	<bean:write name="cableBean" property="fiberlength"/>
    </template:formTr>
    <template:formTr name="�Ƿ�����">
    	<bean:write name="cableBean" property="isaccept"/>
    </template:formTr>
    <template:formTr name="��ע">
    	<bean:write name="cableBean" property="remark"/>
    </template:formTr>
    </template:formTable>
    <template:formSubmit>
      <td>
       <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
      </td>
    </template:formSubmit>
    </html:form>
    </logic:notEqual>
</body>
