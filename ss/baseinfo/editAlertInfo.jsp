<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="AlertInfoBean" class="com.cabletech.baseinfo.beans.AlertInfoBean" scope="request"/>
<%@include file="/common/header.jsp"%>
<template:titile value="�޸ı�����Ϣ"/>
<html:form onsubmit="" method="Post" action="/AlertInfoAction.do?method=updateDepart">
  <template:formTable>
    <template:formTr name="���" isOdd="false">
      <html:text property="id" styleClass="inputtext" style="width:160px" readonly="true"/>
    </template:formTr>
    <template:formTr name="ִ����" isOdd="false">
      <apptag:setSelectOptions valueName="executorCellection" tableName="patrolmaninfo" columnName1="patrolname" region="true" columnName2="patrolid"/>
      <html:select property="executorid" styleClass="inputtext" style="width:160px">
        <html:options collection="executorCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="operationcode" styleClass="inputtext" style="width:160px" maxlength="45"/>
    </template:formTr>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
