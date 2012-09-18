<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
%>
<script language="javascript" type="">
var g_strKeyin;
var g_lasttimer = Date.parse(Date());
var g_timelimit = 2000;
//Select String
function SelectItem()
{
  if(g_strKeyin == null)
  {
    g_strKeyin = "";
  }
  var length;
  var strText;
  var timer;
  var TheForm;
  var j;
  TheForm=event.srcElement;
  timer=Date.parse(Date());
  if(timer - g_lasttimer > g_timelimit)
  {g_strKeyin="";}
  g_lasttimer = timer;
  g_strKeyin = g_strKeyin + String.fromCharCode(window.event.keyCode);
  length = TheForm.length;
  for(i=0;i<length;i++)
  {
    strText=TheForm.options[i].text;
    strText=strText.toLowerCase();
    intI=strText.indexOf(g_strKeyin);
    if (intI==0)
    {
      for(j=0;j<length;j++)
      {
        TheForm.options[j].selected=false;
      }
      TheForm.options[i].selected=true;
      window.event.returnValue=false;
      return;
    }
  }
}
function onChangeLine(){
  document.all.lineid.value = document.all.lId.options[document.all.lId.selectedIndex].text;
}

 function onChangeLineAll(){
    k=0;
      for( i=0;i<document.all.workID.options.length;i++){

             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                 k++;
                document.all.lId.options.length=k;
                document.all.lId.options[k-1].value=document.all.workID.options[i].value;
                document.all.lId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }

      }
      if(k==0)
       document.all.lId.options.length=0;
      document.all.lineid.value = document.all.lId.options[document.all.lId.selectedIndex].text;
 }
</script>
<BR/>
<%
if(userinfo.getType().equals( "11" )){%>
<body onload="onChangeLineAll()">
<% }else{%>
<body onload="onChangeLine()">
<% }%>
<template:titile value="线路月统计查询"/>
<html:form method="Post" action="/PlanMonthlyStatAction?method=showLineMonthlyStat">
  <input  id="lineid"  name="linename" type="hidden"/>
  <template:formTable contentwidth="200" namewidth="300">
      <logic:equal value="11" name="LOGIN_USER" property="type">
          <logic:notEmpty name="reginfo">
            <template:formTr name="所属区域" isOdd="false">
              <select name="regionId" class="inputtext" style="width:180px" id="rId" onchange="onChangeLineAll()">
                <logic:present name="reginfo">
                  <logic:iterate id="reginfoId" name="reginfo">
                    <option value="<bean:write name="reginfoId" property="regionid"/>">
                         <bean:write name="reginfoId" property="regionname"/>
                    </option>
                  </logic:iterate>
                </logic:present>
              </select>
            </template:formTr>
            <logic:present name="lineinfo">
              <select name="workID" style="display:none">
                <logic:iterate id="lineinfoId" name="lineinfo">
                    <option value="<bean:write name="lineinfoId" property="lineid"/>"><bean:write name="lineinfoId" property="regionid"/>--<bean:write name="lineinfoId" property="linename"/></option>
                </logic:iterate>
              </select>
          </logic:present>
        </logic:notEmpty>
    </logic:equal>

    <logic:notEmpty name="lineinfo">
      <template:formTr name="线路名称" tagID="lineTr">
        <select name="lineID" class="inputtext" style="width:180px" id="lId" onchange="onChangeLine()" onkeypress="SelectItem()" onblur="onChangeLine()">
          <logic:present name="lineinfo">
            <logic:iterate id="lineinfoId" name="lineinfo">
              <option value="<bean:write name="lineinfoId" property="lineid"/>">
                     <bean:write name="lineinfoId" property="linename"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
      </logic:notEmpty>
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
          <html:submit styleClass="button" >查询</html:submit>
        </td>
        <td>
          <html:reset styleClass="button">取消</html:reset>
        </td>
      </template:formSubmit>
  </template:formTable>
</html:form>


