<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>
<script language="javascript" type="">
//<!--
function String.prototype.trim1(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function isValidForm() {
    //部门校验
	if(document.departBean.deptName.value==""||document.departBean.deptName.value.trim1()==""||document.departBean.deptName.value.trim1()=="null"){
		alert("部门名称不能为空或者空格或者null!! ");
        document.departBean.deptName.focus();
		return false;
	}
  
    if(valCharLength(document.departBean.deptName.value)>20){
      alert("部门名称字数太多！不能超过10个汉字");
      return false;
    }
	if(document.departBean.linkmanInfo.value==""||document.departBean.linkmanInfo.value.trim1()=="null"||document.departBean.linkmanInfo.value.trim1()==""||document.departBean.linkmanInfo.value.trim1()=="null"){
		alert("联系人不能为空或者null!! ");
        document.departBean.linkmanInfo.focus();
		return false;
	}
    //新增以下一段代码，用以控制联系人不能为空格.Add by Steven Mar 12,2007
    if(trim(document.departBean.linkmanInfo.value)==""){
		alert("联系人不能为空格!! ");
        document.departBean.linkmanInfo.focus();
		return false;
	}
	  //新增以下一段代码，用以控制部门名称不能为空格.Add by Steven Mar 12,2007
    if(trim(document.departBean.deptName.value)==""){
		alert("部门名称不能为空格!! ");
        document.departBean.deptName.focus();
		return false;
	}

	return true;
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

function getDep(){

	var depV = departBean.regionid.value;
	var URL = "getSelect.jsp?depType=1&selectname=parentID&regionid=" + depV;

	hiddenInfoFrame.location.replace(URL);
}
function toGetBack(){
 	var url="${ctx}/departAction.do?method=queryDepart";
	self.location.replace(url);
//window.history.go(-1);
}
//-->
</script>

<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );

//省移动
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL and regionid='" + userinfo.getRegionID() + "'";
}

List superDeptCollection = statdao.getSelectForTag("deptname","deptid","deptinfo",condition);
request.setAttribute("superDeptCollection",superDeptCollection);
%>

<apptag:checkpower thirdmould="70202" ishead="5">
  <jsp:forward page="/globinfo/powererror.jsp"/>
</apptag:checkpower>
<br>
<template:titile value="增加部门信息"/>
<html:form onsubmit="return isValidForm()" method="Post" styleId="departBean" action="/departAction.do?method=addDepart">
  <template:formTable>
    <template:formTr name="部门名称">
      <html:text property="deptName" styleClass="inputtext" style="width:160px" maxlength="20"/> <font color="red"> *</font>
    </template:formTr>
    <logic:equal value="11" name="LOGIN_USER" property="type">
      <template:formTr name="所属区域" isOdd="false">
        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
        <html:select property="regionid" styleClass="inputtext" style="width:160px">
          <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
    </logic:equal>
     <logic:equal value="12" name="LOGIN_USER" property="type">
      <template:formTr name="所属区域" isOdd="false" style="display:none">
        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid"/>
        <html:select property="regionid" styleClass="inputtext" style="width:160px">
          <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
    </logic:equal>

    <logic:equal value="12" name="LOGIN_USER" property="type">
      <template:formTr name="上级部门">
        <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true"/>
        <span id="selectSpan">
          <html:select property="parentID" styleClass="inputtext" style="width:160px">
          	<html:option value="00000000">无</html:option>
            <html:options collection="deptCollection" property="value" labelProperty="label"/>
          </html:select><font color="red"> *</font>
        </span>
      </template:formTr>
    </logic:equal>

    <logic:equal value="11" name="LOGIN_USER" property="type">
      <template:formTr name="上级部门">
        <html:select property="parentID" styleClass="inputtext" style="width:160px">
          <html:options collection="superDeptCollection" property="value" labelProperty="label"/>
        </html:select><font color="red"> *</font>
      </template:formTr>
    </logic:equal>

    <template:formTr name="联 系 人" isOdd="false">
      <html:text property="linkmanInfo" styleClass="inputtext" style="width:160px" maxlength="25"/> <font color="red"> *</font>
    </template:formTr>
    <!-- zsh New add 2004-10-13
    <template:formTr name="状态">
      <html:select property="state" styleClass="inputtext" style="width:160px">
        <html:option value="1">正常</html:option>
        <html:option value="2">暂停</html:option>
        <html:option value="3">停止</html:option>
      </html:select>
    </template:formTr>-->
    <template:formTr name="备&nbsp;&nbsp;&nbsp;&nbsp;注" isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:160px" maxlength="10"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<template:cssTable/>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
