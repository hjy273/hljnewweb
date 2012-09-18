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
  condition = " WHERE state IS NULL  and parentid='"+userinfo.getDeptID()+"' ";
}
//省移动
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//代维
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE parentid ='"+userinfo.getDeptID()+"' and state is null";
}
if( userinfo.getDeptype().equals( "2" )  ){
 	List patrolCollection = statdao.getSelectForTag("patrolname","patrolid","patrolmaninfo",condition);
	request.setAttribute("patrolCollection",patrolCollection);
}
%>
<%@include file="/common/header.jsp"%>
<script type="text/javascript">
<!--
function queryPatrolGroup(){
	     		var contractorID = document.getElementById("contractorID").value;
	       		var params = "&contractorID="+contractorID;
	       		var ops = document.getElementById('ownerID');
	  			ops.options.length = 0;//清空下拉列表
	  			ops.options.add(new Option("请选择", ""));//增加提示信息
	  			var url = "${ctx}//terminalAction.do?method=getPatrolGroup"
	  			var myAjax = new Ajax.Request(url, {method:"post", parameters:params, onComplete:callback,asynchronous:true}); 
	     }
	    
	    function callback(originalRequest) {
	    		var rst = originalRequest.responseText;
	    		var queryRes = eval('('+rst+')');
	    		var ops = document.getElementById('ownerID');
	    		for(var i = 0 ; i < queryRes.length; i++) { 
	  				ops.options.add(new Option(queryRes[i].patrolname, queryRes[i].patrolid));
	  			}
	  			var myGlobalHandlers = {onCreate:function () {
				Element.show("Loadingimg");
				}, onFailure:function () {
					alert("网络连接出现问题，请关闭后重试!");
				}, onComplete:function () {
					if (Ajax.activeRequestCount == 0) {
						Element.hide("Loadingimg");
					}
				}};
				Ajax.Responders.register(myGlobalHandlers);
	    }
//-->
</script>
<br>
<template:titile value="查询巡检终端设备信息"/>
<html:form method="Post" action="/terminalAction.do?method=queryTerminal">
  <template:formTable >
    <template:formTr name="设备编号">
      <html:text property="terminalID" styleClass="inputtext" style="width:200px" maxlength="16" title="只需输入设备编号的后4位"/>
    </template:formTr>

    <logic:equal value="1" name="LOGIN_USER" property="deptype">
      <template:formTr name="代维单位" isOdd="false">
        <apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true"/>
        <html:select property="contractorID" styleId="contractorID" styleClass="inputtext" style="width:200px" onclick="queryPatrolGroup();">
          <html:option value="">不限</html:option>
          <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
    </logic:equal>
    <logic:equal value="group" name="PMType">
    	<template:formTr name="巡检维护组">
          <!-- 因为巡检组与代维单位未作关联，暂时将些下拉选项只显示不限 -->
	      <html:select property="ownerID" styleId="ownerID" styleClass="inputtext" style="width:200px">
	        <html:option value="">不限</html:option>
	        <logic:equal value="2" name="LOGIN_USER" property="deptype"> 
	        	<html:options collection="patrolCollection" property="value" labelProperty="label"/>
	        </logic:equal>
	      </html:select>
	    </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
	    <template:formTr name="持有巡检人">
	      <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true"/>
	      <html:select property="ownerID" styleClass="inputtext" style="width:200px">
	        <html:option value="">不限</html:option>
	        <html:option value="0">无</html:option>
	        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
	      </html:select>
	    </template:formTr>
    </logic:notEqual>
    <!-- 
    <template:formTr name="设备类型" isOdd="false">
      <html:select property="terminalType" styleClass="inputtext" style="width:200px">
        <html:option value="">不限</html:option>
      </html:select>       
      <apptag:quickLoadList cssClass="inputtext" name="layingmethod" style="width:200px" listName="terminalmodel" type="select"/>
      
    </template:formTr>
   
    <template:formTr name="设备型号" isOdd="false">
      <html:text property="terminalModel" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="SIM卡类型">
      <html:select property="simType" styleClass="inputtext" style="width:200px">
        <html:option value="">不限</html:option>
        <html:option value="0001">全球通</html:option>
        <html:option value="0002">神州行</html:option>

        <html:option value="0003">联通</html:option>
        <html:option value="0004">联通CDMA</html:option>

      </html:select>
    </template:formTr>
     -->
	<template:formTr name="SIM卡号" isOdd="false">
      <html:text property="simNumber" styleClass="inputtext" style="width:200px" maxlength="45"/>
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
<template:cssTable></template:cssTable>