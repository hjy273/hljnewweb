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
function onChangePatrolman(){ 
  if(document.all.cId.value == "") {
	// ��ά��˾ѡ���޵����
	document.all.uId.options.length = document.all.userID.options.length + 1;
	document.all.uId.options[0].value="";
    document.all.uId.options[0].text="����";
	for( i=0;i<document.all.userID.options.length;i++){   
      	document.all.uId.options[i + 1].value=document.all.userID.options[i].value;
      	document.all.uId.options[i + 1].text=document.all.userID.options[i].text.substring(12,document.all.userID.options[i].text.length);    	
  	}  
  } else {
	k=0;
	document.all.uId.options.length = 1;
	document.all.uId.options[0].value="";
    document.all.uId.options[0].text="����";
  	for( i=0;i<document.all.userID.options.length;i++){
    	if(document.all.userID.options[i].text.substring(0,10)== document.all.cId.value){
      		k++;
      		document.all.uId.options.length=k+1;
      		document.all.uId.options[k].value=document.all.userID.options[i].value;
      		document.all.uId.options[k].text=document.all.userID.options[i].text.substring(12,document.all.userID.options[i].text.length);
    	}
  	}
	// ���ά��˾����û��Ѳ��������
  	if(k==0) {
	 	document.all.uId.options.length = 1;
	 	document.all.uId.options[0].value="";
     	document.all.uId.options[0].text="��";
  	}
  } 
  
   
}
</script>
<%
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
if(userinfo.getType().equals("12")){%>
<body onload="onChangePatrolman()">
<% }else{%>
<body>
<% }%>
<template:titile value="�ƻ�ִ����Ϣ��ѯ"/>
<html:form method="Post" action="/PlanExeResultAction?method=showPlanExeResult">
	<input type="hidden" name="flag"  value="view">
  <template:formTable contentwidth="200" namewidth="300">
    <logic:notEmpty name="coninfo">
      <template:formTr name="��ά��λ" tagID="conTr">
        <select name="contractorID" class="inputtext" style="width:180px" id="cId" onchange="onChangePatrolman()">
          <option value="">����</option>
          <logic:present name="coninfo">
            <logic:iterate id="coninfoId" name="coninfo">
              <option value="<bean:write name="coninfoId" property="contractorid"/>">
              <bean:write name="coninfoId" property="contractorname"/>
</option>
            </logic:iterate>
          </logic:present>
        </select>
      </template:formTr>
    </logic:notEmpty>
    <logic:equal value="group" name="PMType">
      <template:formTr name="Ѳ��ά����" isOdd="false">
        <select name="patrolID" class="inputtext" style="width:180px" id="uId" onkeypress="SelectItem()">
          <option value="">����</option>
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
        <select name="patrolID" class="inputtext" style="width:180px" id="uId" onkeypress="SelectItem()">
          <option value="">����</option>
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

    <logic:present name="uinfo">
      <select name="userID" style="display:none">
        <logic:iterate id="uinfoId" name="uinfo">
          <option value="<bean:write name="uinfoId" property="patrolid"/>"><bean:write name="uinfoId" property="parentid"/>--<bean:write name="uinfoId" property="patrolname"/></option>
        </logic:iterate>
      </select>
    </logic:present>

    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
      <apptag:getYearOptions />
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
        <html:submit styleClass="button" >��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>



