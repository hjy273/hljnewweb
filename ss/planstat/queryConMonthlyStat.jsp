<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
%>
<script language="javascript" type="">
 function onChangeCon(){
	var conId=document.forms[0].elements["conId"];
	var conid=document.getElementById("conid");
   	conid.value = conId.options[conId.selectedIndex].text;
 }
 function onChangeConAll(){
    k=0;
      for( i=0;i<document.all.workID.options.length;i++){

             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                 k++;
                document.all.conId.options.length=k;
                document.all.conId.options[k-1].value=document.all.workID.options[i].value;
                document.all.conId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }

      }
      if(k==0)
       document.all.conId.options.length=0;
      document.all.conid.value = document.all.conId.options[document.all.conId.selectedIndex].text;
 }
</script>
<%
if(userinfo.getType().equals( "11" )){%>
<body onload="onChangeConAll()">
<% }else{%>
<body onload="onChangeCon()">
<% }%>
<template:titile value="��ά��˾��ͳ�Ʋ�ѯ"/>
<html:form method="Post" action="/PlanMonthlyStatAction?method=showConMonthlyStat">
  <input  id="conid"  name="conname" type="hidden"/>
  <template:formTable contentwidth="200" namewidth="300">
    <logic:equal value="11" name="LOGIN_USER" property="type">
          <logic:notEmpty name="reginfo">
            <template:formTr name="��������" isOdd="false">
              <select name="regionId" class="inputtext" style="width:180px" id="rId" onchange="onChangeConAll()">
                <logic:present name="reginfo">
                  <logic:iterate id="reginfoId" name="reginfo">
                    <option value="<bean:write name="reginfoId" property="regionid"/>">
                         <bean:write name="reginfoId" property="regionname"/>
                    </option>
                  </logic:iterate>
                </logic:present>
              </select>
            </template:formTr>
            <logic:present name="coninfo">
              <select name="workID" style="display:none">
                <logic:iterate id="coninfoId" name="coninfo">
                    <option value="<bean:write name="coninfoId" property="contractorid"/>"><bean:write name="coninfoId" property="regionid"/>--<bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>
        </logic:notEmpty>
    </logic:equal>
    <template:formTr name="�д�ά��˾" isOdd="false">
      <select name="conID" class="inputtext" style="width:180px" id="conId" onclick="onChangeCon()">
        <logic:present name="coninfo">
          <logic:iterate id="coninfoId" name="coninfo">
            <option value="<bean:write name="coninfoId" property="contractorid"/>">
                <bean:write name="coninfoId" property="contractorname"/>
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
          <option value="06">����</option>
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

