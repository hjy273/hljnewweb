<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
function isValidForm() {
	if(document.NoticeBean.title.value ==""){
		alert("���ⲻ��Ϊ��!! ");
		return false;
	}
	if(document.NoticeBean.content.value ==""){
		alert("���ݲ���Ϊ��!! ");
		return false;
	}
	return true;
}
function issue(){
	NoticeBean.isissue.value='y';
	if(isValidForm()){
		NoticeBean.submit();
	}
	
}
function save(){
    NoticeBean.isissue.value='n';
	if(isValidForm()){
		NoticeBean.submit();
	}
}

//-->
</script>
<template:titile value="�༭������Ϣ"/>

<html:form  method="Post" action="/NoticeAction.do?method=updateNotice" encType="multipart/form-data">
    <template:formTable>
		<template:formTr name="����" >
            <html:text property="title" name="notice" styleClass="inputtext" style="width:300" maxlength="25"/>
        </template:formTr>

		<template:formTr name="����" >
        	<html:select property="type" name="notice" styleClass="inputtext">
        		<html:option value="ϵͳ����">ϵͳ����</html:option>
        		<html:option value="����">����</html:option>
        	 	<html:option value="֪ͨ">֪ͨ</html:option>
        	 	<html:option value="����">����</html:option>
        	</html:select>        
        </template:formTr>
		
		<template:formTr name="��Ҫ�̶�" >
        	<html:select property="grade" name="notice" styleClass="inputtext">
        		<html:option value="һ��">һ��</html:option>
        		<html:option value="��Ҫ">��Ҫ</html:option>
        	</html:select>        
        </template:formTr>
        <template:formTr name="����" >
        	  <logic:empty name="notice" property="fileinfo">
		      </logic:empty>
		      <logic:notEmpty name="notice" property="fileinfo">
		        <bean:define id="temp" name="notice" property="fileinfo" type="java.lang.String"/>
		        <apptag:listAttachmentLink fileIdList="<%=temp%>" />
		      </logic:notEmpty><br>
			<input type="file" name="uploadFile[0].file"/> <span style="color:red;">* �ɵĸ�������ɾ��</span>
        </template:formTr>
        <template:formTr name="����">
        	<html:textarea property="content" styleClass="textarea" onkeyup="this.value=this.value.substr(0,1500)" title="��ע��Ϣ�1500������" name="notice" rows="5" cols="50"></html:textarea>
        </template:formTr>
		<html:hidden property="isissue" name="notice"/>
		<html:hidden property="id" name="notice"/>
		<html:hidden property="fileinfo" name="notice"/>
		 
		<template:formSubmit>
            <td >
            	<html:button property="action" styleClass="button" onclick="issue()">��������</html:button>
            </td>
            <td >
                <html:button styleClass="button"  onclick="save()" property="action">����</html:button>
            </td>
            <td >
                <html:reset styleClass="button"  >ȡ��</html:reset>
            </td>
        </template:formSubmit>


   </template:formTable>
</html:form>
