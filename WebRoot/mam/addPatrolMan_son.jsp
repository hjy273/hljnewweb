<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<%@page import="java.util.Date" %>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<jsp:useBean id="selectForTag" class="com.cabletech.commons.tags.SelectForTag" scope="request"/>


<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
List groupCollection = selectForTag.getPatrolManInfo(userinfo);
request.setAttribute("pglist",groupCollection);
//市移动
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
}
//市代维
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and contractorid='"+userinfo.getDeptID()+"' ";
}
//省移动
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//省代维
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
}
List deptCollection = selectForTag.getSelectForTag("contractorname","contractorid","contractorinfo",condition);
request.setAttribute("deptCollection",deptCollection);
%>
<script src="${ctx}/common/validator.js" type=text/javascript></script>
<script language="javascript" type="">
<!--
function isValidForm() {

  if(document.patrolSonBean.patrolName.value==""){
    alert("姓名不能为空!! ");
    document.patrolSonBean.patrolName.focus();
    return false;
  }
  //新增以下一段代码，用以控制姓名不能为空格.Add by Steven Mar 13,2007
  if(document.patrolSonBean.patrolName.value.trim()==""){
    alert("姓名不能为空格!! ");
    document.patrolSonBean.patrolName.focus();
    return false;
  }
  str1 = document.patrolSonBean.phone.value;
   //新增以下一段代码，用以控制电话号码格式.Add by Steven Mar 13,2007
  if (CheckTEL(str1)!=true){
    document.patrolSonBean.phone.focus();
    return false;
  }
  /*
  if (escape(str1).indexOf("%u")!=-1) {
    alert("电话号码中不能含有汉字!!");
    document.patrolSonBean.phone.focus();
    return false;
  }*/

  str2 = document.patrolSonBean.mobile.value;
  //新增以下一段代码，用以控制手机号码格式.Add by Steven Mar 13,2007
  if (ChkMobilePhone(str2)!=true){
    document.patrolSonBean.mobile.focus();
    return false;
  }
  ////新增以下一段代码，用以控制选择的出生日期.Add by Steven Mar 13,2007
   date = new Date();
   intYear = parseInt(date.getYear());
   choosedate = document.patrolSonBean.birthday.value;
   if ((choosedate!="" || choosedate!=null) && parseInt(choosedate.substring(0,4))>=intYear){
    alert("非法选择，出生年份不可能等于或者大于当前系统年份");
    document.patrolSonBean.birthday.value="";
    return false;
  }

  str3 = document.patrolSonBean.postalcode.value;
  if (checkPostcode(str3)!=1){
    document.patrolSonBean.postalcode.focus();
    return false;
 }

  str4 = document.patrolSonBean.identityCard.value;
  //新增以下一段代码，用以控制身份证号码格式.Add by Steven Mar 13,2007
  if (checkIDCard(str4)!=1){
    document.patrolSonBean.identityCard.focus();
    return false;
  }
  str5 = document.patrolSonBean.familyAddress.value;
  if (str5.length>20){
    alert("内容过长，管理范围项只能输入20个字符");
    document.patrolSonBean.familyAddress.focus();
    return false;
  }
  str6 = document.patrolSonBean.workRecord.value;
  if (str6.length>1024){
    alert("内容过长，工作经历项只能输入1024个字符");
    document.patrolSonBean.workRecord.focus();
    return false;
  }
  return true;
}

//-->
</script>
<template:titile value="增加巡检员信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/patrolSonAction.do?method=addPatrolManSon">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="姓&nbsp;&nbsp;&nbsp;&nbsp;名" isOdd="">
      <html:text property="patrolName" styleClass="inputtext" style="width:200" maxlength="10"/>
      <!-- 年龄-->
      <html:hidden property="age"/>
      <!-- 区域 -->
      <html:hidden property="regionID"/>
    </template:formTr>
    <template:formTr name="性&nbsp;&nbsp;&nbsp;&nbsp;别">
      <html:select property="sex" styleClass="inputtext" style="width:200">
        <html:option value="1">男</html:option>
        <html:option value="2">女</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="单位名称"  style="display:none">
      <html:select property="parentID" styleClass="inputtext" style="display:none">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>

    </template:formTr>

    <logic:equal value="group" name="PMType">
      <template:formTr name="巡检维护组">
        <html:select property="parent_patrol" styleClass="inputtext" style="width:200">

          <logic:present name="pglist">
            <logic:iterate id="pgId" name="pglist">
              <option value="<bean:write name="pgId" property="patrolid"/>"><bean:write name="pgId" property="patrolname"/></option>
            </logic:iterate>
          </logic:present>
        </html:select>
      </template:formTr>
    </logic:equal>

    <template:formTr name="工作信息" >
      <html:text property="jobInfo" styleClass="inputtext" style="width:200" maxlength="50"/>
    </template:formTr>
    <template:formTr name="工作状态">
      <html:select property="jobState" styleClass="inputtext" style="width:200">
        <html:option value="1">在岗</html:option>
        <html:option value="2">休假</html:option>
        <html:option value="3">离职</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="固定电话" >
      <html:text property="phone" styleClass="inputtext" style="width:200" maxlength="12"/>
    </template:formTr>
    <template:formTr name="移动电话">
      <html:text property="mobile" styleClass="inputtext" style="width:200" maxlength="12"/>
    </template:formTr>
    <template:formTr name="出生日期" >
      <html:text readonly="true" property="birthday" styleClass="inputtext" style="width:200" maxlength="12"/>
      <apptag:date property="birthday"/>
    </template:formTr>
    <template:formTr name="婚姻状况">
      <html:select property="isMarriage" styleClass="inputtext" style="width:200">
        <html:option value="0">未婚</html:option>
        <html:option value="1">已婚</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="文化程度" >
      <html:select property="culture" styleClass="inputtext" style="width:200">
        <html:option value="4">初中及以下</html:option>
        <html:option value="1">高中/中专</html:option>
        <html:option value="2">大专</html:option>
        <html:option value="3">本科/本科以上</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="邮政编码">
      <html:text property="postalcode" styleClass="inputtext" style="width:200" maxlength="6"/>
    </template:formTr>
    <template:formTr name="身份证号" >
      <html:text property="identityCard" styleClass="inputtext" style="width:200" maxlength="18"/>
    </template:formTr>
    <template:formTr name="管理范围">
      <!-- 改单行为多行显示.Add by Steven Mar 13,2007 -->
      <textarea cols="" rows="2" name="familyAddress" style="width:280;" class="textarea" ></textarea>
    </template:formTr>
    <template:formTr name="工作经历" >
      <!-- 改单行为多行显示.Add by Steven Mar 13,2007 -->
      <textarea cols="" rows="2" name="workRecord" style="width:280;" class="textarea" ></textarea>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
