<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>

<BR/>

<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
%>
<script language="javascript" type="">
function onChangeMobile(){
  document.all.mobileid.value = document.all.mId.options[document.all.mId.selectedIndex].text;
}
</script>

<body onload="onChangeMobile()">
<template:titile value="市移动公司月统计查询"/>
<html:form method="Post" action="/PlanMonthlyStatAction?method=showMobileMonthlyStat">
  <input  id="mobileid"  name="mobilename" type="hidden"/>
  <template:formTable contentwidth="200" namewidth="300">
    <template:formTr name="市移动公司" isOdd="false">
      <select name="mobileID" class="inputtext" style="width:180px" id="mId" onchange="onChangeMobile()">
        <logic:present name="mobileinfo">
          <logic:iterate id="mobileinfoId" name="mobileinfo">
            <option value="<bean:write name="mobileinfoId" property="deptid"/>">
                <bean:write name="mobileinfoId" property="deptname"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
    <template:formTr name="年&nbsp;&nbsp;&nbsp;&nbsp;份" isOdd="false">
      <apptag:getYearOptions/>
      <html:select property="endYear" styleClass="inputtext" style="width:180">
        <html:options collection="yearCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="月&nbsp;&nbsp;&nbsp;&nbsp;份" isOdd="false">
      <html:select property="endMonth" styleClass="inputtext" style="width:180">
        <html:option value="01">一月</html:option>
        <html:option value="02">二月</html:option>
        <html:option value="03">三月</html:option>
        <html:option value="04">四月</html:option>
        <html:option value="05">五月</html:option>
        <html:option value="06">六月</html:option>
        <html:option value="07">七月</html:option>
        <html:option value="08">八月</html:option>
        <html:option value="09">九月</html:option>
        <html:option value="10">十月</html:option>
        <html:option value="11">十一月</html:option>
        <html:option value="12">十二月</html:option>
      </html:select>
    </template:formTr>
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

