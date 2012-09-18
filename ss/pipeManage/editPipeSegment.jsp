<%@include file="/common/header.jsp"%>



<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
String strRegion = userinfo.getRegionID();
%>
<script language="javascript" type="">

function isValidForm() {
	    var myform = document.forms[0];
    if(myform.pipeno.value.replace(/(^\s*)|(\s*$)/g,"")==''){
       alert('�ܵ��α�Ų���Ϊ�գ�');
        return false;
    }
    if(myform.pipename.value.replace(/(^\s*)|(\s*$)/g,"")==''){
       alert('�ܵ������Ʋ���Ϊ�գ�');
        return false;
    }
    if(myform.length.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
       alert('���벻��Ϊ�գ�');
        return false;
    }
    var patrn1 =/^([0-9]{1,20})(.)([0-9]{1,20})$/; 
    if (!patrn1.exec(myform.length.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('������������֣�');
       return false;
    }
    if(myform.pipehole.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
	alert('�ܿ�������Ϊ�գ�');
        return false;
    }
   var patrn2 =/^[0-9]{1,20}$/; 
    if (!patrn2.exec(myform.pipehole.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('�ܿ������������֣�');
        return false;
    }
    if(myform.subpipehole.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
	alert('�ӹ�������Ϊ�գ�');
        return false;
    }
    if (!patrn2.exec(myform.subpipehole.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('�ӹ������������֣�');
        return false;
    }
    if(myform.unuserpipe.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
       alert('δ���ӹ�������Ϊ�գ�');
        return false;
    }
  if (!patrn2.exec(myform.unuserpipe.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('δ���ӹ������������֣�');
        return false;
    }
	return true;
}


</script>

<body>
<template:titile value="�޸Ĺܵ�����Ϣ"/>

<html:form onsubmit="return isValidForm()" method="Post" action="/PipeSegmentAction.do?method=updatePipeSegment">
  <template:formTable contentwidth="220" namewidth="220">
    <template:formTr name="�ܵ��� ���">
    <html:hidden property="id" />
      <html:text property="pipeno"  styleClass="inputtext" style="width:160" maxlength="40"/>
    </template:formTr>
    
     <template:formTr name="�ܵ�������">
    <html:text property="pipename" styleClass="inputtext" style="width:160" maxlength="240"/>
    </template:formTr>
     
      <template:formTr name="��ά��λ">
     <html:select property="contractorid" styleClass="inputtext" style="width:160">
        <html:options collection="contractorlist" property="contractorid" labelProperty="contractorname"/>
      </html:select>
    </template:formTr>       
   
    <template:formTr name="ά������">
    <html:text property="county" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="Ƭ��">
    <html:text property="area" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="��������">
    <html:text property="town" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="����">
    <html:text property="length" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="�ܵ�����">
    <html:text property="material" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="��Ȩ">
    <html:text property="right" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
    <template:formTr name="�ܿ���">
    <html:text property="pipehole" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
    <template:formTr name="�ܿ׹��">
    <html:text property="pipetype" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
    <template:formTr name="�ӹ�����">
    <html:text property="subpipehole" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
    <template:formTr name="δ���ӹ���">
    <html:text property="unuserpipe" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
     <template:formTr name="�Ƿ�����">
     <html:select property="isaccept" styleClass="inputtext" style="width:160">
         <html:option value="0">��</html:option>
         <html:option value="1">��</html:option>
       </html:select>
    </template:formTr>
    
    <template:formTr name="��ע">
    <html:text property="remark1" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
     <template:formTr name="��ע2">
    <html:text property="remark2" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
     <template:formTr name="ͼֽ���">
    <html:text property="bluepointno" styleClass="inputtext" style="width:160" maxlength="120"/>
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
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
</body>
