<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="regionBean" class="com.cabletech.baseinfo.beans.RegionBean" scope="request"/>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<template:titile value="��ǰ������Ϣ"/>
<html:form action="/regionAction.do?method=updateRegion">
  <template:formTable>
    <template:formTr name="�������" >
      <html:text property="regionID" styleClass="inputtext" style="width:100" disabled="true"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="regionName" styleClass="inputtext" style="width:100" disabled="true"/>
    </template:formTr>
    <template:formTr name="�ϼ�����" >
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="parentregionid" styleClass="inputtext" style="width:100" disabled="true">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="����˵��">
      <html:text property="regionDes" styleClass="inputtext" disabled="true" style="width:100"/>
    </template:formTr>
    <template:formTr name="״̬" >
      <html:select property="state" styleClass="inputtext" style="width:100" disabled="true">
        <html:option value="1">����</html:option>
        <html:option value="2">��ͣ</html:option>
        <html:option value="3">ֹͣ</html:option>
      </html:select>
    </template:formTr>
  </template:formTable>
</html:form>
<IFRAME src="/WEBGIS/selectline.jsp?RegionId=<%=request.getParameter("cRegion")%>" frameBorder=0 width="100%" height=342></IFRAME>
