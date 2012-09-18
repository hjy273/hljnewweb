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
    //���ƶ�
    if (userinfo.getDeptype().equals("1")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE regionid IN ('" + userinfo.getRegionID()
                + "') AND state IS NULL";
    }
    //�д�ά
    if (userinfo.getDeptype().equals("2")
            && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL  and parentid='" + userinfo.getDeptID() + "' ";
    }
    //ʡ�ƶ�
    if (userinfo.getDeptype().equals("1")
            && userinfo.getRegionID().substring(2, 6).equals("0000")) {
        condition = " WHERE state IS NULL";
    }
    //ʡ��ά
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
			alert("Ѳ����δ֪�쳣��");
		}       		
	});
}
function isValidForm() {


    if(document.terminalBean.produceManId.value==""){
        alert("�������̲���Ϊ��!! ");
        return false;
    }
    if(document.terminalBean.produceManId.value.trim()==""){
        alert("�������̲���Ϊ�ո�!! ");
        return false;
    }
    if(document.terminalBean.terminalID.value==""){
        alert("�豸��Ų���Ϊ��!! ");
        return false;
    }
    if(document.terminalBean.terminalID.value.trim()==""){
        alert("�豸��Ų���Ϊ�ո�!! ");
        return false;
    }
    if(document.terminalBean.terminalType.value==""){
        alert("�豸���Ͳ���Ϊ��!! ");
        return false;
    }
    if(document.terminalBean.terminalType.value.trim()==""){
        alert("�豸���Ͳ���Ϊ�ո�!! ");
        return false;
    }
    if(document.terminalBean.machineSerial.value==""){
        alert("�豸���кţ�Ψһ��ʶ������Ϊ��!! ");
        return false;
    }
    if(document.terminalBean.machineSerial.value.trim()==""){
        alert("�豸���кţ�Ψһ��ʶ������Ϊ�ո�!! ");
        return false;
    }
    if(document.terminalBean.simNumber.value==""){
        alert("SIM���Ų���Ϊ��!! ");
        return false;
    }
    if(document.terminalBean.simNumber.value.trim()==""){
        alert("SIM���Ų���Ϊ�ո�!! ");
        return false;
    }
    //IMEI��֤��14-15λ����ĸA-F,a-f+����0-9
    var regxImei=/^[A-Fa-f0-9]{14,15}$/;
    if(!regxImei.test(terminalBean.imei.value)){
    	alert("IMEI��GSM����WCDMA��ʽ�նˣ�����Ϊ15λ���ֻ���MEID(CDMA��ʽ�ն�)14λ0-9A-F!");
    	return false;
    }
    if(judgeExist()){
    	alert("IMEI/MEID�Ѿ����ڣ�");
    	return false;
    }
    //ˢ��SMS����
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
        //���豸Ϊ���豸����ʾ�豸״̬
        document.getElementById('state').style.display = "block";
        //alert("test"+sel);
    }else{
        document.getElementById('state').style.display = "none";
        //���豸
        //alert("--"+sel);
    }
}

</script>
<bean:define id="temp" name="terminalBean" property="terminalModel"
	type="java.lang.String" />
