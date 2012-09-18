<%@include file="/common/header.jsp"%>



<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
String strRegion = userinfo.getRegionID();
%>
<script language="javascript" type="">

function isValidForm() {
	    var myform = document.forms[0];
    if(myform.pipeno.value.replace(/(^\s*)|(\s*$)/g,"")==''){
       alert('管道段编号不能为空！');
        return false;
    }
    if(myform.pipename.value.replace(/(^\s*)|(\s*$)/g,"")==''){
       alert('管道段名称不能为空！');
        return false;
    }
    if(myform.length.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
       alert('距离不能为空！');
        return false;
    }
    var patrn1 =/^([0-9]{1,20})(.)([0-9]{1,20})$/; 
    if (!patrn1.exec(myform.length.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('距离必须是数字！');
       return false;
    }
    if(myform.pipehole.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
	alert('管孔数不能为空！');
        return false;
    }
   var patrn2 =/^[0-9]{1,20}$/; 
    if (!patrn2.exec(myform.pipehole.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('管孔数必须是数字！');
        return false;
    }
    if(myform.subpipehole.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
	alert('子管数不能为空！');
        return false;
    }
    if (!patrn2.exec(myform.subpipehole.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('子管数必须是数字！');
        return false;
    }
    if(myform.unuserpipe.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
       alert('未用子管数不能为空！');
        return false;
    }
  if (!patrn2.exec(myform.unuserpipe.value.replace(/(^\s*)|(\s*$)/g,""))){
       alert('未用子管数必须是数字！');
        return false;
    }
	return true;
}


</script>

<body>
<template:titile value="修改管道段信息"/>

<html:form onsubmit="return isValidForm()" method="Post" action="/PipeSegmentAction.do?method=updatePipeSegment">
  <template:formTable contentwidth="220" namewidth="220">
    <template:formTr name="管道段 编号">
    <html:hidden property="id" />
      <html:text property="pipeno"  styleClass="inputtext" style="width:160" maxlength="40"/>
    </template:formTr>
    
     <template:formTr name="管道段名称">
    <html:text property="pipename" styleClass="inputtext" style="width:160" maxlength="240"/>
    </template:formTr>
     
      <template:formTr name="代维单位">
     <html:select property="contractorid" styleClass="inputtext" style="width:160">
        <html:options collection="contractorlist" property="contractorid" labelProperty="contractorname"/>
      </html:select>
    </template:formTr>       
   
    <template:formTr name="维护区域">
    <html:text property="county" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="片区">
    <html:text property="area" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="所属乡镇">
    <html:text property="town" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="距离">
    <html:text property="length" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="管道材料">
    <html:text property="material" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    <template:formTr name="产权">
    <html:text property="right" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
    <template:formTr name="管孔数">
    <html:text property="pipehole" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
    <template:formTr name="管孔规格">
    <html:text property="pipetype" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
    <template:formTr name="子管总数">
    <html:text property="subpipehole" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
    <template:formTr name="未用子管数">
    <html:text property="unuserpipe" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
     <template:formTr name="是否验收">
     <html:select property="isaccept" styleClass="inputtext" style="width:160">
         <html:option value="0">否</html:option>
         <html:option value="1">是</html:option>
       </html:select>
    </template:formTr>
    
    <template:formTr name="备注">
    <html:text property="remark1" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
     <template:formTr name="备注2">
    <html:text property="remark2" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
     <template:formTr name="图纸编号">
    <html:text property="bluepointno" styleClass="inputtext" style="width:160" maxlength="120"/>
    </template:formTr>
    
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">修改</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
      </td>

    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
</body>
