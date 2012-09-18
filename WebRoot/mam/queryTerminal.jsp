<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<jsp:useBean id="selectForTag" class="com.cabletech.commons.tags.SelectForTag" scope="request"/>
<script language="javascript" src="${ctx}/js/prototype.js" type=""></script>
<script type="text/javascript">
function getPatrolMans(){
	var contractorId = $('contractorID').value;
	var url = "${ctx}/patrolAction.do?method=getPatrolMansByParentId&&parentID="+contractorId+"&&rnd="+Math.random();
	new Ajax.Request(encodeURI(url),{
		method: 'post',
       	evalScripts: true,
       	onSuccess: function(transport){
       		var str = transport.responseText;
       		document.all.ownerID.options.length = 1;
			if(str != ''){
				var strings = str.split(";");
				for(var i=0; i<strings.length; i++){
					if(strings[i] != ""){
						var xstrings = strings[i].split(",");
						var option = new Option(xstrings[0],xstrings[1]);
						document.all.ownerID.options.add(option);
					}
				}
			}       			
       	},
       	onFailure: function(){
			alert("Ѳ����δ֪�쳣��");
		}       		
	});
}
</script>
<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
//���ƶ�
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
   condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
}
//�д�ά
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and parentid='"+userinfo.getDeptID()+"' ";
}
//ʡ�ƶ�
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//ʡ��ά
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE parentid in( select contractorid from contractorinfo where PARENTCONTRACTORID ='"+userinfo.getDeptID()+"')";
}
List patrolCollection = selectForTag.getSelectForTag("patrolname","patrolid","patrolmaninfo",condition);
request.setAttribute("patrolCollection",patrolCollection);
%>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<template:titile value="��ѯ�ֳ��ն��豸��Ϣ"/>
<html:form method="Post" action="/terminalAction.do?method=queryTerminal">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="�豸���">
      <html:text property="terminalID" styleClass="inputtext" style="width:200" maxlength="16" title="ֻ�������豸��ŵĺ�4λ"/>
    </template:formTr>

    <logic:equal value="1" name="LOGIN_USER" property="deptype">
      <template:formTr name="��ά��λ" >
        <apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true"/>
        <html:select property="contractorID" styleClass="inputtext" style="width:200" onchange="getPatrolMans();">
          <html:option value="">����</html:option>
          <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
    </logic:equal>
    <logic:equal value="2" name="LOGIN_USER" property="deptype">
      <template:formTr name="��ά��λ">
        <select name="contractorID" Class="inputtext" style="width:200" onchange="getPatrolMans();">
          <option value="">����</option>
          <option value="${LOGIN_USER.deptID }">${LOGIN_USER.deptName }</option>
        </select>
      </template:formTr>
    </logic:equal>
    <logic:equal value="group" name="PMType">
    	<template:formTr name="Ѳ��ά����">
	      <html:select property="ownerID" styleClass="inputtext" style="width:200">
	        <html:option value="">����</html:option>
	      </html:select>
	    </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
	    <template:formTr name="����Ѳ����">
	      <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true"/>
	      <html:select property="ownerID" styleClass="inputtext" style="width:200">
	        <html:option value="">����</html:option>
	      </html:select>
	    </template:formTr>
    </logic:notEqual>

    <template:formTr name="�豸����" >
      <html:select property="terminalType" styleClass="inputtext" style="width:200">
        <html:option value="">����</html:option>
        <html:option value="1">�ֳ�</html:option>
        <html:option value="2">����</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="produceMan" styleClass="inputtext" style="width:200" maxlength="45"/>
    </template:formTr>
    <template:formTr name="�豸�ͺ�" >
       <html:select property="terminalModel" styleId="select" styleClass="inputtext" style="width:200">
                <html:option value="CT-1200">CT-1200</html:option>
                <html:option value="CT-6100">CT-6100</html:option>
                <html:option value="CT-6610">CT-6610</html:option>
                <html:option value="NV-2000">NV-2000</html:option>
                <html:option value="other">����</html:option>
            </html:select>

    </template:formTr>
    <template:formTr name="SIM������">
      <html:select property="simType" styleClass="inputtext" style="width:200">
        <html:option value="">����</html:option>
        <html:option value="0001">ȫ��ͨ</html:option>
        <html:option value="0002">������</html:option><!--
        <html:option value="0003">��ͨ</html:option>
        <html:option value="0004">��ͨCDMA</html:option>-->
      </html:select>
    </template:formTr>
	<template:formTr name="SIM����" >
      <html:text property="simNumber" styleClass="inputtext" style="width:200" maxlength="45"/>
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
