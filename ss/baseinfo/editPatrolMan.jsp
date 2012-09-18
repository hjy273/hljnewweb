<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="patrolmanBean" class="com.cabletech.baseinfo.beans.PatrolManBean" scope="request"/>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>
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
List deptCollection = statdao.getSelectForTag("contractorname","contractorid","contractorinfo",condition);
request.setAttribute("deptCollection",deptCollection);
%>
<%@include file="/common/header.jsp"%>
<script language="javascript">
<!--
function isValidForm() {

	if(document.patrolBean.patrolName.value==""){
		alert("巡检维护组名称不能为空!! ");
		return false;
	}
    strJobInfo = document.patrolBean.jobInfo.value;
    if (strJobInfo.length>50){
      alert("内容过长，工作信息项只能输入50个字符");
      document.patrolBean.jobInfo.focus();
      return false;
    }
	return true;
}

function toGetBack(){
        var url = "${ctx}/patrolAction.do?method=queryPatrol";
        self.location.replace(url);

}

//-->
</script>
<br>
<template:titile value="修改巡检维护组信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/patrolAction.do?method=updatePatrol">
  <template:formTable >
    <template:formTr name="巡检维护组编号" isOdd="false" style="display:none">
      <html:text readonly="true" property="patrolID" styleClass="inputtext" style="width:200px"/>
    </template:formTr>
    <template:formTr name="巡检维护组">
      <html:text property="patrolName" styleClass="inputtext" style="width:200px" maxlength="10"/><font color="red"> *</font>
    </template:formTr>

    <template:formTr name="所属区域" style="display:none">
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid"/>
      <html:select property="regionID" styleClass="inputtext" style="width:200px">
        <html:options collection="regionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="所属单位" isOdd="false" >

      <html:select property="parentID" styleClass="inputtext" style="width:200px">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>

    <template:formTr name="工作信息" isOdd="false">
      <html:textarea property="jobInfo" styleClass="textarea" style="width:280px;" rows="2" cols="" ></html:textarea>
    </template:formTr>
    <!--
    <template:formTr name="工作信息">
      <html:text property="jobInfo" styleClass="inputtext" style="width:200px" maxlength="50"/>
    </template:formTr>
    -->
	<html:hidden property="state"/>
    <html:hidden property="remark"/>
	<template:formSubmit>
      <td>
        <html:submit styleClass="button">更新</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
