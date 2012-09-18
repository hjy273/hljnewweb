<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="departBean" class="com.cabletech.baseinfo.beans.DepartBean" scope="request"/>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<!-- ZSH 2004-10-13 -->
<script language="javascript">
<!--
function isValidForm() {
    //部门校验
	if(document.departBean.deptName.value==""){
		alert("名称不能为空!! ");
		return false;
	}
    //新增以下一段代码，用以控制部门名称不能为空格.Add by Steven Mar 12,2007
    if(document.departBean.deptName.value.trim()==""){
		alert("部门名称不能为空格!! ");
        document.departBean.deptName.focus();
		return false;
	}
    if(valCharLength(document.departBean.deptName.value)>20){
      alert("部门名称字数太多！不能超过10个汉字");
      return false;
    }
	if(document.departBean.linkmanInfo.value==""){
		alert("联系人不能为空!! ");
		return false;
	}
    //新增以下一段代码，用以控制联系人不能为空格.Add by Steven Mar 12,2007
    if(document.departBean.linkmanInfo.value.trim()==""){
		alert("联系人不能为空格!! ");
        document.departBean.linkmanInfo.focus();
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
 // window.history.go(-1);
}
function seleOnCh(){
	temp = departBean.parentID.selectedIndex;
    //alert(temp);
}
function check(){
  	tem = departBean.parentID.options[departBean.parentID.selectedIndex].value;
    deptid = departBean.deptID.value;
    //alert (tem  +" "+deptid +"  "+temp);
    if(tem ==deptid){
    	alert("对不起，您选择的上级部门不正确！！");
        departBean.parentID.selectedIndex = temp;
    }

}

//-->
</script>
<template:titile value="修改部门信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/departAction.do?method=updateDepart">
  <template:formTable contentwidth="200" namewidth="200">
    <html:hidden property="deptID"/>
    <template:formTr name="部门名称">
      <html:text property="deptName" styleClass="inputtext" style="width:160" maxlength="20"/>
    </template:formTr>
    <logic:equal value="11" name="LOGIN_USER" property="type">
      <template:formTr name="所属区域">
        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
        <html:select property="regionid" disabled="true" styleClass="inputtext" style="width:160">
          <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>

    </logic:equal>
    <template:formTr name="上级部门">
      <%
     	DepartBean bean =(DepartBean)request.getAttribute("departBean");
      	String parentid = bean.getParentID();
      	if("0000".equals(parentid)){
        %>
        <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname"  columnName2="deptid" condition="parentid like '%0000'"/>
        <html:hidden property="parentID"/>
        <span id="selectSpan">

          <html:select property="parentID" styleClass="inputtext" style="width:160">
            <html:options collection="deptCollection" property="value" labelProperty="label"/>
          </html:select>
        </span>
        <% }else{
        %>
        <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true" />
        <span id="selectSpan">
          <html:select property="parentID" onchange="check()"  onfocus=" seleOnCh()" styleClass="inputtext" style="width:160">
            <html:options collection="deptCollection" property="value" labelProperty="label"/>
          </html:select>
        </span>
       <%}%>
    </template:formTr>
    <template:formTr name="联 系 人" >
      <html:text property="linkmanInfo" styleClass="inputtext" style="width:160" maxlength="25"/>
    </template:formTr>
    <template:formTr name="备&nbsp;&nbsp;&nbsp;&nbsp;注" >
      <html:text property="remark" styleClass="inputtext" style="width:160" maxlength="10"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >更新</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
