<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include
	file="/common/header.jsp"%>
<jsp:useBean id="terminalBean"
	class="com.cabletech.mam.beans.TerminalBean" scope="request" />
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<script language="javascript" src="${ctx}/js/prototype.js"
	type=""></script>
<jsp:useBean id="selectForTag"
	class="com.cabletech.commons.tags.SelectForTag" scope="request" />
<%
    String condition = "";
    UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
    //市移动
    if (userinfo.getDeptype().equals("1")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE regionid IN ('" + userinfo.getRegionID()
                + "') AND state IS NULL";
    }
    //市代维
    if (userinfo.getDeptype().equals("2")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL  and parentid='" + userinfo.getDeptID() + "' ";
    }
    //省移动
    if (userinfo.getDeptype().equals("1")
            && userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL";
    }
    //省代维
    if (userinfo.getDeptype().equals("2")
            && userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE parentid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                + userinfo.getDeptID() + "')";
    }
    List patrolCollection = selectForTag.getSelectForTag("patrolname", "patrolid",
            "patrolmaninfo", condition);
    request.setAttribute("patrolCollection", patrolCollection);
%>
<script language="JavaScript">
function getPatrolMans(){
	var contractorId = $('contractorID').value;
	var url = "${ctx}/patrolAction.do?method=getPatrolMansByParentId&&parentID="+contractorId+"&&rnd="+Math.random();
	new Ajax.Request(encodeURI(url),{
		method: 'post',
       	evalScripts: true,
       	onSuccess: function(transport){
       		var str = transport.responseText;
       		document.all.ownerID.options.length = 0;
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
			alert("巡检组未知异常！");
		}       		
	});
}
function isValidForm() {


    if(document.terminalBean.produceManId.value==""){
        alert("生产厂商不能为空!! ");
        return false;
    }
    if(document.terminalBean.produceManId.value.trim()==""){
        alert("生产厂商不能为空格!! ");
        return false;
    }
    if(document.terminalBean.terminalID.value==""){
        alert("设备编号不能为空!! ");
        return false;
    }
    if(document.terminalBean.terminalID.value.trim()==""){
        alert("设备编号不能为空格!! ");
        return false;
    }
    if(document.terminalBean.terminalType.value==""){
        alert("设备类型不能为空!! ");
        return false;
    }
    if(document.terminalBean.terminalType.value.trim()==""){
        alert("设备类型不能为空格!! ");
        return false;
    }
    if(document.terminalBean.machineSerial.value==""){
        alert("设备序列号（唯一标识）不能为空!! ");
        return false;
    }
    if(document.terminalBean.machineSerial.value.trim()==""){
        alert("设备序列号（唯一标识）不能为空格!! ");
        return false;
    }
    if(document.terminalBean.simNumber.value==""){
        alert("SIM卡号不能为空!! ");
        return false;
    }
    if(document.terminalBean.simNumber.value.trim()==""){
        alert("SIM卡号不能为空格!! ");
        return false;
    }
    //IMEI验证：14-15位，字母A-F,a-f+数字0-9
    var regxImei=/^[A-Fa-f0-9]{14,15}$/;
    if(!regxImei.test(terminalBean.imei.value)){
    	alert("IMEI（GSM或是WCDMA制式终端）必须为15位数字或者MEID(CDMA制式终端)14位0-9A-F!");
    	return false;
    }
    if(judgeExist()){
    	alert("IMEI/MEID已经存在！");
    	return false;
    }
    //刷新SMS缓存
    //top.bottomFrame.freshSmsCache();

    return true;
}


    	judgeExist=function(){
			var flag=false;
			var imei=terminalBean.imei.value;
			var queryString="imei="+imei+"&&terminalID=<bean:write name='terminalBean' property='terminalID' />&rnd="+Math.random();
			var actionUrl="${ctx}/terminalAction.do?method=judgeExistImei&&"+queryString;
			new Ajax.Request(actionUrl, 
				{
					method:"post", 
					onSuccess:function (transport) {
						if(transport.responseText=='1'){
							flag=true;
						}
					}, 
					asynchronous:false
				}
			);
			return flag;
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
<bean:define id="temp" name="terminalBean" property="terminalModel"
	type="java.lang.String" />
<body onload="viewStat('<%=temp%>')">
	<template:titile value="修改手持终端设备信息" />
	<html:form onsubmit="return isValidForm()" method="Post"
		action="/terminalAction.do?method=updateTerminal">
		<template:formTable contentwidth="200" namewidth="300">
			<template:formTr name="设备编号">
				<html:hidden property="terminalID" />
				<input name="deviceID" class="inputtext" style="width: 160"
					value="${terminalBean.deviceID}" readonly="readonly" maxlength="8" />(该项不可修改)
				<font color="#FF0000">*</font>
    		</template:formTr>
			<template:formTr name="设备密码">
				<html:text property="password" readonly="true"
					styleClass="inputtext" style="width:160" maxlength="2" />
			</template:formTr>
			<template:formTr name="所属区域">
				<apptag:setSelectOptions valueName="regionCellection"
					tableName="region" columnName1="regionname" region="true"
					columnName2="regionid" order="regionid"
					condition="substr(REGIONID,3,4) != '1111' " />
				<html:select property="regionID" styleClass="inputtext"
					style="width:160">
					<html:options collection="regionCellection" property="value"
						labelProperty="label" />
				</html:select>
			</template:formTr>
			<logic:equal value="1" name="LOGIN_USER" property="deptype">
				<template:formTr name="代维单位">
					<apptag:setSelectOptions valueName="deptCollection"
						tableName="contractorinfo" columnName1="contractorname"
						columnName2="contractorid" region="true" />
					<html:select property="contractorID" styleClass="inputtext"
						style="width:160" onchange="getPatrolMans();">
						<html:options collection="deptCollection" property="value"
							labelProperty="label" />
					</html:select>
				</template:formTr>
			</logic:equal>
			<logic:equal value="2" name="LOGIN_USER" property="deptype">
				<template:formTr name="代维单位">
					<select name="contractorID" Class="inputtext" style="width: 160">
						<option value="${LOGIN_USER.deptID }">
							${LOGIN_USER.deptName }
						</option>
					</select>
				</template:formTr>
			</logic:equal>

			<logic:equal value="group" name="PMType">
				<template:formTr name="巡检维护组">
					<select name="ownerID" class="inputtext" style="width:160">
					</select>
				</template:formTr>
			</logic:equal>
			<logic:notEqual value="group" name="PMType">
				<template:formTr name="持有巡检人">
					<apptag:setSelectOptions valueName="patrolCollection"
						tableName="patrolmaninfo" columnName1="patrolname"
						columnName2="patrolid" region="true" />
					<select name="ownerID" class="inputtext" style="width:160">
					</select>
				</template:formTr>
			</logic:notEqual>
			<template:formTr name="设备类型">
				<html:select property="terminalType" styleClass="inputtext"
					style="width:160">
					<html:option value="1">手持</html:option>
					<html:option value="2">车载</html:option>
					<html:option value="3">PDA</html:option>
					<html:option value="4">手机</html:option>
				</html:select>
				<font color="#FF0000">*</font>
			</template:formTr>
			<template:formTr name="生产厂商">
				<html:text property="produceMan" styleClass="inputtext"
					styleId="produceManId" style="width:160" maxlength="45" />
				<font color="#FF0000">*</font>
			</template:formTr>
			<template:formTr name="设备型号">
				<html:hidden property="terminalModel" />
				<html:select property="terminalModel" disabled="true"
					styleId="select" onchange="viewStat(this);" styleClass="inputtext"
					style="width:160">
					<html:option value="CT-1200">CT-1200</html:option>
					<html:option value="CT-6100">CT-6100</html:option>
					<html:option value="CT-6610">CT-6610</html:option>
					<html:option value="NV-2000">NV-2000</html:option>
					<html:option value="other">其他</html:option>
				</html:select>
			</template:formTr>
			<template:formTr name="IMEI/MEID">
				<html:text property="imei" styleClass="inputtext" style="width:160"
					maxlength="20" />
				<font color="#FF0000">*</font>
				<br />
				说明：IMEI（GSM或是WCDMA制式终端）15位数字/MEID(CDMA制式终端)14位0-9A-F
			</template:formTr>
			<template:formTr name="设备序列号">
				<html:text property="machineSerial" styleClass="inputtext"
					style="width:160" maxlength="45" />
				<font color="#FF0000">*</font>
			</template:formTr>
			<template:formTr name="SIM卡类型">
				<html:select property="simType" styleClass="inputtext"
					style="width:160">
					<html:option value="0001">全球通</html:option>
					<html:option value="0002">神州行</html:option>
					<!--
        			<html:option value="0003">联通</html:option>
        			<html:option value="0004">联通CDMA</html:option>-->
				</html:select>
			</template:formTr>
			<template:formTr name="SIM卡号">
				<html:text property="simNumber" styleClass="inputtext"
					style="width:160" maxlength="11" />
				<font color="#FF0000">*</font>
			</template:formTr>
			<template:formTr name="设备状态" tagID="state" style="display:none">
				<html:select property="state" styleClass="inputtext"
					style="width:160">
					<html:option value="00">资源采集</html:option>
					<html:option value="01">日常巡检</html:option>
				</html:select>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit styleClass="button">更新</html:submit>
				</td>
				<td>
					<input type="button" class="button" onclick="toGetBack()"
						value="返回">
				</td>
			</template:formSubmit>
		</template:formTable>
	</html:form>
</body>
<script language="javascript" type="">
getPatrolMans();
</script>
