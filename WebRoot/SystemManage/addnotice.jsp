<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<html>
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

<template:titile value="公告发布"/>
<html:form  action="/NoticeAction?method=addNotice" enctype="multipart/form-data">
    <template:formTable>
		
		<template:formTr name="标题" >
            <html:text property="title"  styleClass="inputtext" style="width:300" maxlength="25"/>
        </template:formTr>
        
        <template:formTr name="类型" >
        	<html:select property="type" styleClass="inputtext">
        		<html:option value="系统升级">系统升级</html:option>
        		<html:option value="公告">公告</html:option>
        	 	<html:option value="通知">通知</html:option>
        	 	<html:option value="新闻">新闻</html:option>
        	</html:select>        
        </template:formTr>
		
		<template:formTr name="重要程度" >
        	<html:select property="grade" styleClass="inputtext">
        		<html:option value="一般">一般</html:option>
        		<html:option value="重要">重要</html:option>
        	</html:select>        
        </template:formTr>
        <template:formTr name="附件" >
			<input type="file" name="uploadFile[0].file"/>
        </template:formTr>
		
        <template:formTr name="内容">
        	<html:textarea property="content" styleClass="textarea" onkeyup="this.value=this.value.substr(0,1500)" title="备注信息最长1500个汉字" rows="5" cols="50"></html:textarea>
        </template:formTr>
		<html:hidden property="isissue" />
		
		<template:formSubmit>
            <td >
            	<html:button property="action1" styleClass="button" onclick="issue()">立即发布</html:button>
            </td>
            <td >
                <html:button  property="action2" styleClass="button" onclick="save()">保存</html:button>
            </td>
            <td >
                <html:reset styleClass="button"  >取消</html:reset>
            </td>
        </template:formSubmit>
   </template:formTable>
</html:form>
</body>
</html>
