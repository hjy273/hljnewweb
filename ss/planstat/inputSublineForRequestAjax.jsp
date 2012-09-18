<!-- 
<?xml version="1.0" encoding="UTF-8" ?>
<%@ //page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 -->
<%@ include file="/common/header.jsp"%>
<%@ taglib uri="newcabletechtags" prefix="myajax"%>
<%//request.setCharacterEncoding("UTF-8"); %>
<%@ page import="java.util.ArrayList"; %>
<%@ page import="java.util.List"; %>
<%@ page import="com.cabletech.baseinfo.domainobjects.UserInfo"; %>
<%
 UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
 List listSubline = new ArrayList();
 String sqlKey="";
 if ("22".equals(userinfo.getType())) {
   listSubline.add(userinfo.getDeptID());
   sqlKey = "subLineListSql22";
 }else if ("12".equals(userinfo.getType())) {
   listSubline.add(userinfo.getRegionid());
   sqlKey = "subLineListSql22";
 }
 request.getSession().setAttribute("inputName","inputName");
 request.getSession().setAttribute("sqlKey",sqlKey);
 request.getSession().setAttribute("listParam",listSubline);
 %>
<script language="javascript" type="">
 function selectandsubmit(){
     alert(document.all.inputName.value + "," + document.all.inputID.value);
     var   myfrm = document.getElementById("myform");   
     myfrm.action = "${ctx}/SublineRealTimeAction.do?method=sendRealTimeRequestAjax";
     myfrm.submit(); 
 }
</script>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/prototype-1.4.0.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/scriptaculous.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxtags-1.2-beta2.js"></script>
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ajaxtags.css" />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />
</head>
<br>
<body>
<template:titile value="巡检线段实时查询"/>
<br>
<form id = "myform" method="post" action="${ctx}/SublineRealTimeAction.do?method=sendRealTimeRequestAjax" class="basicForm">
    <template:formTable contentwidth="200" namewidth="300">
	    <template:formTr name="巡检线段名称" isOdd="false">
	    <input  id="inputID"  name="inputID" type="hidden"/>
	    <input id="inputName" name="inputName" type="text" size="30" />
	    <span id="indicator" style="display:none;"><img src="${ctx}/images/ajaxtags/indicator.gif" /></span>
	    </template:formTr>
	    <template:formSubmit>
	      <td>
	        <html:submit styleClass="button">直接提交</html:submit>
	      </td>	    
	      <td>
	        <html:button property="button" styleClass="button" value="模糊查询" onclick="selectandsubmit()"/> 
	      </td>
	      <td>
	        <html:reset styleClass="button">取消</html:reset>
	      </td>
	    </template:formSubmit>
    </template:formTable>
    </form>

<div id="errorMsg" style="display:none;border:1px solid #e00;background-color:#fee;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:#900"></div>

<myajax:autocomplete
  source="inputName"
  target="inputID"
  baseUrl="${ctx}/MyAutoCompleteAction.do"
  className="autocomplete"
  sqlKey = "${sqlKey}"
  sqlParamListName="listSubline"
  indicator="indicator"
  minimumCharacters="1"
  parser="new ResponseXmlToHtmlListParser()" />
</body>
</html>



  
