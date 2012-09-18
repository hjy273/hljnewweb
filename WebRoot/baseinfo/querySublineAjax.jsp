<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%@ page import="java.util.ArrayList"; %>
<%@ page import="java.util.List"; %>
<%@ page import="com.cabletech.sysmanage.domainobjects.UserInfo"; %>
<%@ taglib uri="newcabletechtags" prefix="myajax"%>
<%
 UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
 List listSubline = new ArrayList();
 String sqlKey ="";
 if ("22".equals(userinfo.getType())) {
   listSubline.add(userinfo.getDeptID());
   sqlKey = "subLineListSql22";
 }else if ("12".equals(userinfo.getType())) {
   listSubline.add(userinfo.getRegionid());
   sqlKey = "subLineListSql12";
 }else{
   sqlKey = "subLineListSql11";	 
 }
 request.getSession().setAttribute("inputName","subLineName");
 request.getSession().setAttribute("sqlKey",sqlKey);
 request.getSession().setAttribute("listParam",listSubline);
 %>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/prototype-1.4.0.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/scriptaculous.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxtags-1.2-beta2.js"></script>
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ajaxtags.css" />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />
</head>

<template:titile value="查询巡检线段信息"/>
<html:form method="Post" action="/sublineAction.do?method=querySubline">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="巡检线段名称">
      <input  id="inputID"  name="inputID" type="hidden"/>
      <html:text property="subLineName" styleClass="inputtext" style="width:200" maxlength="45"/>
      <span id="indicator" style="display:none;"><img src="${ctx}/images2/indicator.gif" /></span>
    </template:formTr>
    <template:formTr name="所属线路" >
      <apptag:setSelectOptions valueName="lineCollection" tableName="lineinfo" columnName1="linename" region="true" columnName2="lineid"/>
      <html:select property="lineID" styleClass="inputtext" style="width:200">
        <html:option value="">不限</html:option>
        <html:options collection="lineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="所属部门">
      <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true"/>
      <html:select property="ruleDeptID" styleClass="inputtext" style="width:200">
        <html:option value="">不限</html:option>
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="线路类型" >
      <html:select property="lineType" styleClass="inputtext" style="width:200">
        <html:option value="">不限</html:option>
        <html:option value="00">直埋</html:option>
        <html:option value="01">架空</html:option>
        <html:option value="02">管道</html:option>
		<html:option value="04">挂墙</html:option>
		<html:option value="03">混合</html:option>
	  </html:select>
    </template:formTr>

    <logic:equal value="group" name="PMType">
    	<template:formTr name="巡检维护组">
	      <html:select property="patrolid" styleClass="inputtext" style="width:200">
	        <html:option value="">无</html:option>
	        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
	      </html:select>
	    </template:formTr>
    </logic:equal>
     <logic:notEqual value="group" name="PMType">
    	<template:formTr name="巡检维护人">
	      <html:select property="patrolid" styleClass="inputtext" style="width:200">
	        <html:option value="">无</html:option>
	        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
	      </html:select>
	    </template:formTr>
    </logic:notEqual>


    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

<div id="errorMsg" style="display:none;border:1px solid #e00;background-color:#fee;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:#900"></div>
<myajax:autocomplete
	 source="subLineName"
	 target="inputID"
	 baseUrl="${ctx}/MyAutoCompleteAction.do"
	 className="autocomplete"
	 sqlKey = "${sqlKey}"
	 sqlParamListName="listSubline"
	 indicator="indicator"
	 minimumCharacters="1"
	 parser="new ResponseXmlToHtmlListParser()" 
 />
