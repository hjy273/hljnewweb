<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>

<BR/>

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
 function onChangePatrolman(){
    document.all.patrolname.value = document.all.uId.options[document.all.uId.selectedIndex].text;
 }
</script>

<body onload="onChangePatrolman()">
<logic:equal value="group" name="PMType">
   <template:titile value="巡检维护组月统计查询"/>
</logic:equal>
<logic:notEqual value="group" name="PMType">
   <template:titile value="巡检人员月统计查询"/>
</logic:notEqual>
<html:form method="Post" action="/PlanMonthlyStatAction?method=showPatrolMonthlyStat">
  <input  id="patrolname"  name="patrolname" type="hidden"/>
  <template:formTable contentwidth="200" namewidth="300">
      <logic:equal value="group" name="PMType">
        <template:formTr name="巡检维护组" isOdd="false">
          <select name="patrolID" class="inputtext" style="width:180px" id="uId" onchange="onChangePatrolman()" onkeypress="SelectItem()" onblur="onChangePatrolman()">
            <logic:present name="uinfo">
              <logic:iterate id="uinfoId" name="uinfo">
                <option value="<bean:write name="uinfoId" property="patrolid"/>">
                    <bean:write name="uinfoId" property="patrolname"/>
                </option>
              </logic:iterate>
            </logic:present>
          </select>
        </template:formTr>
      </logic:equal>
      <logic:notEqual value="group" name="PMType">
        <template:formTr name="巡 检 人" isOdd="false">
          <select name="patrolID" class="inputtext" style="width:180px" id="uId" onchange="onChangePatrolman()" onkeypress="SelectItem()" onblur="onChangePatrolman()">
             <logic:present name="uinfo">
              <logic:iterate id="uinfoId" name="uinfo">
                <option value="<bean:write name="uinfoId" property="patrolid"/>">
                    <bean:write name="uinfoId" property="patrolname"/>
                </option>
              </logic:iterate>
            </logic:present>
          </select>
        </template:formTr>
      </logic:notEqual>
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
