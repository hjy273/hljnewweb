<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>

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
function onChangeSubline(){
  k=0;
  for( i=0;i<document.all.thesublineID.options.length;i++){

    if(document.all.thesublineID.options[i].text.substring(0,8)== document.all.lId.value){
      k++;
      document.all.sId.options.length=k;
      document.all.sId.options[k-1].value=document.all.thesublineID.options[i].value;
      document.all.sId.options[k-1].text=document.all.thesublineID.options[i].text.substring(10,document.all.thesublineID.options[i].text.length);
    }
  }
  document.all.lineid.value = document.all.lId.options[document.all.lId.selectedIndex].text;
  document.all.sublineid.value = document.all.sId.options[document.all.sId.selectedIndex].text;
  //if(k==0)
  //document.all.uId.options.length=0;
}
function getSubline(){
  document.all.sublineid.value = document.all.sId.options[document.all.sId.selectedIndex].text;
  //if(k==0)
  //document.all.uId.options.length=0;
}
</script>
<BR/>
<body onload="onChangeSubline()">
<template:titile value="线段月统计查询"/>
<html:form method="Post" action="/PlanMonthlyStatAction?method=showSublineMonthlyStat">
  <input  id="lineid"  name="linename" type="hidden"/>
  <input  id="sublineid"  name="sublinename" type="hidden"/>
  <template:formTable contentwidth="200" namewidth="300">
    <logic:notEmpty name="lineinfo">
      <template:formTr name="线路名称" tagID="lineTr">
        <select name="lineID" class="inputtext" style="width:180px" id="lId" onchange="onChangeSubline()" onkeypress="SelectItem()" onkeyup="onChangeSubline()">
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
    <logic:notEmpty name="sublineinfo">
      <template:formTr name="线段名称" isOdd="false">
        <select name="sublineID" class="inputtext" style="width:180px" id="sId" onkeypress="SelectItem()" onchange="getSubline()">
          <logic:present name="sublineinfo">
            <logic:iterate id="sublineinfoId" name="sublineinfo">
              <option value="<bean:write name="sublineinfoId" property="SUBLINEID"/>">
                  <bean:write name="sublineinfoId" property="SUBLINENAME"/>
              </option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
    </logic:notEmpty>

    <logic:present name="sublineinfo">
      <select name="thesublineID" style="display:none">
        <logic:iterate id="sublineinfoId" name="sublineinfo">
          <option value="<bean:write name="sublineinfoId" property="SUBLINEID"/>"><bean:write name="sublineinfoId" property="LINEID"/>--<bean:write name="sublineinfoId" property="SUBLINENAME"/></option>
        </logic:iterate>
      </select>
    </logic:present>

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

