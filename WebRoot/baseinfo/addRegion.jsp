<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<script language="javascript" type="">
<!--
function addGoBack(){
	try{
       location.href = "${ctx}/baseinfo/queryregionresult.jsp";
        return true;
     }catch(e){
         alert(e);
     }
}
function isValidForm() {

    var chinses = /^\d\d\d\d\d\d$/;///^[\u0391-\uFFE5]+$/;
    if(document.regionBean.regionID.value==""){
		alert("请填写区域编码!! ");
		return false;
	}
    if(!chinses.test(document.regionBean.regionID.value)){
        document.regionBean.regionID.value = "";
        document.regionBean.regionID.focus();
        alert("区域编码不是数字或长度不足6位！！");
        return false;
    }
	if(document.regionBean.regionName.value==""){
		alert("请填写名称!! ");
		return false;
	}
    //新增以下一段代码，用以控制区域名称不能为空格.Add by Steven Mar 16,2007
    if(document.regionBean.regionName.value.trim()==""){
      alert("区域名称不能为空格!! ");
      document.regionBean.regionName.focus();
      return false;
    }
	if(document.regionBean.regionDes.value==""){
		alert("请填写区域说明!! ");
		return false;
	}
     if(document.regionBean.regionDes.value.trim()==""){
      alert("区域说明不能为空格!! ");
      document.regionBean.regionDes.focus();
      return false;
    }
   // return false;
}
function toGetBack(){
       window.history.go(-1);
}

//-->
</script>
<apptag:checkpower thirdmould="70102" ishead="1">
	<jsp:forward page="../globinfo/powererror.jsp"/>
</apptag:checkpower>
<template:titile value="增加区域信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/regionAction.do?method=addRegion">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="区域编码" >
      <html:text property="regionID" styleClass="inputtext" style="width:200" maxlength="6"  title="必须是6位数字"/>
    </template:formTr>
    <template:formTr name="区域名称">
      <html:text property="regionName" styleClass="inputtext" style="width:200" maxlength="10"/>
    </template:formTr>
    <template:formTr name="上级区域" >
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname"  columnName2="regionid" region="true" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="parentregionid" styleClass="inputtext" style="width:200">
      	<html:option value="">无</html:option>
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="区域说明">
      <html:text property="regionDes" styleClass="inputtext" style="width:200" maxlength="50"/>
    </template:formTr>
    <!--
    <template:formTr name="状态" >
      <html:select property="state" styleClass="inputtext" style="width:200">
        <html:option value="1">正常</html:option>
        <html:option value="2">暂停</html:option>
        <html:option value="3">停止</html:option>
      </html:select>
    </template:formTr>-->
    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="addGoBack()" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
