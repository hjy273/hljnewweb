<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="AlertInfoBean" class="com.cabletech.baseinfo.beans.AlertInfoBean" scope="request"/>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<template:titile value="�޸ı�����Ϣ"/>
<html:form onsubmit="" method="Post" action="/AlertInfoAction.do?method=updateDepart">
  <template:formTable>
    <template:formTr name="���" >
      <html:text property="id" styleClass="inputtext" style="width:160" readonly="true"/>
    </template:formTr>
    <template:formTr name="ִ����" >
      <apptag:setSelectOptions valueName="executorCellection" tableName="patrolmaninfo" columnName1="patrolname" region="true" columnName2="patrolid"/>
      <html:select property="executorid" styleClass="inputtext" style="width:160">
        <html:options collection="executorCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="operationcode" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
