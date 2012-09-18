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
	//���ƶ�
	if (userinfo.getDeptype().equals("1")
			&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
		condition = " WHERE regionid IN ('" + userinfo.getRegionID()
				+ "') AND state IS NULL";
	}
	//�д�ά
	if (userinfo.getDeptype().equals("2")
			&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
		condition = " WHERE state IS NULL  and parentid='"
				+ userinfo.getDeptID() + "' ";
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
         //alert("ֻ����д4λ����,����������");
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
			alert("Ѳ����δ֪�쳣��");
		}       		
	});
}
function isValidForm() {
    var sel = terminalBean.terminalModel.value;
    /*if(sel.indexOf("CT")==-1){
      if( !valiMachineID("maID")){
          alert("�豸ʶ���ʶ���豸�趨��ֻ����д4λ����,���������룡");
        return false;
      }
     }else{*/
        
     //}

    if(document.terminalBean.deviceID.value==""){
        alert("�豸��Ų���Ϊ��!! ");
        document.terminalBean.deviceID.focus();
        return false;
    }
    if(document.terminalBean.deviceID.value.trim()==""){
        alert("�豸��Ų���Ϊ�ո�!! ");
        document.terminalBean.deviceID.focus();
        return false;
    }
    if(terminalBean.deviceID.value.length != 8){
        alert("�豸���ֻ��Ϊ8λ�ַ�������,���������룡");
        document.terminalBean.deviceID.focus();
        return false;
    }
    
    if(terminalBean.produceMan.value==""){
    	document.terminalBean.produceMan.focus();
        alert("�������̲���Ϊ��!! ");
        return false;
    }
    if(terminalBean.produceMan.value.trim()==""){
    	document.terminalBean.produceMan.focus();
        alert("�������̲���Ϊ�ո�!! ");
        return false;
    }
    if(terminalBean.terminalType.value==""){
    	document.terminalBean.terminalType.focus();
        alert("�豸���Ͳ���Ϊ��!! ");
        return false;
    }
    if(terminalBean.terminalType.value.trim()==""){
    	document.terminalBean.terminalType.focus();
        alert("�豸���Ͳ���Ϊ�ո�!! ");
        return false;
    }
    if(terminalBean.machineSerial.value==""){
    	document.terminalBean.machineSerial.focus();
        alert("�豸���кţ�Ψһ��ʶ������Ϊ��!! ");
        return false;
    }
    if(terminalBean.machineSerial.value.trim()==""){
    	document.terminalBean.machineSerial.focus();
        alert("�豸���кţ�Ψһ��ʶ������Ϊ�ո�!! ");
        return false;
    }
    if(terminalBean.simNumber.value==""){
    	document.terminalBean.simNumber.focus();
        alert("SIM���Ų���Ϊ��!! ");
        return false;
    }
    if(terminalBean.simNumber.value.trim()==""){
    	document.terminalBean.simNumber.focus();
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
<template:titile value="�����ֳ��ն��豸��Ϣ" />
<html:form onsubmit="return isValidForm();" method="Post" action="/terminalAction.do?method=addTerminal">
	<template:formTable contentwidth="300" namewidth="200">

		<template:formTr name="�豸���">
			<input name="deviceID" id="deviceId" title="�豸���Ϊ����2��+��ά��2��+�豸��ţ�4����������Ϫ��ʵ�豸��04cs0020" class="inputtext"
				style="width: 200" maxlength="8" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="�豸����">
			<html:text property="password" value="00" styleClass="inputtext" style="width:200" maxlength="2" />
		</template:formTr>
		<logic:equal value="1" name="LOGIN_USER" property="deptype">
			<template:formTr name="��ά��λ">
				<apptag:setSelectOptions valueName="deptCollection" tableName="contractorinfo" columnName1="contractorname"
					columnName2="contractorid" region="true" />
				<html:select property="contractorID" styleClass="inputtext" style="width:200" alt="��ά��λ" onchange="getPatrolMans();">
					<html:options collection="deptCollection" property="value" labelProperty="label" />
				</html:select>
			</template:formTr>
		</logic:equal>
		<logic:equal value="2" name="LOGIN_USER" property="deptype">
			<template:formTr name="��ά��λ">
				<select name="contractorID" Class="inputtext" style="width: 200">
					<option value="${LOGIN_USER.deptID }">${LOGIN_USER.deptName }</option>
				</select>
			</template:formTr>
		</logic:equal>

		<logic:equal value="group" name="PMType">
			<template:formTr name="Ѳ��ά����">
				<select name="ownerID" class="inputtext" style="width:200">
				</select>
			</template:formTr>
		</logic:equal>
		<logic:notEqual value="group" name="PMType">
			<template:formTr name="����Ѳ����">
				<apptag:setSelectOptions valueName="patrolCollection" tableName="patrolmaninfo" columnName1="patrolname"
					columnName2="patrolid" region="true" />
				<select name="ownerID" class="inputtext" style="width:200">
					<option value="0">��</option>
				</select>
			</template:formTr>
		</logic:notEqual>
		<template:formTr name="�豸����">
			<html:select property="terminalType" styleClass="inputtext" style="width:200">
				<html:option value="1">�ֳ�</html:option>
				<html:option value="2">����</html:option>
				<html:option value="3">PDA</html:option>
				<html:option value="4">�����ֻ�</html:option>
			</html:select>
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="��������">
			<html:text property="produceMan" styleId="produceManId" styleClass="inputtext" style="width:200" maxlength="10" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="�豸�ͺ�">
			<html:select property="terminalModel" styleId="select" onchange="viewStat(this);" styleClass="inputtext"
				style="width:200">
				<html:option value="CT-1200">CT-1200</html:option>
				<html:option value="CT-6100">CT-6100</html:option>
				<html:option value="CT-6610">CT-6610</html:option>
				<html:option value="NV-2000">NV-2000</html:option>
				<html:option value="other">����</html:option>
			</html:select>
		</template:formTr>
		<template:formTr name="IMEI/MEID">
			<html:text property="imei" styleClass="inputtext" style="width:200" maxlength="20" />
			<font color="#FF0000">*</font>
			<br/>
			˵����IMEI��GSM����WCDMA��ʽ�նˣ�15λ����/MEID(CDMA��ʽ�ն�)14λ0-9A-F
		</template:formTr>
		<template:formTr name="�豸���к�">
			<html:text property="machineSerial" styleClass="inputtext" style="width:200" maxlength="50" />
			<font color="#FF0000">*</font>
		</template:formTr>
		<template:formTr name="SIM������">
			<html:select property="simType" styleClass="inputtext" style="width:200">
				<html:option value="0001">ȫ��ͨ</html:option>
				<html:option value="0002">������</html:option>
				<!--
        <html:option value="0003">��ͨ</html:option>
        <html:option value="0004">��ͨCDMA</html:option>-->
			</html:select>
		</template:formTr>
		<template:formTr name="SIM����">
			<html:text property="simNumber" styleClass="inputtext" style="width:200" maxlength="11" />
			<font color="#FF0000">*</font>
		</template:formTr>
		
		
		<template:formTr name="�豸״̬" tagID="state" style="display:none">
			<html:select property="state" styleClass="inputtext" style="width:200">
				<html:option value="00">��Դ�ɼ�</html:option>
				<html:option value="01">�ճ�Ѳ��</html:option>
			</html:select>
		</template:formTr>

		<template:formSubmit>
			<td><html:submit styleClass="button">����</html:submit>
			</td>
			<td><html:reset styleClass="button">ȡ��</html:reset>
			</td>
			<td><input type="button" class="button" onclick="toGetBack()" value="����">
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
<script type="text/javascript">
getPatrolMans();
</script>