<body onload="viewStat('<%=temp%>')">
	<template:titile value="�޸��ֳ��ն��豸��Ϣ" />
	<html:form onsubmit="return isValidForm()" method="Post"
		action="/terminalAction.do?method=updateTerminal">
		<template:formTable contentwidth="200" namewidth="300">
			<template:formTr name="�豸���">
				<html:hidden property="terminalID" />
				<input name="deviceID" class="inputtext" style="width: 160"
					value="${terminalBean.deviceID}" readonly="readonly" maxlength="8" />(������޸�)
				<font color="#FF0000">*</font>
    		</template:formTr>
			<template:formTr name="�豸����">
				<html:text property="password" readonly="true"
					styleClass="inputtext" style="width:160" maxlength="2" />
			</template:formTr>
			<template:formTr name="��������">
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
				<template:formTr name="��ά��λ">
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
				<template:formTr name="��ά��λ">
					<select name="contractorID" Class="inputtext" style="width: 160">
						<option value="${LOGIN_USER.deptID }">
							${LOGIN_USER.deptName }
						</option>
					</select>
				</template:formTr>
			</logic:equal>

			<logic:equal value="group" name="PMType">
				<template:formTr name="Ѳ��ά����">
					<select name="ownerID" class="inputtext" style="width:160">
					</select>
				</template:formTr>
			</logic:equal>
			<logic:notEqual value="group" name="PMType">
				<template:formTr name="����Ѳ����">
					<apptag:setSelectOptions valueName="patrolCollection"
						tableName="patrolmaninfo" columnName1="patrolname"
						columnName2="patrolid" region="true" />
					<select name="ownerID" class="inputtext" style="width:160">
					</select>
				</template:formTr>
			</logic:notEqual>
			<template:formTr name="�豸����">
				<html:select property="terminalType" styleClass="inputtext"
					style="width:160">
					<html:option value="1">�ֳ�</html:option>
					<html:option value="2">����</html:option>
					<html:option value="3">PDA</html:option>
					<html:option value="4">�ֻ�</html:option>
				</html:select>
				<font color="#FF0000">*</font>
			</template:formTr>
			<template:formTr name="��������">
				<html:text property="produceMan" styleClass="inputtext"
					styleId="produceManId" style="width:160" maxlength="45" />
				<font color="#FF0000">*</font>
			</template:formTr>
			<template:formTr name="�豸�ͺ�">
				<html:hidden property="terminalModel" />
				<html:select property="terminalModel" disabled="true"
					styleId="select" onchange="viewStat(this);" styleClass="inputtext"
					style="width:160">
					<html:option value="CT-1200">CT-1200</html:option>
					<html:option value="CT-6100">CT-6100</html:option>
					<html:option value="CT-6610">CT-6610</html:option>
					<html:option value="NV-2000">NV-2000</html:option>
					<html:option value="other">����</html:option>
				</html:select>
			</template:formTr>
			<template:formTr name="IMEI/MEID">
				<html:text property="imei" styleClass="inputtext" style="width:160"
					maxlength="20" />
				<font color="#FF0000">*</font>
				<br />
				˵����IMEI��GSM����WCDMA��ʽ�նˣ�15λ����/MEID(CDMA��ʽ�ն�)14λ0-9A-F
			</template:formTr>
			<template:formTr name="�豸���к�">
				<html:text property="machineSerial" styleClass="inputtext"
					style="width:160" maxlength="45" />
				<font color="#FF0000">*</font>
			</template:formTr>
			<template:formTr name="SIM������">
				<html:select property="simType" styleClass="inputtext"
					style="width:160">
					<html:option value="0001">ȫ��ͨ</html:option>
					<html:option value="0002">������</html:option>
					<!--
        			<html:option value="0003">��ͨ</html:option>
        			<html:option value="0004">��ͨCDMA</html:option>-->
				</html:select>
			</template:formTr>
			<template:formTr name="SIM����">
				<html:text property="simNumber" styleClass="inputtext"
					style="width:160" maxlength="11" />
				<font color="#FF0000">*</font>
			</template:formTr>
			<template:formTr name="�豸״̬" tagID="state" style="display:none">
				<html:select property="state" styleClass="inputtext"
					style="width:160">
					<html:option value="00">��Դ�ɼ�</html:option>
					<html:option value="01">�ճ�Ѳ��</html:option>
				</html:select>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit styleClass="button">����</html:submit>
				</td>
				<td>
					<input type="button" class="button" onclick="toGetBack()"
						value="����">
				</td>
			</template:formSubmit>
		</template:formTable>
	</html:form>
</body>
<script language="javascript" type="">
getPatrolMans();
</script>
