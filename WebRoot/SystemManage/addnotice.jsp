<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<html>
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
		//NoticeBean.enctype="multipart/form-data";
		NoticeBean.submit();
	}
	
}
function save(){
    NoticeBean.isissue.value='n';
	if(isValidForm()){
		//NoticeBean.enctype="multipart/form-data";
		NoticeBean.submit();
	}
}

//-->
</script>
<body>

<template:titile value="���淢��"/>
<html:form  action="/NoticeAction?method=addNotice" enctype="multipart/form-data">
    <template:formTable>
		
		<template:formTr name="����" >
            <html:text property="title"  styleClass="inputtext" style="width:300" maxlength="25"/>
        </template:formTr>
        
        <template:formTr name="����" >
        	<html:select property="type" styleClass="inputtext">
        		<html:option value="ϵͳ����">ϵͳ����</html:option>
        		<html:option value="����">����</html:option>
        	 	<html:option value="֪ͨ">֪ͨ</html:option>
        	 	<html:option value="����">����</html:option>
        	</html:select>        
        </template:formTr>
		
		<template:formTr name="��Ҫ�̶�" >
        	<html:select property="grade" styleClass="inputtext">
        		<html:option value="һ��">һ��</html:option>
        		<html:option value="��Ҫ">��Ҫ</html:option>
        	</html:select>        
        </template:formTr>
        <template:formTr name="����" >
			<input type="file" name="uploadFile[0].file"/>
        </template:formTr>
		
        <template:formTr name="����">
        	<html:textarea property="content" styleClass="textarea" onkeyup="this.value=this.value.substr(0,1500)" title="��ע��Ϣ�1500������" rows="5" cols="50"></html:textarea>
        </template:formTr>
		<html:hidden property="isissue" />
		
		<template:formSubmit>
            <td >
            	<html:button property="action1" styleClass="button" onclick="issue()">��������</html:button>
            </td>
            <td >
                <html:button  property="action2" styleClass="button" onclick="save()">����</html:button>
            </td>
            <td >
                <html:reset styleClass="button"  >ȡ��</html:reset>
            </td>
        </template:formSubmit>
   </template:formTable>
</html:form>
</body>
</html>
