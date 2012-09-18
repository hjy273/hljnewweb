<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="当前报警信息"/>
<display:table name="sessionScope.queryresult" id="currentRowObject" pagesize="18">
  <display:column property="pid" title="标识名称"/>
  <display:column property="executorid" title="上报人"/>
  <display:column property="executetime" title="上报时间"/>
  <display:column property="operationcode" title="报警内容"/>
</display:table>
