<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<script language="javascript" src="${ctx}/js/prototype.js" type=""></script>
<jsp:useBean id="patrolmanBean" class="com.cabletech.mam.beans.PatrolManBean" scope="request" />
<jsp:useBean id="terminalBean" class="com.cabletech.mam.beans.TerminalBean" scope="request" />
<jsp:useBean id="selectForTag" class="com.cabletech.commons.tags.SelectForTag" scope="request" />
<%
	String condition = "";
	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
			"LOGIN_USER");
	//市移动
	if (userinfo.getDeptype().equals("1")
			&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
		condition = " WHERE regionid IN ('" + userinfo.getRegionID()
				+ "') AND state IS NULL";
	}
	//市代维
	if (userinfo.getDeptype().equals("2")
			&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
		condition = " WHERE state IS NULL  and parentid='"
				+ userinfo.getDeptID() + "' ";
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
	List patrolCollection = selectForTag.getSelectForTag("patrolname",
			"patrolid", "patrolmaninfo", condition);
	request.setAttribute("patrolCollection", patrolCollection);
%>
<script language="JavaScript" type="text/javascript">
function valiMachineID(id){
      var obj = document.getElementById(id);

      var mysplit = /\d\d\d\d/;
      if(mysplit.test(obj.value)){
         return true;
      }
      else{
         //alert("只能填写4位数字,请重新输入");
        obj.value="";
        obj.focus();
        return false;
      }
}
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
    var sel = terminalBean.terminalModel.value;
    /*if(sel.indexOf("CT")==-1){
      if( !valiMachineID("maID")){
          alert("设备识别标识（设备设定）只能填写4位数字,请重新输入！");
        return false;
      }
     }else{*/
        
     //}

    if(document.terminalBean.deviceID.value==""){
        alert("设备编号不能为空!! ");
        document.terminalBean.deviceID.focus();
        return false;
    }
    if(document.terminalBean.deviceID.value.trim()==""){
        alert("设备编号不能为空格!! ");
        document.terminalBean.deviceID.focus();
        return false;
    }
    if(terminalBean.deviceID.value.length != 8){
        alert("设备编号只能为8位字符或数据,请重新输入！");
        document.terminalBean.deviceID.focus();
        return false;
    }
    
    if(terminalBean.produceMan.value==""){
    	document.terminalBean.produceMan.focus();
        alert("生产厂商不能为空!! ");
        return false;
    }
    if(terminalBean.produceMan.value.trim()==""){
    	document.terminalBean.produceMan.focus();
        alert("生产厂商不能为空格!! ");
        return false;
    }
    if(terminalBean.terminalType.value==""){
    	document.terminalBean.terminalType.focus();
        alert("设备类型不能为空!! ");
        return false;
    }
    if(terminalBean.terminalType.value.trim()==""){
    	document.terminalBean.terminalType.focus();
        alert("设备类型不能为空格!! ");
        return false;
    }
    if(terminalBean.machineSerial.value==""){
    	document.terminalBean.machineSerial.focus();
        alert("设备序列号（唯一标识）不能为空!! ");
        return false;
    }
    if(terminalBean.machineSerial.value.trim()==""){
    	document.terminalBean.machineSerial.focus();
        alert("设备序列号（唯一标识）不能为空格!! ");
        return false;
    }
    if(terminalBean.simNumber.value==""){
    	document.terminalBean.simNumber.focus();
        alert("SIM卡号不能为空!! ");
        return false;
    }
    if(terminalBean.simNumber.value.trim()==""){
    	document.terminalBean.simNumber.focus();
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
    return true;
}
    	judgeExist=function(){
			var flag=false;
			var imei=terminalBean.imei.value;
			var queryString="imei="+imei+"&&terminalID=-1&rnd="+Math.random();
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
function viewStat(){
    var sel = terminalBean.terminalModel.value;
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
<template:titile value="增加手持终端设备信息" />
<html:form onsubmit="return isValidForm();" method="Post" action="/terminalAction.do?method=addTerminal">
	<template:formTable contentwidth="300" namewidth="200">

		<template:formTr name="设备编号">
			<input name="deviceID" id="deviceId" title="设备编号为区域（2）+代维（2）+设备编号（4），例如玉溪长实设备：04cs0020" class="inputtext"
				style="width: 200" maxlength="8" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="设备密码">
			<html:text property="password" value="00" styleClass="inputtext" style="width:200" maxlength="2" />
		</template:formTr>
		<logic:equal value="1" name="LOGIN_USER" property="deptype">
			<template:formTr name="代维单位">
				<apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname"
					columnName2="contractorid" region="true" />
				<html:select property="contractorID" styleClass="inputtext" style="width:200" alt="代维单位" onchange="getPatrolMans();">
					<html:options collection="deptCollection" property="value" labelProperty="label" />
				</html:select>
			</template:formTr>
		</logic:equal>
		<logic:equal value="2" name="LOGIN_USER" property="deptype">
			<template:formTr name="代维单位">
				<select name="contractorID" Class="inputtext" style="width: 200">
					<option value="${LOGIN_USER.deptID }">${LOGIN_USER.deptName }</option>
				</select>
			</template:formTr>
		</logic:equal>

		<logic:equal value="group" name="PMType">
			<template:formTr name="巡检维护组">
				<select name="ownerID" class="inputtext" style="width:200">
				</select>
			</template:formTr>
		</logic:equal>
		<logic:notEqual value="group" name="PMType">
			<template:formTr name="持有巡检人">
				<apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname"
					columnName2="patrolid" region="true" />
				<select name="ownerID" class="inputtext" style="width:200">
					<option value="0">无</option>
				</select>
			</template:formTr>
		</logic:notEqual>
		<template:formTr name="设备类型">
			<html:select property="terminalType" styleClass="inputtext" style="width:200">
				<html:option value="1">手持</html:option>
				<html:option value="2">车载</html:option>
				<html:option value="3">PDA</html:option>
				<html:option value="4">智能手机</html:option>
			</html:select>
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="生产厂商">
			<html:text property="produceMan" styleId="produceManId" styleClass="inputtext" style="width:200" maxlength="10" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="设备型号">
			<html:select property="terminalModel" styleId="select" onchange="viewStat(this);" styleClass="inputtext"
				style="width:200">
				<html:option value="CT-1200">CT-1200</html:option>
				<html:option value="CT-6100">CT-6100</html:option>
				<html:option value="CT-6610">CT-6610</html:option>
				<html:option value="NV-2000">NV-2000</html:option>
				<html:option value="other">其他</html:option>
			</html:select>
		</template:formTr>
		<template:formTr name="IMEI/MEID">
			<html:text property="imei" styleClass="inputtext" style="width:200" maxlength="20" />
			<font color="#FF0000">*</font>
			<br/>
			说明：IMEI（GSM或是WCDMA制式终端）15位数字/MEID(CDMA制式终端)14位0-9A-F
		</template:formTr>
		<template:formTr name="设备序列号">
			<html:text property="machineSerial" styleClass="inputtext" style="width:200" maxlength="50" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="SIM卡类型">
			<html:select property="simType" styleClass="inputtext" style="width:200">
				<html:option value="0001">全球通</html:option>
				<html:option value="0002">神州行</html:option>
				<!--
        <html:option value="0003">联通</html:option>
        <html:option value="0004">联通CDMA</html:option>-->
			</html:select>
		</template:formTr>
		<template:formTr name="SIM卡号">
			<html:text property="simNumber" styleClass="inputtext" style="width:200" maxlength="11" />
			<font color="#FF0000">*</font>
		</template:formTr>
		
		
		<template:formTr name="设备状态" tagID="state" style="display:none">
			<html:select property="state" styleClass="inputtext" style="width:200">
				<html:option value="00">资源采集</html:option>
				<html:option value="01">日常巡检</html:option>
			</html:select>
		</template:formTr>

		<template:formSubmit>
			<td><html:submit styleClass="button">增加</html:submit>
			</td>
			<td><html:reset styleClass="button">取消</html:reset>
			</td>
			<td><input type="button" class="button" onclick="toGetBack()" value="返回">
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
<script type="text/javascript">
getPatrolMans();
</script>
