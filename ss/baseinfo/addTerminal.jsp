<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="patrolmanBean"
    class="com.cabletech.baseinfo.beans.PatrolManBean" scope="request" />
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao"
    scope="request" />
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
        + userinfo.getDeptID() + "') and state is null ";
    }
    List patrolCollection = statdao.getSelectForTag("patrolname",
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

function isValidForm() {
	var deviceid = terminalBean.deviceID;
	if(deviceid.value==""){
		alert("�豸��Ų���Ϊ��!");
		return false;
	}
	if(valCharLength(deviceid.value)>8 || valCharLength(deviceid.value)<8){
		alert("�豸��ų��ȱ���Ϊ8λ!");
		return false;
	}
	var formatNum=/\W/;
	if(formatNum.test(deviceid.value)){
		alert("�豸���ֻ����Ӣ����ĸ��������!");	
		return false;
	}

  
    if(document.terminalBean.produceManId.value==""){
        alert("�������̲���Ϊ��!! ");
        return false;
    }
   
    if(document.terminalBean.machineSerialid.value==""){
        alert("�豸���кţ�Ψһ��ʶ������Ϊ��!! ");
        return false;
    }
    
    if(document.terminalBean.simNumber.value==""){
        alert("SIM���Ų���Ϊ��!! ");
        return false;
    }
    
    //return true;
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
<br>
<template:titile value="����Ѳ���ն��豸��Ϣ" />
<html:form onsubmit="return isValidForm();" method="Post"
    action="/terminalAction.do?method=addTerminal">
    <template:formTable >
        <template:formTr name="�豸���" isOdd="false">
             <html:text property="deviceID" styleClass="inputtext" maxlength="8" style="width:200px"></html:text><font color="red"> *</font>
             <br/><font color="red">�豸���Ϊ8λ�ַ�,��ʽΪ:��˾(2λ��ĸ)+Ͷ�����(2λ����)+0+���(3λ����)</font>
        </template:formTr>
        <template:formTr name="�豸����">
            <html:text property="password" value="00" readonly="true"
                styleClass="inputtext" style="width:200px" maxlength="2" /><font color="red"> *</font>
        </template:formTr>
        <logic:equal value="1" name="LOGIN_USER" property="deptype">
            <template:formTr name="��ά��λ" isOdd="false">
                <apptag:setSelectOptions valueName="deptCollection"
                    tableName="contractorinfo" columnName1="contractorname"
                    columnName2="contractorid" region="true" />
                <html:select property="contractorID" styleClass="inputtext"
                    style="width:200px" alt="��ά��λ">
                    <html:options collection="deptCollection" property="value"
                        labelProperty="label" />
                </html:select><font color="red"> *</font>
            </template:formTr>
        </logic:equal>
        <logic:equal value="2" name="LOGIN_USER" property="deptype">
            <template:formTr name="��ά��λ">
                <select name="contractorID" Class="inputtext" style="width:200px">
                    <option value="<bean:write name="LOGIN_USER_DEPT_ID"/>"><bean:write
                        name="LOGIN_USER_DEPT_NAME" /></option>
                </select><font color="red"> *</font>
            </template:formTr>
        </logic:equal>

        <logic:equal value="group" name="PMType">
            <template:formTr name="Ѳ��ά����">

                <html:select property="ownerID" styleClass="inputtext"
                    style="width:200px">

                    <html:options collection="patrolCollection" property="value"
                        labelProperty="label" />
                </html:select><font color="red"> *</font>
            </template:formTr>
        </logic:equal>
        <logic:notEqual value="group" name="PMType">
            <template:formTr name="����Ѳ����">
                <apptag:setSelectOptions valueName="patrolCollection"
                    tableName="patrolmaninfo" columnName1="patrolname"
                    columnName2="patrolid" region="true" />
                <html:select property="ownerID" styleClass="inputtext"
                    style="width:200px">
                    <html:option value="0">��</html:option>
                    <html:options collection="patrolCollection" property="value"
                        labelProperty="label" />
                </html:select><font color="red"> *</font>
            </template:formTr>
        </logic:notEqual>
        <template:formTr name="�豸������">
        	<html:text property="holder" styleClass="inputtext" style="width:200px"></html:text>
        </template:formTr>
        <template:formTr name="�豸����" isOdd="false">
            <html:select property="terminalType" styleClass="inputtext"
                style="width:200px">
                <html:option value="1">�ֳ�</html:option>
                <html:option value="2">����</html:option>
                <html:option value="3">PDA</html:option>
                <html:option value="4">�ֻ�</html:option>
            </html:select><font color="red"> *</font>
        </template:formTr>
        <template:formTr name="��������">
            <html:text property="produceMan" styleId="produceManId"
                styleClass="inputtext" style="width:200px" maxlength="10" readonly="true" value="�����θ���"/><font color="red"> *</font>
        </template:formTr>
        <template:formTr name="�豸�ͺ�" isOdd="false">
            <html:select property="terminalModel" styleId="select" onchange="viewStat(this);" styleClass="inputtext" style="width:200px">
                <html:option value="CT-1200">CT-1200</html:option>
                <html:option value="CT-6100">CT-6100</html:option>
                <html:option value="CT-6610">CT-6610</html:option>
                <html:option value="NV-2000">NV-2000</html:option>
            </html:select><font color="red"> *</font>

        </template:formTr>
        <template:formTr name="�豸���к�">
            <html:text property="machineSerial" styleId="machineSerialid" styleClass="inputtext"
                style="width:200px" maxlength="50" /><font color="red"> *</font>
        </template:formTr>
        <template:formTr name="�豸ʶ���루IMEI��">
            <html:text property="IMEI" styleId="IMEI_ID" styleClass="inputtext"
                style="width:200px" maxlength="16" /><font color="red"> * </font>
        </template:formTr>
        <template:formTr name="SIM������" isOdd="false">
            <html:select property="simType" styleClass="inputtext"
                style="width:200px">
                <html:option value="0001">ȫ��ͨ</html:option>
                <html:option value="0002">������</html:option>
                <!--
        <html:option value="0003">��ͨ</html:option>
        <html:option value="0004">��ͨCDMA</html:option>-->
            </html:select><font color="red"> *</font>
        </template:formTr>
        <template:formTr name="SIM����">
            <html:text property="simNumber" styleClass="inputtext"
                style="width:200px" maxlength="11" /><font color="red"> *</font>
        </template:formTr>
        <template:formTr name="�豸״̬" isOdd="false" tagID="state"
            style="display:none">
            <html:select property="state" styleClass="inputtext"
                style="width:200px">
                <html:option value="00">������·</html:option>
                <html:option value="01">�ճ�Ѳ��</html:option>
            </html:select><font color="red"> *</font>
        </template:formTr>

        <template:formSubmit>
            <td><html:submit styleClass="button">����</html:submit></td>
            <td><html:reset styleClass="button">ȡ��</html:reset></td>
            <td><input type="button" class="button" onclick="toGetBack()"
                value="����"></td>
        </template:formSubmit>
    </template:formTable>
</html:form>
<template:cssTable></template:cssTable>
