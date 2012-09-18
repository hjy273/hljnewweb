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
<template:titile value="���ƶ���˾��ͳ�Ʋ�ѯ"/>
<html:form method="Post" action="/PlanMonthlyStatAction?method=showMobileMonthlyStat">
  <input  id="mobileid"  name="mobilename" type="hidden"/>
  <template:formTable contentwidth="200" namewidth="300">
    <template:formTr name="���ƶ���˾" isOdd="false">
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
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
      <apptag:getYearOptions/>
      <html:select property="endYear" styleClass="inputtext" style="width:180">
        <html:options collection="yearCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
      <html:select property="endMonth" styleClass="inputtext" style="width:180">
        <html:option value="01">һ��</html:option>
        <html:option value="02">����</html:option>
        <html:option value="03">����</html:option>
        <html:option value="04">����</html:option>
        <html:option value="05">����</html:option>
        <html:option value="06">����</html:option>
        <html:option value="07">����</html:option>
        <html:option value="08">����</html:option>
        <html:option value="09">����</html:option>
        <html:option value="10">ʮ��</html:option>
        <html:option value="11">ʮһ��</html:option>
        <html:option value="12">ʮ����</html:option>
      </html:select>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

