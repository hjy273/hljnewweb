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
   <template:titile value="Ѳ��ά������ͳ�Ʋ�ѯ"/>
</logic:equal>
<logic:notEqual value="group" name="PMType">
   <template:titile value="Ѳ����Ա��ͳ�Ʋ�ѯ"/>
</logic:notEqual>
<html:form method="Post" action="/PlanMonthlyStatAction?method=showPatrolMonthlyStat">
  <input  id="patrolname"  name="patrolname" type="hidden"/>
  <template:formTable contentwidth="200" namewidth="300">
      <logic:equal value="group" name="PMType">
        <template:formTr name="Ѳ��ά����" isOdd="false">
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
        <template:formTr name="Ѳ �� ��" isOdd="false">
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
