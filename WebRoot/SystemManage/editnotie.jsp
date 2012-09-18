<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
<!--
function isValidForm() {
	if(document.NoticeBean.title.value ==""){
		alert("标题不能为空!! ");
		return false;
	}
	if(document.NoticeBean.content.value ==""){
		alert("内容不能为空!! ");
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
<template:titile value="编辑公告信息"/>

<html:form  method="Post" action="/NoticeAction.do?method=updateNotice" encType="multipart/form-data">
    <template:formTable>
		<template:formTr name="标题" >
            <html:text property="title" name="notice" styleClass="inputtext" style="width:300" maxlength="25"/>
        </template:formTr>

		<template:formTr name="类型" >
        	<html:select property="type" name="notice" styleClass="inputtext">
        		<html:option value="系统升级">系统升级</html:option>
        		<html:option value="公告">公告</html:option>
        	 	<html:option value="通知">通知</html:option>
        	 	<html:option value="新闻">新闻</html:option>
        	</html:select>        
        </template:formTr>
		
		<template:formTr name="重要程度" >
        	<html:select property="grade" name="notice" styleClass="inputtext">
        		<html:option value="一般">一般</html:option>
        		<html:option value="重要">重要</html:option>
        	</html:select>        
        </template:formTr>
        <template:formTr name="附件" >
        	  <logic:empty name="notice" property="fileinfo">
		      </logic:empty>
		      <logic:notEmpty name="notice" property="fileinfo">
		        <bean:define id="temp" name="notice" property="fileinfo" type="java.lang.String"/>
		        <apptag:listAttachmentLink fileIdList="<%=temp%>" />
		      </logic:notEmpty><br>
			<input type="file" name="uploadFile[0].file"/> <span style="color:red;">* 旧的附件将被删除</span>
        </template:formTr>
        <template:formTr name="内容">
        	<html:textarea property="content" styleClass="textarea" onkeyup="this.value=this.value.substr(0,1500)" title="备注信息最长1500个汉字" name="notice" rows="5" cols="50"></html:textarea>
        </template:formTr>
		<html:hidden property="isissue" name="notice"/>
		<html:hidden property="id" name="notice"/>
		<html:hidden property="fileinfo" name="notice"/>
		 
		<template:formSubmit>
            <td >
            	<html:button property="action" styleClass="button" onclick="issue()">立即发布</html:button>
            </td>
            <td >
                <html:button styleClass="button"  onclick="save()" property="action">保存</html:button>
            </td>
            <td >
                <html:reset styleClass="button"  >取消</html:reset>
            </td>
        </template:formSubmit>


   </template:formTable>
</html:form>
