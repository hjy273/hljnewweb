<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>

<script language="javascript" type="">

function isValidForm() {
    var myform = document.forms[0];
	if(myform.cableno.value==""){
       alert('光缆段编号编号不能为空！');
	   return fasle;
    }

	return true;
}

function onChangeCon(){
      k=1;
      for( i=0;i<document.all.workID.options.length;i++){
             if(document.all.workID.options[i].text.substring(0,6)== document.all.rId.value){
                k++;
                document.all.dId.options.length=k;
                document.all.dId.options[0].value="";
                document.all.dId.options[0].text="不限";

                document.all.dId.options[k-1].value=document.all.workID.options[i].value;
                document.all.dId.options[k-1].text=document.all.workID.options[i].text.substring(8,document.all.workID.options.length);
             }
      }
      if(k==0)
        document.all.dId.options.length=0;

    }
</script>
<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
//市移动
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
   condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
}
//市代维
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and contractorid='"+userinfo.getDeptID()+"' ";
}
//省移动
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//省代维
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
}
 condition += " and depttype=2 ";
List deptCollection = statdao.getSelectForTag("contractorname","contractorid","contractorinfo",condition);
request.setAttribute("deptCollection",deptCollection);
%>
<body>
<template:titile value="查询光缆段信息"/>

<html:form method="Post" action="/CableAction.do?method=queryCable&page=0">
  <template:formTable contentwidth="220" namewidth="220">
    <template:formTr name="光缆段编号">
      <html:text property="cableno" styleClass="inputtext" style="width:160" maxlength="40"/>
    </template:formTr>
    <logic:notEmpty name="coninfo">
            <template:formTr name="代维单位" tagID="conTr">
              <select name="contractorid" class="inputtext" style="width:160px" id="dId" onclick="onChangeCon">
                <option value="">不限</option>
                <logic:present name="coninfo">
                  <logic:iterate id="coninfoId" name="coninfo">
                    <option value="<bean:write name="coninfoId" property="contractorid"/>">
                    <bean:write name="coninfoId" property="contractorname"/></option>
                  </logic:iterate>
                </logic:present>
              </select>
            </template:formTr>
    <logic:present name="coninfo">
              <select name="workID" style="display:none" >
                <logic:iterate id="coninfoId" name="coninfo">
                  <option value="<bean:write name="coninfoId" property="contractorid"/>"><bean:write name="coninfoId" property="regionid"/>--<bean:write name="coninfoId" property="contractorname"/></option>
                </logic:iterate>
              </select>
            </logic:present>
        </logic:notEmpty>
  
     <logic:empty name="reginfo">
      <template:formTr name="代维单位" isOdd="false">
        <html:select property="contractorid" styleClass="inputtext" style="width:160">
          <html:option value="">不限</html:option>
          <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
   </logic:empty>
    <template:formTr name="光缆名称">
      <html:text property="cablename" styleClass="inputtext" style="width:160" maxlength="200"/>
    </template:formTr>
    <template:formTr name="光缆段名称">
      <html:text property="cablelinename" styleClass="inputtext" style="width:160" maxlength="200"/>
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
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
</body>
