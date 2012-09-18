<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@include file="/common/header.jsp"%>
<jsp:useBean id="terminalBean" class="com.cabletech.baseinfo.beans.TerminalBean" scope="request"/>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>
<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
//市移动
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
   condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
   condition+=" and p.state is null";
}
//市代维
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and parentid='"+userinfo.getDeptID()+"' ";
   condition+=" and p.state is null";
}
//省移动
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL and p.state is null";
}
//省代维
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE parentid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
    condition+=" and p.state is null";
}
List patrolCollection = statdao.getSelectForTag("patrolname","patrolid","patrolmaninfo p",condition);
request.setAttribute("patrolCollection",patrolCollection);
%>
<script language="JavaScript">
function isValidForm() {
	var deviceid = terminalBean.deviceID;
	if(deviceid.value==""){
		alert("设备编号不能为空!");
		return false;
	}
	if(valCharLength(deviceid.value)>8 || valCharLength(deviceid.value)<8){
		alert("设备编号长度必须为8位!");
		return false;
	}
	var formatNum=/\W/;
	if(formatNum.test(deviceid.value)){
		alert("设备编号只能是英文字母或是数字");	
		return false;
	}
 

    if(document.terminalBean.produceManId.value==""){
        alert("生产厂商不能为空!! ");
        return false;
    }

  
    if(document.terminalBean.terminalType.value==""){
        alert("设备类型不能为空!! ");
        return false;
    }
    
    if(document.terminalBean.machineSerial.value==""){
        alert("设备序列号（唯一标识）不能为空!! ");
        return false;
    }
   
    if(document.terminalBean.simNumber.value==""){
        alert("SIM卡号不能为空!! ");
        return false;
    }
  
    //刷新SMS缓存
    //top.bottomFrame.freshSmsCache();

    return true;
}

function valCharLength(Value){
		      var j=0;
		      var s = Value;
		      for(var i=0; i<s.length; i++) {
		        if (s.substr(i,1).charCodeAt(0)>255) {
		        	j  = j + 2;
		       	} else { 
		        	j++;
		        }
		      }
		      return j;
}

function toGetBack(){
        var url = "${ctx}/terminalAction.do?method=queryTerminal&produceMan=&terminalModel=&simNumber=";
        self.location.replace(url);

}
function viewStat(Value){
    var sel = Value;
    if(sel.indexOf("CT")==-1){
        //此设备为旧设备，显示设备状态
        document.getElementById('state').style.display = "block";
        //alert("test"+sel);
    }else{
        document.getElementById('state').style.display = "none";
        //新设备
        //alert("--"+sel);
    }
}

</script>
<bean:define id="temp" name="terminalBean" property="terminalModel" type="java.lang.String"/>
<body onload="viewStat('<%=temp%>')">
<br><br>
<template:titile value="修改巡检终端设备信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/terminalAction.do?method=updateTerminal">
  <template:formTable>
      <html:hidden property="terminalID" />
     <template:formTr name="设备编号" isOdd="false">
             <html:text property="deviceID" styleClass="inputtext" maxlength="8" style="width:200px"></html:text><font color="red"> *</font>
             <br/><font color="red">设备编号	为8位字符,格式为:公司(2位字母)+投入年份(2位数字)+0+序号(3位数字)</font>
      </template:formTr>
    <template:formTr name="设备密码" isOdd="false">
      <html:text property="password"  readonly="true" styleClass="inputtext" style="width:200px" maxlength="2"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="所属区域">
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionID" styleClass="inputtext" style="width:200px">
        <html:options collection="regionCellection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>
    <logic:equal value="1" name="LOGIN_USER" property="deptype">
      <template:formTr name="代维单位" isOdd="false">
        <apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname" columnName2="contractorid" region="true"/>
        <html:select property="contractorID" styleClass="inputtext" style="width:200px">
          <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select><font color="red"> *</font>
      </template:formTr>
    </logic:equal>
    <logic:equal value="2" name="LOGIN_USER" property="deptype">
      <template:formTr name="代维单位">
        <select name="contractorID" Class="inputtext" style="width:200px">
          <option value="<bean:write name="LOGIN_USER_DEPT_ID"/>"><bean:write name="LOGIN_USER_DEPT_NAME"/></option>
        </select><font color="red"> *</font>
      </template:formTr>
    </logic:equal>

    <logic:equal value="group" name="PMType">
        <template:formTr name="巡检维护组">
          <html:select property="ownerID" styleClass="inputtext" style="width:200px" name="terminalBean">
            <html:option value="0">无</html:option>
            <html:options collection="patrolCollection" property="value" labelProperty="label"/>
          </html:select><font color="red"> *</font>
        </template:formTr>
    </logic:equal>
    <logic:notEqual value="group" name="PMType">
            <template:formTr name="持有巡检人">
              <apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" region="true"/>
              <html:select property="ownerID" styleClass="inputtext" style="width:200px">
                <html:option value="0">无</html:option>
                <html:options collection="patrolCollection" property="value" labelProperty="label"/>
              </html:select><font color="red"> *</font>
            </template:formTr>
    </logic:notEqual>
 	<template:formTr name="设备持有人">
        	<html:text property="holder" styleClass="inputtext" style="width:200px"></html:text>
    </template:formTr>
    <template:formTr name="设备类型" isOdd="false">
      <html:select property="terminalType" styleClass="inputtext" style="width:200px">
        <html:option value="1">手持</html:option>
        <html:option value="2">车载</html:option>
        <html:option value="3">PDA</html:option>
        <html:option value="4">手机</html:option>
      </html:select><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="生产厂商">
      <html:text property="produceMan" styleClass="inputtext" readonly="true" styleId="produceManId" style="width:200px" maxlength="45"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="设备型号" isOdd="false">
    <html:hidden property="terminalModel"/>
      <html:select property="terminalModel" disabled="true" styleId="select"
                onChange="viewStat(this);" styleClass="inputtext" style="width:200px">
                <html:option value="CT-1200">CT-1200</html:option>
                <html:option value="CT-6100">CT-6100</html:option>
                <html:option value="CT-6610">CT-6610</html:option>
                <html:option value="NV-2000">NV-2000</html:option>
            </html:select><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="设备序列号">
      <html:text property="machineSerial" styleClass="inputtext" style="width:200px" maxlength="45"/><font color="red"> *</font>
    </template:formTr>
     <template:formTr name="设备识别码（IMEI）">
            <html:text property="IMEI" styleId="IMEI_ID" styleClass="inputtext"
                style="width:200px" maxlength="16" /><font color="red"> * </font>
        </template:formTr>
    <template:formTr name="SIM卡类型" isOdd="false">
      <html:select property="simType" styleClass="inputtext" style="width:200px">
        <html:option value="0001">全球通</html:option>
        <html:option value="0002">神州行</html:option><!--
        <html:option value="0003">联通</html:option>
        <html:option value="0004">联通CDMA</html:option>-->
      </html:select><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="SIM卡号">
      <html:text property="simNumber" styleClass="inputtext" style="width:200px" maxlength="11"/>
    </template:formTr>
    <template:formTr name="设备状态" isOdd="false" tagID="state"
            style="display:none">
      <html:select property="state" styleClass="inputtext" style="width:200px">
        <html:option value="00">创建线路</html:option>
        <html:option value="01">日常巡检</html:option>
      </html:select><font color="red"> *</font>
    </template:formTr>
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
<template:cssTable></template:cssTable>
</body>

