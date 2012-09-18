<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<script language="javascript" type="">
 function onChangeCon(){
	var rId=document.forms[0].elements["rId"];
	var regionid=document.getElementById("regionid");
   	regionid.value = rId.options[rId.selectedIndex].text;
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
<body onload="">
<template:titile value="全网年统计查询"/>
<html:form method="Post"
	action="/whole_net_stat.do?method=showYearStatInfo">
  <input  id="conid"  name="conname" type="hidden"/>
  <template:formTable contentwidth="200" namewidth="300">
    <logic:equal value="11" name="LOGIN_USER" property="type">
          <logic:notEmpty name="reginfo">
            <template:formTr name="所属区域" isOdd="false">
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
      <template:formTr name="年&nbsp;&nbsp;&nbsp;&nbsp;份" isOdd="false">
        <apptag:getYearOptions/>
        <html:select property="endYear" styleClass="inputtext">
          <html:options collection="yearCollection" property="value" labelProperty="label"/>
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

